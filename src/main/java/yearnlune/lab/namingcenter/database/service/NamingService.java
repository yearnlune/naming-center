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
        if (!hasNaming(registerRequest.getName())) {
            return convertToCommonResponse(namingRepository.save(ConvertObject.object2Object(registerRequest, Naming.class)));
        }
        return null;
    }

    private boolean hasNaming(String name) {
        return namingRepository.existsByName(name);
    }

    private NamingDTO.CommonResponse convertToCommonResponse(Naming naming) {
        return ConvertObject.object2Object(naming, NamingDTO.CommonResponse.class);
    }
}
