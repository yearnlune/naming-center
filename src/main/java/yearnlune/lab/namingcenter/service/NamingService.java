package yearnlune.lab.namingcenter.service;

import static yearnlune.lab.namingcenter.service.RedisService.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import yearnlune.lab.convertobject.ConvertObject;
import yearnlune.lab.namingcenter.database.dto.NamingDTO;
import yearnlune.lab.namingcenter.database.repository.NamingRepository;
import yearnlune.lab.namingcenter.database.table.Naming;
import yearnlune.lab.namingcenter.exception.BadRequestException;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.03
 * DESCRIPTION :
 */

@Slf4j
@Service
public class NamingService {

	private final NamingRepository namingRepository;

	private final RedisTemplate<String, Integer> redisTemplate;

	public NamingService(NamingRepository namingRepository, RedisTemplate<String, Integer> redisTemplate) {
		this.namingRepository = namingRepository;
		this.redisTemplate = redisTemplate;
	}

	public NamingDTO.AutoCompleteResponse findNamingUsingRedis(String naming) {
		return NamingDTO.AutoCompleteResponse.builder()
			.namingList(getNamingListUsingRedis(naming.toLowerCase()))
			.build();
	}

	public NamingDTO.CommonResponse saveNamingIfNotExist(NamingDTO.RegisterRequest registerRequest) throws BadRequestException {
		if (hasNaming(registerRequest.getName())) {
			throw new BadRequestException("이미 존재하는 이름입니다. [" + registerRequest.getName() + "]");
		}

		return convertToCommonResponse(namingRepository.save(
			Naming.builder()
				.name(registerRequest.getName().toLowerCase())
				.description(registerRequest.getDescription().toLowerCase())
				.build()
		));
	}

	public List<NamingDTO.CommonResponse> findNaming(String keyword) {
		return convertToCommonResponse(namingRepository.findAllByKeywordContaining(keyword));
	}

	private boolean hasNaming(String name) {
		return namingRepository.existsByName(name);
	}

	private NamingDTO.CommonResponse convertToCommonResponse(Naming naming) {
		return ConvertObject.object2Object(naming, NamingDTO.CommonResponse.class);
	}

	private List<NamingDTO.CommonResponse> convertToCommonResponse(List<Naming> namingList) {
		List<NamingDTO.CommonResponse> refinedNamingList = new ArrayList<>();
		for (Naming naming : namingList) {
			refinedNamingList.add(convertToCommonResponse(naming));
		}
		return refinedNamingList;
	}

	public List<Naming> findAllUpdatedNaming(Timestamp current) {
		return namingRepository.findAllByUpdatedAtGreaterThanEqual(current);
	}

	public List<Naming> findAll() {
		return namingRepository.findAll();
	}

	public List<String> findNames() {
		return namingRepository.findNames();
	}

	private List<String> getNamingListUsingRedis(String naming) {
		Set<String> namingSet = redisTemplate.keys(makeNameRedisKey(naming) + "*");
		List<String> namingList = new ArrayList<>();
		if (namingSet == null) {
			namingSet = new HashSet<>();
		}

		for (String key : namingSet) {
			namingList.add(key.split(":")[1]);
		}

		return namingList;
	}
}
