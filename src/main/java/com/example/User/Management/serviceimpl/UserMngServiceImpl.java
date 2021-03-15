package com.example.User.Management.serviceimpl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.User.Management.dto.UserMngDto;
import com.example.User.Management.model.UserMngModel;
import com.example.User.Management.model.FileDirectory;
import com.example.User.Management.model.Response;
import com.example.User.Management.repository.UserMngRepository;
import com.example.User.Management.service.UserMngService;

@Service
public class UserMngServiceImpl implements UserMngService {

	@Autowired
	UserMngRepository repository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private FileDirectory directory;
	
	private static final String LOCATION = "D://files";
	
	ModelMapper mapper = new ModelMapper();
	// Response res = new Response();
	
	@Override
	public ResponseEntity<?> creategroup(UserMngDto dto) throws AddressException, MessagingException, IOException {
		UserMngModel id = new UserMngModel();
		String name = dto.getName();
		Map<String, Object> map = new HashMap<String, Object>();

		String address = dto.getAddress();
		String email = dto.getEmail();
		String password = dto.getPassword();

		if (dto.getGid() > 0) {
			id.setGid(dto.getGid());
		}

		String regex = "^[a-zA-Z]*$";
		if (name.isEmpty()) {
			map.put("code", 404);
			map.put("message", "name not valid");
			map.put("name", name);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);

		}
		if (address.isEmpty()) {
			map.put("code", 404);
			map.put("message", "address not valid");
			map.put("name", address);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

		boolean emailValid = email.matches(emailRegex);

		if (!(emailValid)) {
			map.put("code", 404);
			map.put("message", "Email is not Valid.");
			map.put("status", false);
			map.put("email", email);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		if (email.isEmpty()) {
			map.put("code", 404);
			map.put("message", "not valid... ");
			map.put("email", email);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);

		}

		UserMngModel detail = repository.findByEmail(email);

		if (null != detail) {
			map.put("code", 404);
			map.put("message", "Email is Already Exist.");
			map.put("status", false);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		String passwordregex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$";

		boolean passwordvalid = password.matches(passwordregex);

		if (!(passwordvalid)) {
			map.put("code", 404);
			map.put("message", "password is not Valid.");
			map.put("status", false);
			map.put("email", password);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		if (password.isEmpty()) {
			map.put("code", 404);
			map.put("message", "not valid... ");
			map.put("email", password);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		else {
			UserMngModel model = mapper.map(dto, UserMngModel.class);

			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("name", dto.getName());
			map1.put("Address", dto.getAddress());
			map1.put("email", dto.getEmail());
			// map1.put("password", dto.getPassword());

			map.put("code", 200);
			map.put("message", "data inserted...");
			map.put("status", true);
			map.put("data", repository.save(model));
			sendmail(dto.getEmail(),dto.getName());
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}

	}

	@Override
	public ResponseEntity<?> getuser(long gid) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserMngModel mngModel = (UserMngModel) repository.findById(gid);
		if (null == mngModel) {
			map.put("code", 404);
			map.put("message", "user does not found");
			map.put("status", false);
			map.put("id", gid);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		} else {
			repository.findById(gid);
			map.put("code", 200);
			map.put("Data", repository.findById(gid));
			map.put("message", "found user Successfully");
			map.put("status", true);
			return ResponseEntity.ok(map);
		}

		// TODO Auto-generated method stub
	}

	@Override
	public ResponseEntity<?> getall() {
		List<UserMngModel> list = repository.findAll();
		LinkedHashMap<String, Object> datamap = new LinkedHashMap<String, Object>();
		datamap.put("code", 200);
		datamap.put("status", true);
		datamap.put("message", "GetAll User....");
		datamap.put("data", list);
		return ResponseEntity.ok(datamap);

//		List<UserMngDto> userDetaillist = list.stream().map(UserMngDto::new)
//			.collect(Collectors.toCollection(ArrayList::new));

		// TODO Auto-generated method stub
	}

	@Override
	public ResponseEntity<?> deleteuser(long gid) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserMngModel mngModel = (UserMngModel) repository.findById(gid);
		if (null == mngModel) {
			map.put("code", 404);
			map.put("message", "user does not found");
			map.put("status", false);
			map.put("id", gid);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		} else {
			repository.deleteById(gid);
			map.put("code", 200);
			map.put("Data", gid);
			map.put("message", "Delete Successfully");
			map.put("status", true);
			return ResponseEntity.ok(map);
		}
		// TODO Auto-generated method stub

	}

	public ResponseEntity<?> deletemulti(long[] gid) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		for (long id : gid) {

			if (!repository.existsById(id)) {
				map.put("code", 404);
				map.put("message", "User_id does not found");
				map.put("status", false);
				map.put("user_id does not found", id);
				return ResponseEntity.ok(map);
			} else {
				map.put("code", 200);
				map.put("message", "Delete Successfully");
				map.put("status", true);
				map.put("deleted_data", gid);
				repository.deleteById(id);

			}
		}

		return ResponseEntity.ok(map);
	}

