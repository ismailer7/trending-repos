package org.gemography.service;

import java.util.Map;
import java.util.Set;

import org.gemography.bean.TrendingLanguageDetails;
import org.gemography.bean.TrendingReposResponse;

public interface IFetchService {
	
	Set<TrendingLanguageDetails> fetchAllTrendingReposLanguage();
	
	Map<String, TrendingReposResponse> fetchListOfReposUsedByLanguage(String lang, int page);

}
