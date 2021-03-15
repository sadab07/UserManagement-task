package com.example.User.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.mail.SimpleMailMessage;

import com.example.User.Management.dto.UserMngDto;
import com.example.User.Management.model.UserMngModel;

public interface UserMngRepository extends JpaRepository<UserMngModel, Long>{

	UserMngModel findByEmail(String email);
	
	UserMngModel findById(long gid);
	
	boolean existsByGid(long gid);
	
	boolean existsByGidAndPassword(long gid,String pass);
	
	UserMngModel findByGid(long gid);
	//UserMngModel editUserMngModel(UserMngDto dto);	
}
