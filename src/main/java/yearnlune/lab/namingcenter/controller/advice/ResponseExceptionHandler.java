package yearnlune.lab.namingcenter.controller.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import yearnlune.lab.namingcenter.exception.BadRequestException;
import yearnlune.lab.namingcenter.exception.ExceptionResponse;
import yearnlune.lab.namingcenter.exception.TooManyRequestException;
import yearnlune.lab.namingcenter.exception.UnauthorizedException;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.12.16
 * DESCRIPTION :
 */

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
		HttpServletRequest request) {
		return new ResponseEntity<>(
			new ExceptionResponse(HttpStatus.BAD_REQUEST, exception.getBindingResult(), request.getRequestURI()),
			HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException exception,
		HttpServletRequest request) {
		return new ResponseEntity<>(
			new ExceptionResponse(HttpStatus.BAD_REQUEST, exception.getReason(), request.getRequestURI()),
			HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TooManyRequestException.class)
	protected ResponseEntity<ExceptionResponse> handleTooManyRequestException(TooManyRequestException exception,
		HttpServletRequest request) {
		return new ResponseEntity<>(
			new ExceptionResponse(HttpStatus.TOO_MANY_REQUESTS, exception.getReason(), request.getRequestURI()),
			HttpStatus.TOO_MANY_REQUESTS);
	}

	@ExceptionHandler(UnauthorizedException.class)
	protected ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException exception,
		HttpServletRequest request) {
		return new ResponseEntity<>(
			new ExceptionResponse(HttpStatus.UNAUTHORIZED, exception.getReason(), request.getRequestURI()),
			HttpStatus.UNAUTHORIZED);
	}
}
