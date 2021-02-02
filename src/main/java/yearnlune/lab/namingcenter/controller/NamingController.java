package yearnlune.lab.namingcenter.controller;

import static yearnlune.lab.namingcenter.constant.AccountConstant.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import yearnlune.lab.namingcenter.database.dto.NamingDTO;
import yearnlune.lab.namingcenter.service.NamingService;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.03
 * DESCRIPTION :
 */

@Slf4j
@RestController
@RequestMapping(NAMING)
public class NamingController {

	private final NamingService namingService;

	public NamingController(NamingService namingService) {
		this.namingService = namingService;
	}

	@PostMapping
	public ResponseEntity<NamingDTO.CommonResponse> registerNaming(
		@RequestBody NamingDTO.RegisterRequest registerRequest) {
		NamingDTO.CommonResponse naming = namingService.saveNamingIfNotExist(registerRequest);
		return new ResponseEntity<>(naming, HttpStatus.CREATED);
	}

	@GetMapping("/{keyword}")
	public ResponseEntity<List<NamingDTO.CommonResponse>> searchNaming(
		@PathVariable String keyword) {
		List<NamingDTO.CommonResponse> namingList = namingService.findNaming(keyword);
		return new ResponseEntity<>(namingList, HttpStatus.OK);
	}

	@PatchMapping("/{idx}")
	public ResponseEntity<NamingDTO.CommonResponse> updateNaming(
		@PathVariable Integer idx,
		@RequestBody NamingDTO.CommonResponse patchRequest) {
		NamingDTO.CommonResponse naming =  namingService.updateNaming(idx, patchRequest);
		return new ResponseEntity<>(naming, HttpStatus.OK);
	}

	@GetMapping("/{naming}" + AUTOCOMPLETE)
	public ResponseEntity<NamingDTO.AutoCompleteResponse> getAutocomplete(
		@PathVariable String naming) {
		NamingDTO.AutoCompleteResponse autoCompleteResponse = namingService.findNamingUsingRedis(naming);
		return new ResponseEntity<>(autoCompleteResponse, HttpStatus.OK);
	}
}
