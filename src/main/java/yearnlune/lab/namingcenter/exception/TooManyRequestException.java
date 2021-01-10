package yearnlune.lab.namingcenter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2021.01.08
 * DESCRIPTION :
 */
public class TooManyRequestException extends ResponseStatusException {
	public TooManyRequestException(@Nullable String reason) {
		super(HttpStatus.TOO_MANY_REQUESTS, reason == null ? "요청이 너무 많습니다. 잠시 후 요청해주세요." : reason);
	}
}
