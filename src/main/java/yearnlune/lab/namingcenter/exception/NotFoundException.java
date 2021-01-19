package yearnlune.lab.namingcenter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2021.01.19
 * DESCRIPTION :
 */
public class NotFoundException extends ResponseStatusException {
	public NotFoundException(@Nullable String reason) {
		super(HttpStatus.NOT_FOUND, reason == null ? "요청을 찾을 수 없습니다." : reason);
	}
}
