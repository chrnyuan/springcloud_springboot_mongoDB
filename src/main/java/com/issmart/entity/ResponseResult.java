package com.issmart.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResponseResult<T> {
	/**
	 * 是否成功
	 */
	private boolean isSuccess = true;
	/**
	 * 响应结果
	 */
	private T data = null;
	/**
	 * 错误信息
	 */
	private String message = null;

	/**
	 * 异常信息
	 */
	private RuntimeException exception = null;

	public ResponseResult() {
	}

	public ResponseResult(boolean isSuccess, T data, String message) {
		this.isSuccess = isSuccess;
		this.data = data;
		this.message = message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean success) {
		isSuccess = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RuntimeException getException() {
		return exception;
	}

	public void setException(RuntimeException exception) {
		this.exception = exception;
	}

	public static <T> ResponseResult<T> create(boolean isSuccess, T data, String errorMessage) {
		return new ResponseResult<>(isSuccess, data, errorMessage);
	}
}
