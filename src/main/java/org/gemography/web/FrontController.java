package org.gemography.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gemography.bean.TrendingLanguageDetails;
import org.gemography.bean.TrendingReposResponse;
import org.gemography.service.IFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repos/trending")
public class FrontController {

	@Autowired
	private IFetchService fetchService;

	/**
	 * fetch most used languages for the 100 trending repos on github.
	 * @return a list of languages
	 */
	@GetMapping("/languages")
	public List<String> trendingLanguages() {
		List<String> languages = convertToStringList(fetchService.fetchAllTrendingReposLanguage());
		return languages;
	}

	/**
	 * fetch all repositories using some language
	 * @param language the language 
	 * @param page the page number
	 * @return all repositories for the language requested
	 */
	@GetMapping("/language")
	public Map<String, TrendingReposResponse> languageDetails(@RequestParam String language, @RequestParam int page) {
		return fetchService.fetchListOfReposUsedByLanguage(language, page);
	}
	
	/**
	 * convert a set of repositories to a list of languages.
	 * @param repos a set of repositories
	 * @return a list of languages
	 */
	private List<String> convertToStringList(Set<TrendingLanguageDetails> repos) {
		List<String> languages = new ArrayList<>();
		List<TrendingLanguageDetails> repoList = new ArrayList<>(repos);
		for (TrendingLanguageDetails repo : repoList) {
			if(repo.getLanguage() != null && !repo.getLanguage().isEmpty()) {
				languages.add(repo.getLanguage());
			}
		}
		return languages;
	}

}
