package com.example.User.Management.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table
public class UserMngModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long gid;
	
	@NotEmpty(message = "name should not be blank")
	@Min(8)
	@Max(15)
	String name;
	String email;
	String address;
	@JsonIgnore
	String password;
	@JsonIgnore
	String img;
	int attempts = 0;
	
	
	public long getGid() {
		return gid;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getAddress() {
		return address;
	}
	public String getPassword() {
		return password;
	}
	public String getImg() {
		return img;
	}
	public void setGid(long gid) {
		this.gid = gid;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setImg(String img) {
		this.img = img;
	}
	

	public UserMngModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	@Override
	public String toString() {
		return "UserMngModel [gid=" + gid + ", name=" + name + ", email=" + email + ", address=" + address
				+ ", password=" + password + ", img=" + img + ", attempts=" + attempts + "]";
	}
	public UserMngModel(long gid, String name, String email, String address, String password, String img,
			int attempts) {
		super();
		this.gid = gid;
		this.name = name;
		this.email = email;
		this.address = address;
		this.password = password;
		this.img = img;
		this.attempts = attempts;
	}
	
	
	
}
