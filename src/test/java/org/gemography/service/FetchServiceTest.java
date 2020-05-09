package org.gemography.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.gemography.bean.TrendingLanguageDetails;
import org.gemography.bean.TredningRepoDetails;
import org.gemography.bean.TrendingLanguageResponse;
import org.gemography.bean.TrendingReposResponse;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FetchServiceTest {
	
	/**
	 * TODO Adapt Junit with the new changes.
	 */
	
	@Mock
	private RestTemplate restMock;

	@Mock
	ResponseEntity<TrendingLanguageResponse> responseMock;
	
	@Mock
	ResponseEntity<TrendingReposResponse> responseMockRepo;
	
	@InjectMocks
	private FetchServiceImpl service;

	
	@Test
	@Ignore
	public void fetchAllTrendingRepoLanguageTest() {
		TrendingLanguageResponse simulateResponse = new TrendingLanguageResponse();
		List<TrendingLanguageDetails> repositories = Arrays.asList(new TrendingLanguageDetails("java"), new TrendingLanguageDetails("python"),
				new TrendingLanguageDetails("Go"));
		simulateResponse.setItems(repositories);
		when(restMock.getForEntity("https://api.github.com/search/repositories?q=created:>2020-04-06&sort=stars&order=desc&page=1&per_page=100", TrendingLanguageResponse.class)).thenReturn(responseMock);
		when(responseMock.getBody()).thenReturn(simulateResponse);
		assertNotNull(service.fetchAllTrendingReposLanguage());
		assertEquals(3, service.fetchAllTrendingReposLanguage().size());
	}

	@Test
	@Ignore
	public void fetchListOfReposUsedByLanguageTest() {
		TrendingReposResponse simulateResponse = new TrendingReposResponse();
		simulateResponse.setTotal_count(123456);
		List<TredningRepoDetails> repositoryDetailsList = Arrays.asList(
				new TredningRepoDetails(1234, "name1", 1452),
				new TredningRepoDetails(1478, "name2", 12),
				new TredningRepoDetails(9632, "name3", 124)
		);
		simulateResponse.setItems(repositoryDetailsList);
		
		when(restMock.getForEntity("https://api.github.com/search/repositories?q=language:java&sort=stars&order=desc&page=1", TrendingReposResponse.class)).thenReturn(responseMockRepo);
		when(responseMockRepo.getBody()).thenReturn(simulateResponse);
		Map<String, TrendingReposResponse> result = service.fetchListOfReposUsedByLanguage("java", 1);
		assertNotNull(result);
		assertEquals(1, result.keySet().size()); 
		assertEquals(3, result.get("java").getItems().size());
		assertEquals("name1", result.get("java").getItems().get(0).getName());
		assertEquals("name2", result.get("java").getItems().get(1).getName());
		assertEquals("name3", result.get("java").getItems().get(2).getName());
	}
	
}
