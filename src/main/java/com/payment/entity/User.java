package com.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
	
	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	private String username;
    private String password;
	private String role; // USER /ADMIN
	
	

}
