package yearnlune.lab.namingcenter.rest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static yearnlune.lab.namingcenter.constant.AccountConstant.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import yearnlune.lab.namingcenter.database.dto.AccountDTO;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.12.15
 * DESCRIPTION :
 */

public class AccountControllerTest extends RestfulApiTestSupport {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void registerAccount_correctAccount_shouldBeCreated() throws Exception {
		/* GIVEN */
		String content = objectMapper.writeValueAsString(
			AccountDTO.RegisterRequest.builder()
				.id("object")
				.name("오브젝트")
				.password("object")
				.build());

		/* WHEN */
		mockMvc.perform(
			post(ACCOUNT)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.with(csrf())
		)

		/* THEN */
			.andDo(print())
			.andExpect(content().encoding("UTF-8"))
			.andExpect(status().isCreated());
	}

	@Test
	public void registerAccount_emptyPassword_shouldBeBadRequest() throws Exception {
		/* GIVEN */
		String content = objectMapper.writeValueAsString(
			AccountDTO.RegisterRequest.builder()
				.id("")
				.name("오브젝트")
				.password("")
				.build());

		/* WHEN */
		mockMvc.perform(
			post(ACCOUNT)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.with(csrf())
		)

		/* THEN */
			.andDo(print())
			.andExpect(content().encoding("UTF-8"))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void loginAccount_correctAccount_shouldBeOk() throws Exception {
		String content = objectMapper.writeValueAsString(
			AccountDTO.LoginRequest.builder()
				.id("object")
				.password("object")
				.build());

		mockMvc.perform(
			post(LOGIN)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.with(csrf())
		)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void validateToken_expiredToken_shouldBeUnauthorized() throws Exception {
		String token =
			"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuYW1pbmdDZW50ZXJKV1QiLCJpc3MiOiJ"
				+ "uYW1pbmdDZW50ZXIiLCJhdXRob3JpdGllcyI6WyJST0xFX0dVRVNU"
				+ "Il0sImlkIjoiYWRtaW4iLCJpYXQiOjE2MDM0MzQ2MzYsImV4cCI6MTYwMzQzNTIzNn0"
				+ ".0l447JjHgTVFwLflZ9OeWvdUkXc-Ejjh5TwMXCk"
				+ "kGUTV1DllLQy-yBzL3gkAftztjRjbP-RPCI5U5U_ISt4xSw";

		String content = objectMapper.writeValueAsString(
			AccountDTO.TokenValidationRequest.builder()
				.jwt(token)
				.build());

		mockMvc.perform(
			post(VALIDATE)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.with(csrf())
		)
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}
}
