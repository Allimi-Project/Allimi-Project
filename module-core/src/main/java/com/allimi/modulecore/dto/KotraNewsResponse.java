package com.allimi.modulecore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "response")
public class KotraNewsResponse {

	@JsonProperty("response")
	private Response response;

	@Data
	public static class Response {
		@JsonProperty("header")
		@JacksonXmlProperty(localName = "header")
		private Header header;

		@JsonProperty("body")
		@JacksonXmlProperty(localName = "body")
		private Body body;
	}

	@Data
	public static class Header {
		@JsonProperty("resultCode")
		@JacksonXmlProperty(localName = "resultCode")
		private String resultCode;

		@JsonProperty("resultMsg")
		@JacksonXmlProperty(localName = "resultMsg")
		private String resultMsg;
	}

	@Data
	public static class Body {
		@JsonProperty("totalCnt")
		@JacksonXmlProperty(localName = "totalCnt")
		private String totalCnt;

		@JsonProperty("pageNo")
		@JacksonXmlProperty(localName = "pageNo")
		private String pageNo;

		@JsonProperty("numOfRows")
		@JacksonXmlProperty(localName = "numOfRows")
		private String numOfRows;

		@JsonProperty("itemList")
		@JacksonXmlProperty(localName = "items")
		private ItemList itemList;
	}

	@Data
	public static class ItemList {
		@JsonProperty("item")
		@JacksonXmlProperty(localName = "item")
		@JacksonXmlElementWrapper(useWrapping = false)
		private List<Item> items;

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}
	}

	@Data
	public static class Item {
		@JsonProperty("cmdltNmKorn")
		@JacksonXmlProperty(localName = "cmdltNmKorn")
		private String cmdltNmKorn;

		@JsonProperty("newsTitl")
		@JacksonXmlProperty(localName = "newsTitl")
		private String newsTitl;

		@JsonProperty("kotraNewsUrl")
		@JacksonXmlProperty(localName = "kotraNewsUrl")
		private String kotraNewsUrl;

		@JsonProperty("jobSeNm")
		@JacksonXmlProperty(localName = "jobSeNm")
		private String jobSeNm;

		@JsonProperty("hsCdNm")
		@JacksonXmlProperty(localName = "hsCdNm")
		private String hsCdNm;

		@JsonProperty("dataType")
		@JacksonXmlProperty(localName = "dataType")
		private String dataType;

		@JsonProperty("cmdltNmEng")
		@JacksonXmlProperty(localName = "cmdltNmEng")
		private String cmdltNmEng;

		@JsonProperty("regn")
		@JacksonXmlProperty(localName = "regn")
		private String regn;

		@JsonProperty("fileLink")
		@JacksonXmlProperty(localName = "fileLink")
		private String fileLink;

		@JsonProperty("indstCl")
		@JacksonXmlProperty(localName = "indstCl")
		private String indstCl;

		@JsonProperty("indstCdList")
		@JacksonXmlProperty(localName = "indstCdList")
		private String indstCdList;

		@JsonProperty("ovrofInfo")
		@JacksonXmlProperty(localName = "ovrofInfo")
		private String ovrofInfo;

		@JsonProperty("othbcDt")
		@JacksonXmlProperty(localName = "othbcDt")
		private String othbcDt;

		@JsonProperty("bbstxSn")
		@JacksonXmlProperty(localName = "bbstxSn")
		private String bbstxSn;

		@JsonProperty("newsWrterNm")
		@JacksonXmlProperty(localName = "newsWrterNm")
		private String newsWrterNm;

		@JsonProperty("bbsSn")
		@JacksonXmlProperty(localName = "bbsSn")
		private String bbsSn;

		@JsonProperty("infoCl")
		@JacksonXmlProperty(localName = "infoCl")
		private String infoCl;

		@JsonProperty("natn")
		@JacksonXmlProperty(localName = "natn")
		private String natn;
	}
}