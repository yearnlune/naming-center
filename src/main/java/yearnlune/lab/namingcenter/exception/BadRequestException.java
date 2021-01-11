package yearnlune.lab.namingcenter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2021.01.02
 * DESCRIPTION :
 */
public class BadRequestException extends ResponseStatusException {
	public BadRequestException(@Nullable String reason) {
		super(HttpStatus.BAD_REQUEST, reason == null ? "잘못된 요청입니다." : reason);
	}
}
