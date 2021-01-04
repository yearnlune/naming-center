package yearnlune.lab.namingcenter.exception;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.12.17
 * DESCRIPTION :
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
	private Timestamp timestamp;

	private Integer status;

	private String error;

	private String message;

	private String path;

	public ExceptionResponse(HttpStatus httpStatus, BindingResult bindingResult, String path) {
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.status = httpStatus.value();
		this.error = httpStatus.name();
		this.message = createErrorMessage(bindingResult);
		this.path = path;
	}

	public ExceptionResponse(HttpStatus httpStatus, String reason, String path) {
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.status = httpStatus.value();
		this.error = httpStatus.name();
		this.message = reason;
		this.path = path;
	}

	private static String createErrorMessage(BindingResult bindingResult) {
		StringBuilder stringBuilder = new StringBuilder();
		boolean isFirst = true;

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			if (!isFirst) {
				stringBuilder.append(", ");
			} else {
				isFirst = false;
			}

			stringBuilder.append("[");
			stringBuilder.append(fieldError.getField());
			stringBuilder.append("] ");
			stringBuilder.append(fieldError.getDefaultMessage());
			stringBuilder.append(" ");
		}

		return stringBuilder.toString();
	}
}
