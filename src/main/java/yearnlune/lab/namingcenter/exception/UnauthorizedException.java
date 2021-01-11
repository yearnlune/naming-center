package yearnlune.lab.namingcenter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2021.01.11
 * DESCRIPTION :
 */
public class UnauthorizedException extends ResponseStatusException {
	public UnauthorizedException(@Nullable String reason) {
		super(HttpStatus.UNAUTHORIZED, reason == null ? "권한이 없습니다." : reason);
	}
}
