package rest.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import grpc.chat.ChatServerGrpc;
import grpc.chat.ChatServerGrpc.ChatServerBlockingStub;
import grpc.chat.LoginRequest;
import grpc.chat.LoginResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import rest.chat.message.LoginResponseREST;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DelegateController {

	Logger logger = LoggerFactory.getLogger(DelegateController.class);

	@PostMapping("/login/username/{username}/password/{password}")
	public LoginResponseREST login(@PathVariable String username, @PathVariable String password) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8980).usePlaintext().build();
		ChatServerBlockingStub stub = ChatServerGrpc.newBlockingStub(channel);
		System.out.println(username);
		System.out.println(password);
		LoginResponse loginResponse = stub
				.login(LoginRequest.newBuilder().setUserName(username).setPassword(password).build());
		System.out.println(loginResponse);
		
		LoginResponseREST loginResponseREST = new LoginResponseREST(username, password, loginResponse.getStatus(), loginResponse.getToken());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
		headers.add("Access-Control-Allow-Credentials", "true");
		headers.add("Access-Control-Max-Age", "1800");
		headers.add("Access-Control-Allow-Headers", "content-type");
		headers.add("Access-Control-Allow-Methods", "PUT, POST, GET, DELETE, PATCH, OPTIONS");

		return loginResponseREST;
	}
}
