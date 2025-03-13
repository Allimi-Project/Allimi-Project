package com.allimi.modulebatch.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class CreateDateJobParameter {

	private LocalDate date;

	@Value("#{jobParameters[date]}")
	public void setDate(String date) throws JobParametersInvalidException {
		if (Objects.isNull(date)) {
			throw new JobParametersInvalidException("날짜 형식 파라미터는 필수입니다.");
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.date = LocalDate.parse(date, formatter);
	}
}
