package org.gemography.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gemography.bean.TrendingLanguageDetails;
import org.gemography.bean.TrendingLanguageResponse;
import org.gemography.bean.TrendingReposResponse;
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

	@Value("${github.token}")
	private String token;

	/**
	 * fetch the most used languages in 100 trending Repos on github.
	 * 
	 * @return Set of languages.
	 */
	@Override
	public Set<TrendingLanguageDetails> fetchAllTrendingReposLanguage() {
		ResponseEntity<TrendingLanguageResponse> response = null;
		TrendingLanguageResponse responseBean = null;
		response = rest.exchange(
				"https://api.github.com/search/repositories?q=created:>" + simplifyDate()
						+ "&sort=stars&order=desc&page=1&per_page=100",
				HttpMethod.GET, generateHeaderEntity(), TrendingLanguageResponse.class);
		responseBean = response.getBody();
		Set<TrendingLanguageDetails> repos = new HashSet<>(responseBean.getItems());
		return repos;
	}

	/**
	 * fetch all repositories that use some specific language.
	 * 
	 * @param lang the language
	 * @param page the page requested
	 * @return the used repositories for some language.
	 */
	@Override
	public Map<String, TrendingReposResponse> fetchListOfReposUsedByLanguage(String lang, int page) {
		Map<String, TrendingReposResponse> data = new HashMap<>();
		ResponseEntity<TrendingReposResponse> response = null;
		TrendingReposResponse responseBean = null;
		response = rest
				.exchange(
						"https://api.github.com/search/repositories?q=language:" + lang + "&sort=stars&order=desc&page="
								+ (page == 0 ? 1 : page),
						HttpMethod.GET, generateHeaderEntity(), TrendingReposResponse.class);
		responseBean = response.getBody();
		responseBean.setCurrentPage((page == 0) ? "1" : String.valueOf(page));
		responseBean
				.setPreviousPage((Integer.valueOf(page) - 1 > 0 ? String.valueOf((Integer.valueOf(page) - 1)) : null));
		int nextPage = Integer.valueOf(responseBean.getCurrentPage()) + 1;
		responseBean.setNextPage(String.valueOf(nextPage));
		data.put(lang, responseBean);
		return data;
	}
	
	/**
	 * generate HEader Entity for the Get request
	 * 
	 * @return HttpEntity
	 */
	public HttpEntity<String> generateHeaderEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("X-Auth-Token", " token " + token);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		return entity;
	}

	/**
	 * Simplify a date format
	 * 
	 * @return simplified date string before one month.
	 */
	public String simplifyDate() {
		Pattern pattern = Pattern
				.compile("([a-zA-Z]{3}) ([a-zA-Z]{3}) (\\d{2}) (\\d{2}:\\d{2}:\\d{2}) ([a-zA-Z]{3,}) (\\d{4})$");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1); // before 1 month
		Date date = cal.getTime();
		Matcher matcher = pattern.matcher(date.toString());
		String month = "";
		String day = "";
		String year = "";
		matcher.matches();
		month = getMonth(matcher.group(2));
		day = matcher.group(3);
		year = matcher.group(6);
		return year + "-" + month + "-" + day;
	}

	/**
	 * convert between String representation and number representation.
	 * 
	 * @param month A String representation month
	 * @return a number representation format
	 */
	private String getMonth(String month) {
		String m = "";
		switch (month) {
		case "Jan":
			m = "01";
			break;
		case "Feb":
			m = "02";
			break;
		case "Mar":
			m = "03";
			break;
		case "Apr":
			m = "04";
			break;
		case "May":
			m = "05";
			break;
		case "Jun":
			m = "06";
			break;
		case "Jul":
			m = "07";
			break;
		case "Aug":
			m = "08";
			break;
		case "Sep":
			m = "09";
			break;
		case "Oct":
			m = "10";
			break;
		case "Nov":
			m = "11";
			break;
		case "Dec":
			m = "12";
			break;
		}
		return m;
	}

}
