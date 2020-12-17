package yearnlune.lab.namingcenter.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import yearnlune.lab.namingcenter.exception.ExceptionResponse;

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
	protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		log.warn("HttpStatus.BAD_REQUEST" + ex.getBindingResult());
		return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getBindingResult()), HttpStatus.BAD_REQUEST);
	}

}
