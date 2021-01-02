package yearnlune.lab.namingcenter.rest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static yearnlune.lab.namingcenter.constant.AccountConstant.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import yearnlune.lab.namingcenter.database.dto.AccountDTO;
import yearnlune.lab.namingcenter.service.AccountService;

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

	@Autowired
	private AccountService accountService;

	private String jwtToken;

	@Before
	public void init() {
		jwtToken = accountService.createAuthorizationToken(
			AccountDTO.CommonResponse.builder()
				.idx(1)
				.role("ROLE_GUEST")
				.build()
		);
	}

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
	public void updateAccount_name_shouldBeOk() throws Exception {
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
		);

		String patchedContent = objectMapper.writeValueAsString(
			AccountDTO.PatchedRequest.builder()
				.name("object")
				.build());

		mockMvc.perform(
			patch(ACCOUNT + "/{idx}", 1)
				.content(patchedContent)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtToken)
				.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void updateAccount_ghostAccount_shouldBeBadRequest() throws Exception {
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
		);

		String patchedContent = objectMapper.writeValueAsString(
			AccountDTO.PatchedRequest.builder()
				.build());

		mockMvc.perform(
			patch(ACCOUNT + "/{idx}", 2)
				.content(patchedContent)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtToken)
				.with(csrf()))
			.andDo(print())
			.andExpect(status().isBadRequest());
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
