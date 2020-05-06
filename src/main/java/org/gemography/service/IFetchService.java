package org.gemography.service;

import java.util.Map;
import java.util.Set;

import org.gemography.bean.Repository;
import org.gemography.bean.ResponseForRepos;

public interface IFetchService {
	
	Set<Repository> fetchAllTrendingReposLanguage();
	
	Map<String, ResponseForRepos> fetchListOfReposUsedByLanguage(String lang, int page);

}
