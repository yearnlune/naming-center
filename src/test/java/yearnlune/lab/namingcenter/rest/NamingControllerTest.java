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
import yearnlune.lab.namingcenter.database.dto.NamingDTO;
import yearnlune.lab.namingcenter.database.repository.NamingRepository;
import yearnlune.lab.namingcenter.database.table.Naming;
import yearnlune.lab.namingcenter.service.AccountService;
import yearnlune.lab.namingcenter.service.NamingService;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2021.01.13
 * DESCRIPTION :
 */
public class NamingControllerTest extends RestfulApiTestSupport {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AccountService accountService;

	@Autowired
	private NamingService namingService;

	@Autowired
	private NamingRepository namingRepository;

	private String jwtToken;

	@Before
	public void setUp() throws Exception {
		jwtToken = accountService.createAuthorizationToken(
			AccountDTO.CommonResponse.builder()
				.idx(1)
				.role("ROLE_GUEST")
				.build()
		);

		insertMockAccount();
		insertMockNaming();
	}

	private void insertMockAccount() throws Exception {
		/* GIVEN */
		String content = objectMapper.writeValueAsString(
			AccountDTO.RegisterRequest.builder()
				.id("mock")
				.name("mock")
				.password("mock")
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
	}

	private void insertMockNaming() throws Exception {
		/* GIVEN */
		String content = objectMapper.writeValueAsString(
			NamingDTO.RegisterRequest.builder()
				.name("mock")
				.description("mock")
				.account(AccountDTO.CommonResponse.builder()
					.idx(1)
					.build())
				.build());

		/* WHEN */
		mockMvc.perform(
			post(NAMING)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtToken)
				.with(csrf())
		);
	}

	@Test
	public void registerNaming_correctNaming_shouldBeCreated() throws Exception {
		/* GIVEN */
		String content = objectMapper.writeValueAsString(
			NamingDTO.RegisterRequest.builder()
				.name("device")
				.description("장치")
				.account(AccountDTO.CommonResponse.builder()
					.idx(1)
					.build())
				.build());

		/* WHEN */
		mockMvc.perform(
			post(NAMING)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtToken)
				.with(csrf())
		)

			/* THEN */
			.andDo(print())
			.andExpect(content().encoding("UTF-8"))
			.andExpect(status().isCreated());
	}

	@Test
	public void registerNaming_alreadyRegisteredNaming_shouldBeBadRequest() throws Exception {
		/* GIVEN */
		String content = objectMapper.writeValueAsString(
			NamingDTO.RegisterRequest.builder()
				.name("mock")
				.description("mock")
				.account(AccountDTO.CommonResponse.builder()
					.idx(1)
					.build())
				.build());

		/* WHEN */
		mockMvc.perform(
			post(NAMING)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtToken)
				.with(csrf())
		)

			/* THEN */
			.andDo(print())
			.andExpect(content().encoding("UTF-8"))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void searchNaming_existNaming_shouldBeOk() throws Exception {
		/* WHEN */
		mockMvc.perform(
			get(NAMING + "/mock")
				.header("Authorization", jwtToken)
				.with(csrf())
		)
			/* THEN */
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void searchNaming_notExistNaming_shouldBeNotFound() throws Exception {
		/* WHEN */
		mockMvc.perform(
			get(NAMING + "/empty")
				.header("Authorization", jwtToken)
				.with(csrf())
		)
			/* THEN */
			.andDo(print())
			.andExpect(status().isNotFound());
	}

	@Test
	public void updateNaming_existNamingIdx_shouldBeOk() throws Exception {
		String content = objectMapper.writeValueAsString(
			NamingDTO.CommonResponse.builder()
				.name("mockn")
				.description("mockd")
				.build());

		/* WHEN */
		mockMvc.perform(
			patch(NAMING + "/1")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", jwtToken)
				.with(csrf())
		)
			/* THEN */
			.andDo(print())
			.andExpect(
				content().json(convertNamingIntoJsonOfCommomResponse(namingRepository.findNamingByIdx(1).orElse(null))))
			.andExpect(status().isOk());
	}

	private String convertNamingIntoJsonOfCommomResponse(Naming naming) throws Exception {
		return objectMapper.writeValueAsString(
			namingService.convertToCommonResponse(naming));
	}

}
