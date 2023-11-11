package com.example.quan_ly_kho.controller;

import com.example.quan_ly_kho.dto.JWTAuthResponse;
import com.example.quan_ly_kho.dto.request.LoginRequest;
import com.example.quan_ly_kho.dto.request.RegisterRequest;
import com.example.quan_ly_kho.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    @PostMapping(value={"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginRequest loginRequest){
        String token = userService.login(loginRequest);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value={"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        String response = userService.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
