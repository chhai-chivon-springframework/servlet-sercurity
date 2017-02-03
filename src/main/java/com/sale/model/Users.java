package com.sale.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Users {
	@JsonProperty("ID")
	private Long id ;
	@JsonProperty("NAME")
	private String username;
	@JsonProperty("PASSWORD")
	private String password;
	
	public Users(){
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	public Users(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
}
