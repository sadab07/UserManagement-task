package com.example.User.Management.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.User.Management.dto.UserMngDto;
import com.example.User.Management.model.Response;
import com.example.User.Management.model.UserMngModel;
import com.example.User.Management.service.UserMngService;
@RestController
//@RequestMapping("/User")
public class UserMngController {

	@Autowired
	UserMngService service;
	
	@PostMapping("/create")
	public ResponseEntity<?> createGroup(@RequestBody @Valid UserMngDto dto) throws AddressException, MessagingException, IOException
	{
		return service.creategroup(dto);
		
	}
	
	@GetMapping("/getuser/{gid}")
	public ResponseEntity<?> getByid(@PathVariable long gid){
		
		return service.getuser(gid);
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(){
		
		return service.getall();
	}
	
	
	
	@DeleteMapping("/delete/{gid}")
	public ResponseEntity<?> deletegroup(@PathVariable long gid){
		return service.deleteuser(gid);
	}
	
	
	
	@DeleteMapping("/deletemulti")
	public ResponseEntity<?> deleteGroups(@RequestParam long[]gid){
		return service.deletemulti(gid);
	}	
	
	
//	@PutMapping("/update")
//	public ResponseEntity<?> updategroup(@RequestBody UserMngDto dto){
//		return service.updateuser(dto);
//	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserMngDto dto){
		return service.updateuser(dto);
	}
	
	
	@PutMapping("/updatepassword")
	public ResponseEntity<?> updatepassword(@RequestParam long gid,@RequestParam String newPassword,@RequestParam  String confirmPass,@RequestParam String currentPass) throws UnsupportedEncodingException, MessagingException
	{
		return service.updatepassword(gid, newPassword,confirmPass,currentPass);
	}
	
	@PostMapping("/uploadFile")
    public Response uploadFile(@RequestParam("file") MultipartFile file) {
       service.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(file.getOriginalFilename())
                .toUriString();
        System.err.println(fileDownloadUri);
        return new Response(file.getOriginalFilename(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String gid,@RequestParam String pass) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		return ResponseEntity.ok(service.checkLogin(gid,pass).getBody());	
	}
}

