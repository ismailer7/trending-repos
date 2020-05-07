package org.gemography.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.gemography.bean.Repository;
import org.gemography.bean.ResponseForLanguages;
import org.gemography.bean.ResponseForRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
	
	@Value("${org.github.api.token}")
	private String token;
	
	/**
	 * fetch the most used languages in 100 trending Repos on github.
	 * @return Set of languages.
	 */
	@Override
	public Set<Repository> fetchAllTrendingReposLanguage() {
		ResponseEntity<ResponseForLanguages> response = null;
		ResponseForLanguages responseBean = null;
		response = rest.exchange("https://api.github.com/search/repositories?q=created:>2020-04-06&sort=stars&order=desc&page=1&per_page=100", HttpMethod.GET, generateHeaderEntity(), ResponseForLanguages.class);
		responseBean = response.getBody();
		Set<Repository> repos = new HashSet<>(responseBean.getItems());
		return repos;
	}
	
	/**
	 * fetch all repositories that use some specific language.
	 * @param lang the language
	 * @param page the page requested
	 * @return the used repositories for some language.
	 */
	@Override
	public Map<String, ResponseForRepos> fetchListOfReposUsedByLanguage(String lang, int page) {
		Map<String, ResponseForRepos> data = new HashMap<>();
		ResponseEntity<ResponseForRepos> response = null;
		ResponseForRepos responseBean = null;
		response = rest.exchange("https://api.github.com/search/repositories?q=language:" + lang + "&sort=stars&order=desc&page=" + (page == 0 ? 1 : page), HttpMethod.GET, generateHeaderEntity(), ResponseForRepos.class);
		responseBean = response.getBody();
		responseBean.setCurrentPage((page == 0) ? "1" : String.valueOf(page));
		responseBean.setPreviousPage((Integer.valueOf(page) - 1 > 0 ? String.valueOf((Integer.valueOf(page) - 1)) : null));
		int nextPage = Integer.valueOf(responseBean.getCurrentPage()) + 1;
		responseBean.setNextPage(String.valueOf(nextPage));
		data.put(lang, responseBean);
		return data;
	}
	/**
	 * generate HEader Entity for the Get request
	 * @return HttpEntity
	 */
	private HttpEntity<String> generateHeaderEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", " token " + token);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		return entity;
	}
	
}
