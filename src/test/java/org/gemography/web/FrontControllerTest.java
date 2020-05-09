package org.gemography.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class FrontControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@org.junit.jupiter.api.Test
	public void trendingLanguagesTest() throws Exception {
		this.mockMvc
				.perform(get("/repos/trending/languages")).andDo(print())
				.andExpect(status().isOk());
	}
	
	@org.junit.jupiter.api.Test
	public void trendingRepositoryTest() throws Exception {
		this.mockMvc
				.perform(get("/repos/trending/language").param("language", "java").param("page", "1")).andDo(print())
				.andExpect(status().isOk());
	}
	
}
