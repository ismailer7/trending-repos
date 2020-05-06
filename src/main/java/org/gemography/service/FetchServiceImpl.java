package org.gemography.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.gemography.bean.Repository;
import org.gemography.bean.RepositoryDetails;
import org.gemography.bean.Response;
import org.gemography.bean.ResponseForLanguages;
import org.gemography.bean.ResponseForRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FetchServiceImpl implements IFetchService {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	private RestTemplate rest;
	
	@Autowired
	public void setRestTemplate(RestTemplate rest) {
		this.rest = rest;
	}
	
	@Override
	public Set<Repository> fetchAllTrendingReposLanguage() {
		ResponseEntity<ResponseForLanguages> response = null;
		ResponseForLanguages responseBean = null;
		response = rest.getForEntity("https://api.github.com/search/repositories?q=created:>2020-04-06&sort=stars&order=desc&page=1&per_page=100", ResponseForLanguages.class);
		responseBean = response.getBody();
		Set<Repository> repos = new HashSet<>(responseBean.getItems());
		return repos;
	}
	
	@Override
	public Map<String, ResponseForRepos> fetchListOfReposUsedByLanguage(String lang, int page) {
		Map<String, ResponseForRepos> data = new HashMap<>();
		ResponseEntity<ResponseForRepos> response = null;
		ResponseForRepos responseBean = null;
		response = rest.getForEntity("https://api.github.com/search/repositories?q=language:" + lang + "&sort=stars&order=desc&page=" + (page == 0 ? 1 : page), ResponseForRepos.class);
		responseBean = response.getBody();
		responseBean.setCurrentPage((page == 0) ? "1" : String.valueOf(page));
		responseBean.setPreviousPage((Integer.valueOf(page) - 1 > 0 ? String.valueOf((Integer.valueOf(page) - 1)) : null));
		responseBean.setNextPage(String.valueOf(page + 1));
		data.put(lang, responseBean);
		return data;
	}
	
}