	@Override
	public ResponseEntity<?> updateuser(UserMngDto dto) {
		ModelMapper mapper = new ModelMapper();
		UserMngModel mngModel = mapper.map(dto, UserMngModel.class);
		Map<String, Object> map = new HashMap<String, Object>();
		if (repository.existsByGid(dto.getGid())) {
			repository.save(mngModel);
			map.put("message", "User updated successfully");
			map.put("code", 200);
			map.put("status", true);
			map.put("data", dto);
		} else {
			map.put("message", "User Does not found");
			map.put("code", 404);
			map.put("status", false);
		}
		return ResponseEntity.ok(map);
//		UserMngModel mngModel = (UserMngModel) repository.editUserMngModel(dto);
//		repository.save(mngModel);

//		if(mngModel != null)
//		{
//		repository.editUserMngModel(dto);
//			map.put("message",  "User updated successfully");
//			map.put("code", 200);
//			map.put("status", true);
//			map.put("data", repository.editUserMngModel(dto));
//		}
//		else
//		{
//			map.put("message",  "User Does not found");
//			map.put("code", 404);
//			map.put("status", false);
//			map.put("data", repository.editUserMngModel(dto));
//			
//	}
//		return ResponseEntity.ok(map);
//		return null;
	}

	@Override
	public ResponseEntity<?> updatepassword(long gid, String newPass, String confirmPass, String currentPass) throws UnsupportedEncodingException, MessagingException {
		UserMngModel mngModel = repository.getOne(gid);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Map<String, Object> passMap = new LinkedHashMap<String, Object>();
		passMap.put("gid", gid);
		passMap.put("current-password", currentPass);
		passMap.put("new-password", newPass);
		passMap.put("confirm-password", confirmPass);

		if (repository.existsByGid(gid)) {
			if (newPass.equals(confirmPass)) {
				if (mngModel.getPassword().equals(newPass)) {
					map.put("message", "current passsword should not be same as before...");
					map.put("code", 404);
					map.put("status", false);
					map.put("data", passMap);
				} else {
					mngModel.setPassword(newPass);
					repository.save(mngModel);
					String message = "Password updated successfully";
					sendmail(mngModel.getEmail(), mngModel.getName(),newPass,message);
					map.put("message", message);
					map.put("code", 200);
					map.put("status", true);
					map.put("data", passMap);
				}
			} else {
				map.put("message", "Password and confirm password should be same");
				map.put("code", 404);
				map.put("status", false);
				map.put("data", passMap);
			}
		} else {
			map.put("message", "User Does not found");
			map.put("code", 404);
			map.put("status", false);
		}
		return ResponseEntity.ok(map);
	}

	// @Override
//	public void sendmail(String recipient, String name) {
//		SimpleMailMessage msg = new SimpleMailMessage();
//		Object mimeMessage = null;
//		MimeMessageHelper helper = new MimeMessageHelper((MimeMessage) mimeMessage, "utf-8");
//		String htmlMsg = "<h3>Hello World!</h3>";
//	
//		MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
//        String htmlMsg = "<body style='border:2px solid black'>"
//                    +"Your onetime password for registration is  " 
//                        + "Please use this OTP to complete your new user registration."+
//                          "OTP is confidential, do not share this  with anyone.</body>";
//        message.setContent(htmlMsg, "text/html");

//	 msg.setTo(recipient);
//	 msg.setSubject("Welcome...... "+name);
//	 msg.setText("Hello "+name);

//	 javaMailSender.send(msg);
	@Override
	public void sendmail(String recipient, String name) throws MessagingException, UnsupportedEncodingException {

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setTo(recipient);
		msg.setSubject("User Management Welcomes You");
		msg.setText("You are Sign up with Username" + " :- " + name);

		javaMailSender.send(msg);
	}
	
	@Override
	public void sendmail(String email,String name,String newPass,String message) throws MessagingException, UnsupportedEncodingException {
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setTo(email);
		msg.setSubject("Password change alert");
		msg.setText("Hey " + name + ", " + message + " and your password is : " + newPass);

		javaMailSender.send(msg);
	}

	@Override
	public UserMngModel storeFile(MultipartFile file) {
		UserMngModel mngModel = new UserMngModel();
		
		Path path = Paths.get(LOCATION).toAbsolutePath().normalize();
		System.err.println(path);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		try {
////			System.err.println(Files.createDirectories(LOCATION));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.err.println(fileName);
		return null;
		
		
	}

	@Override
	public ResponseEntity<?> checkLogin(String gid, String pass) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Map<String, Object> credMap = new LinkedHashMap<String, Object>();
		credMap.put("gid", gid);
		credMap.put("password", pass);

		UserMngModel mngModel = repository.findByGid(Long.parseLong(gid));
		boolean check = repository.existsByGidAndPassword(Long.parseLong(gid), pass);

		if (mngModel != null) {
			int attempt = mngModel.getAttempts();
			if (attempt == 3) {
				if(check) {
					mngModel.setAttempts(0);
					repository.save(mngModel);
					map.put("login-success", "Login successfully...");
					map.put("code", 200);
					map.put("status", true);
					map.put("data", credMap);
				}
				else {
					map.put("login-error", "Your account is blocked");
					map.put("code", 404);
					map.put("status", false);
					map.put("data", credMap);
				}
			}
			else if (check) {
				map.put("login-success", "Login successfully...");
				map.put("code", 200);
				map.put("status", true);
				map.put("data", credMap);
			} else {
				map.put("login-error", "Incorrect password...");
				map.put("code", 404);
				map.put("status", false);
				map.put("data", credMap);
				map.put("attempts-remaining", 3-(mngModel.getAttempts()) + " out of 3");
				if (attempt >= 0 && attempt < 3) {
					attempt += 1;
					mngModel.setAttempts(attempt);
					repository.save(mngModel);
				}
			}
		} else {
			map.put("login-error", "User does not exists...");
			map.put("code", 404);
			map.put("status", false);
			map.put("data", credMap);
		}
		return ResponseEntity.ok(map);
	}
}