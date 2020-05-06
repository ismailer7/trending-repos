package org.gemography.bean;

import lombok.Getter;
import lombok.Setter;

public class ResponseForRepos extends Response<RepositoryDetails> {
	
	@Setter
	@Getter
	private long total_count;
	
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
