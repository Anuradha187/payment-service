package com.payment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
}
