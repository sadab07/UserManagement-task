package com.example.User.Management.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.User.Management.dto.UserMngDto;
import com.example.User.Management.model.UserMngModel;

public interface UserMngService {

	public ResponseEntity<?> creategroup(UserMngDto dto) throws AddressException, MessagingException, IOException ;
	
	public ResponseEntity<?> getuser(long gid);
	public ResponseEntity<?> getall();
	
	public ResponseEntity<?> deleteuser(long gid);
	
	
	public ResponseEntity<?> updateuser(UserMngDto dto);
	public ResponseEntity<?> updatepassword(long gid,String newPass,String confirmPass,String currentPass) throws UnsupportedEncodingException, MessagingException;
	
//	public void sendMail(String recipient,String name) throws AddressException, MessagingException, IOException;

	public void sendmail(String recipient, String name) throws MessagingException, UnsupportedEncodingException;
	public ResponseEntity<?> deletemulti(long[] gid);
	
	public UserMngModel storeFile(MultipartFile file);
	
	public ResponseEntity<?> checkLogin(String gid,String pass);

	public void sendmail(String email, String name, String newPass, String message)
			throws MessagingException, UnsupportedEncodingException;

	
}
