package com.yalizada.democrud.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Length(min=2, message="Minimum 2, maksimum 20 simvol yazmaq lazimdir", max=20)
	private String name;
	@Length(min=2, message="Minimum 2, maksimum 20 simvol yazmaq lazimdir", max=20)
	@Pattern(regexp="[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
	private String email;
	private String imagePath;
private String username;

	 
	public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", imagePath=" + imagePath + "]";
	}

	// standard constructors / setters / getters / toString
}