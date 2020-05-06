package org.gemography.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gemography.bean.Repository;
import org.gemography.bean.ResponseForRepos;
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

	@GetMapping("/languages")
	public List<String> trendingLanguages() {
		List<String> languages = convertToStringList(fetchService.fetchAllTrendingReposLanguage());
		return languages;
	}
	
	@GetMapping("/language")
	public Map<String, ResponseForRepos> languageDetails(@RequestParam String language, @RequestParam int page) {
		return fetchService.fetchListOfReposUsedByLanguage(language, page);
	}
	
	private List<String> convertToStringList(Set<Repository> repos) {
		List<String> languages = new ArrayList<>();
		List<Repository> repoList = new ArrayList<>(repos);
		for (Repository repo : repoList) {
			if(repo.getLanguage() != null && !repo.getLanguage().isEmpty()) {
				fetchService.fetchAllTrendingReposLanguage();
			}
		}
		return languages;
	}

}
