package com.example.quan_ly_kho.service;

import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.dto.UserDto;
import com.example.quan_ly_kho.dto.request.LoginRequest;
import com.example.quan_ly_kho.dto.request.RegisterRequest;
import com.example.quan_ly_kho.dto.request.UserRequest;

import java.util.List;

public interface UserService {
    String login(LoginRequest loginRequest);
    String register(RegisterRequest registerRequest);
    ResultResponse getAllUser(int pageNo, int pageSize, String sortBy, String sortDir);
    UserDto createUser(UserRequest userRequest);
    UserDto editUser(Long id,UserRequest userRequest);
    UserDto deleteUser(Long id);
    UserDto activeUser(Long id);
    UserDto getUserById(Long id);
}
