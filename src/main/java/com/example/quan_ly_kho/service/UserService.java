package com.example.quan_ly_kho.service;

import com.example.quan_ly_kho.dto.UserDto;
import com.example.quan_ly_kho.dto.request.LoginRequest;
import com.example.quan_ly_kho.dto.request.RegisterRequest;
import com.example.quan_ly_kho.dto.request.UserRequest;

public interface UserService {
    String login(LoginRequest loginRequest);
    String register(RegisterRequest registerRequest);
    UserDto addUser(UserRequest userRequest);
    UserDto editUser(Long id,UserRequest userRequest);
    String deleteUser(Long id);
    String activeUser(Long id);
}
