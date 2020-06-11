package com.amatistah.model;

public class ErrorResult {
	private String error;
	private Integer errorLevel;

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setErrorLevel(Integer errorLevel) {
		this.errorLevel = errorLevel;
	}

	public Integer getErrorLevel() {
		return errorLevel;
	}
}
