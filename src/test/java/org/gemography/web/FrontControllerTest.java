package org.gemography.web;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.gemography.bean.TrendingLanguageDetails;
import org.gemography.service.FetchServiceImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FrontControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private FetchServiceImpl service;
	
	@InjectMocks
	private FrontController front;
	
	@Test
	@Ignore
	public void trendingLanguagesTest() throws Exception {
		Set<TrendingLanguageDetails> repositorySet = new HashSet<>();
		repositorySet.add(new TrendingLanguageDetails("java"));
		repositorySet.add(new TrendingLanguageDetails("python"));
		repositorySet.add(new TrendingLanguageDetails("kotlin"));
		when(service.fetchAllTrendingReposLanguage()).thenReturn(repositorySet);
		this.mockMvc
				.perform(get("/repos/trending/languages")).andDo(print())
				.andExpect(status().isOk());
	}
	
}
