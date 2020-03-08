package yearnlune.lab.namingcenter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yearnlune.lab.namingcenter.database.dto.NamingDTO;
import yearnlune.lab.namingcenter.database.service.NamingService;

import javax.servlet.http.HttpServletResponse;

import static yearnlune.lab.namingcenter.constant.AccountConstant.NAMING;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.03
 * DESCRIPTION :
 */

@Slf4j
@RestController
public class NamingController {

    private final NamingService namingService;

    public NamingController(NamingService namingService) {
        this.namingService = namingService;
    }

    @RequestMapping(value = NAMING, method = RequestMethod.POST)
    public ResponseEntity<NamingDTO.CommonResponse> registerNaming(
            HttpServletResponse httpServletResponse,
            @RequestBody NamingDTO.RegisterRequest registerRequest) {
        NamingDTO.CommonResponse naming = namingService.saveNamingIfNotExist(registerRequest);
        return new ResponseEntity<>(naming, HttpStatus.OK);
    }
}
