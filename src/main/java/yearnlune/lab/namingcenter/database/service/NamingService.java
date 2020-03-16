package yearnlune.lab.namingcenter.database.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yearnlune.lab.convertobject.ConvertObject;
import yearnlune.lab.namingcenter.database.dto.AccountDTO;
import yearnlune.lab.namingcenter.database.dto.NamingDTO;
import yearnlune.lab.namingcenter.database.repository.NamingRepository;
import yearnlune.lab.namingcenter.database.table.Account;
import yearnlune.lab.namingcenter.database.table.Naming;

import java.util.ArrayList;
import java.util.List;

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

    public NamingService(NamingRepository namingRepository) {
        this.namingRepository = namingRepository;
    }

    public NamingDTO.CommonResponse saveNamingIfNotExist(NamingDTO.RegisterRequest registerRequest) {
        Naming name = ConvertObject.object2Object(registerRequest, Naming.class);
        if (!hasNaming(registerRequest.getName())) {
            return convertToCommonResponse(namingRepository.save(
                    Naming.builder()
                            .name(registerRequest.getName())
                            .description(registerRequest.getDescription())
                            .build()
                    ));
        }
        return null;
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
}
