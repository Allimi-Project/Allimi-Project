package com.allimi.modulecore.domain.news;

import lombok.Getter;

@Getter
public enum Code {

	I001193("농림수산식품", "1"),
	I001195("광물/에너지", "2"),
	I001212("화학", "3"),
	I001197("섬유/패션", "4"),
	I001198("생활소비재", "5"),
	I001201("철강/금속", "6"),
	I001199("기계류", "7"),
	I001200("자동차/수송기기", "8"),
	I001202("전자/전기", "9"),
	I001206("의료바이오", "10"),
	I001203("환경", "11"),
	I001204("건설/인프라/플랜트", "12"),
	I001208("유통/물류", "13"),
	I001205("IT", "14"),
	I001207("문화컨텐츠", "15"),
	I001209("금융", "16"),
	I001210("관광/교육/서비스", "17"),
	I001211("기타", "18"),
	I001307("산업일반", "19");

	private final String code;
	private final String legacyCode;

	Code(String code, String legacyCode) {
		this.code = code;
		this.legacyCode = legacyCode;
	}
}
