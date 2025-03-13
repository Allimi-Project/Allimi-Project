package com.allimi.modulecore.util;

import com.allimi.modulecore.domain.news.Code;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = false)
public class EnumConverter implements AttributeConverter<Code, String> {

	@Override
	public String convertToDatabaseColumn(Code attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getCode(); // 한글 코드명을 DB에 저장
	}

	@Override
	public Code convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}

		// DB에 저장된 한글 코드명으로 Code enum을 찾음
		return Arrays.stream(Code.values())
				.filter(code -> code.getCode().equals(dbData))
				.findFirst()
				.orElse(Code.I001211); // 기본값은 "기타" 코드
	}
}
