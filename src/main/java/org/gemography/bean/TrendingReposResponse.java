package org.gemography.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class TrendingReposResponse extends Response<TredningRepoDetails> {
	
	@Setter
	@Getter
	@JsonProperty("total_count")
	private long totalCount;
	
	@Setter
	@Getter
	private String currentPage;
	
	@Setter
	@Getter
	private String previousPage;
	
	@Setter
	@Getter
	private String nextPage;
}
