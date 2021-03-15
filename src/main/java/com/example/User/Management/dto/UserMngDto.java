package com.example.User.Management.dto;

public class UserMngDto {
	
	long gid;
	String name;
	String address;
	String email;
	String img;
	String password;
	
	public long getGid() {
		return gid;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getEmail() {
		return email;
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
	public void setAddress(String address) {
		this.address = address;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
