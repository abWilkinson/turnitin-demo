package com.abwilkinson.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.abwilkinson.demo.domain.Athlete;
import com.abwilkinson.demo.repository.AthleteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class DemoIntegrationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void runSearchWithNoQuery() throws Exception {
		mockMvc.perform(get("/api/athletes/search")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(5)));
	}

	@Test
	void runSearchWithNameQuery() throws Exception {
		mockMvc.perform(get("/api/athletes/search?name=anna")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	void runSearchWithValidAgeQuery() throws Exception {
		mockMvc.perform(get("/api/athletes/search?ageRange=>30")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	void runSearchWithInvalidAgeQuery() throws Exception {
		mockMvc.perform(get("/api/athletes/search?ageRange=>99")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message",is("The supplied Age Range: '>99' is not currently supported")));
	}

	@Test
	void runSearchWithSkillsQuery() throws Exception {
		mockMvc.perform(get("/api/athletes/search?skills=snowboarding,cycling")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	void runSearchWithSupersetSkillsQuery() throws Exception {
		mockMvc.perform(get("/api/athletes/search?skills=winter sports")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)));
	}

	@Test
	void runSearchWithInvalidSkillsQuery() throws Exception {
		mockMvc.perform(get("/api/athletes/search?skills=asd")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void runSearchWithExperienceQuery() throws Exception {
		mockMvc.perform(get("/api/athletes/search?minExperience=10")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	void runSearchWithNoExperienceQuery() throws Exception {
		mockMvc.perform(get("/api/athletes/search?minExperience=0")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(5)));
	}

	@Test
	void runSearchWithAllQueries() throws Exception {
		mockMvc.perform(get("/api/athletes/search?name=a&ageRange=>30&skills=winter sports&minExperience=10")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

}
