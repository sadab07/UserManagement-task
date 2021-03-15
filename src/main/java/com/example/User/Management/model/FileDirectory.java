package com.example.User.Management.model;

import org.springframework.stereotype.Service;

@Service
public class FileDirectory {
	private String uploadDir;

	public FileDirectory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileDirectory(String uploadDir) {
		super();
		this.uploadDir = uploadDir;
	}

	@Override
	public String toString() {
		return "FileDirectory [uploadDir=" + uploadDir + "]";
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	
	
}
