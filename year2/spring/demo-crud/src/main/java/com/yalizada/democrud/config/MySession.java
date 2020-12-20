package com.yalizada.democrud.config;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

// bu sinif her login olan istifadeci ucun lazim olur,
// spring her istifadeci ucun bu sinifden bir obyekt yaradir
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS) 
public class MySession {
	private String message;
	
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	 
}
