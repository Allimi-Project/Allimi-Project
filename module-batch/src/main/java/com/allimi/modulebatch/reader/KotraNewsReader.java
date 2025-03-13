package com.allimi.modulebatch.reader;

import com.allimi.moduleapi.service.KotraNewsApiService;
import com.allimi.modulecore.dto.KotraNewsResponse;
import com.allimi.modulecore.dto.KotraNewsResponse.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.util.ArrayList;
import java.util.List;

import static com.allimi.modulebatch.constants.BatchConstants.SEARCH_PARAMS;

public class KotraNewsReader implements ItemReader<Item>, ItemStreamReader<Item> {

	public static final Logger logger = LoggerFactory.getLogger(KotraNewsReader.class);

	private final KotraNewsApiService kotraNewsApiService;
	private int currentCodeIndex = 0;
	private List<Item> currentItems = new ArrayList<>();
	private int currentItemIndex = 0;
	private int pageSize = 100;
	private int currentPage = 1;
	private boolean isInitialized = false;
	private boolean moreItemsAvailable = true;
	private int totalProcessedItems = 0;

	public KotraNewsReader(KotraNewsApiService kotraNewsApiService) {
		this.kotraNewsApiService = kotraNewsApiService;
	}

	@Override
	public void open(org.springframework.batch.item.ExecutionContext executionContext) throws ItemStreamException {
		if (!isInitialized) {
			logger.info("KotraNewsReader 초기화 중 - 총 {} 개의 코드 처리 예정", SEARCH_PARAMS.length);
			isInitialized = true;
			fetchNextPage();
		}
	}

	@Override
	public Item read() {
		if (currentItemIndex < currentItems.size()) {
			Item item = currentItems.get(currentItemIndex++);
			int count = totalProcessedItems;

			if (count % 100 == 0) {
				logger.info("KOTRA 뉴스 처리 중: 총 {}개 아이템 처리 완료 (코드: {}, 현재 코드 인덱스: {}/{})", count, SEARCH_PARAMS[currentCodeIndex], currentCodeIndex + 1, SEARCH_PARAMS.length);
			}
			return item;
		}

		if (moreItemsAvailable) {
			fetchNextPage();

			if (!currentItems.isEmpty()) {
				Item item = currentItems.get(currentItemIndex++);
				totalProcessedItems++;
				return item;
			}
		}
		return null;
	}

	private void fetchNextPage() {
		if (currentCodeIndex >= SEARCH_PARAMS.length) {
			logger.info("모든 코드 처리 완료. 총 처리된 아이템: {}", totalProcessedItems);
			moreItemsAvailable = false;
			currentItems.clear();
			return;
		}

		String currentCode = SEARCH_PARAMS[currentCodeIndex];
		logger.info("코드 {}, 페이지 {} 데이터 로드 중 (코드 인덱스: {}/{})", currentCode, currentPage, currentCodeIndex + 1, SEARCH_PARAMS.length);

		try {
			KotraNewsResponse response = kotraNewsApiService.getKotraNewsByCode(currentPage, pageSize, currentCode)
					.block();

			if (response != null && response.getResponse() != null &&
					response.getResponse().getBody() != null) {

				int totalItems = Integer.parseInt(response.getResponse().getBody().getTotalCnt());
				int totalPages = (int) Math.ceil((double) totalItems / pageSize);

				logger.info("코드 {}: 전체 {}개 아이템, 전체 {}페이지, 현재 {}페이지", currentCode, totalItems, totalPages, currentPage);
				List<Item> items = extractNewsItems(response);

				if (items != null && !items.isEmpty()) {
					currentItems = new ArrayList<>(items);
					currentItemIndex = 0;

					logger.info("코드 {}, 페이지 {} 로드 완료: {}개 아이템", currentCode, currentPage, items.size());

					if (currentPage >= totalPages) {
						moveToNextCode();
					} else {
						currentPage++;
					}
				} else {
					logger.info("코드 {}, 페이지 {}에 데이터 없음, 다음 코드로 이동", currentCode, currentPage);
					moveToNextCode();
				}
			} else {
				logger.warn("코드 {}, 페이지 {}에서 유효하지 않은 응답, 다음 코드로 이동", currentCode, currentPage);
				moveToNextCode();
			}
		} catch (Exception e) {
			logger.error("데이터 가져오기 오류 (코드: {}, 페이지: {}): {}", currentCode, currentPage, e.getMessage(), e);
			moveToNextCode();
		}
	}

	private void moveToNextCode() {
		String completedCode = SEARCH_PARAMS[currentCodeIndex];
		logger.info("코드 {} 처리 완료. 다음 코드로 이동", completedCode);

		currentCodeIndex++;
		currentPage = 1;

		if (currentCodeIndex >= SEARCH_PARAMS.length) {
			logger.info("모든 코드({})의 처리가 완료되었습니다. 총 처리된 아이템: {}", SEARCH_PARAMS.length, totalProcessedItems);
			moreItemsAvailable = false;
			currentItems.clear();
		}
	}

	private List<Item> extractNewsItems(KotraNewsResponse response) {
		return response.getResponse().getBody().getItemList().getItems();
	}

	@Override
	public void close() throws ItemStreamException {
		logger.info("KotraNewsReader 종료, 처리된 코드 수: {}/{}, 총 처리된 아이템: {}", Math.min(currentCodeIndex, SEARCH_PARAMS.length), SEARCH_PARAMS.length, totalProcessedItems);
	}
}