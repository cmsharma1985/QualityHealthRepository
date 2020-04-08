package com.sharecare.qualityhealth.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 166027954218631039L;
	private String message;

}
