package com.example.quan_ly_kho.service.Impl;

import com.example.quan_ly_kho.dto.UserDto;
import com.example.quan_ly_kho.dto.request.LoginRequest;
import com.example.quan_ly_kho.dto.request.RegisterRequest;
import com.example.quan_ly_kho.dto.request.UserRequest;
import com.example.quan_ly_kho.entity.Branch;
import com.example.quan_ly_kho.entity.Role;
import com.example.quan_ly_kho.entity.User;
import com.example.quan_ly_kho.exception.APIException;
import com.example.quan_ly_kho.exception.ResourceNotFoundException;
import com.example.quan_ly_kho.repository.BranchRepository;
import com.example.quan_ly_kho.repository.UserRepository;
import com.example.quan_ly_kho.security.JwtTokenProvider;
import com.example.quan_ly_kho.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BranchRepository branchRepository;
    private ModelMapper modelMapper;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Username is already exists!");
        }
        User user = new User();
        user.setName(registerRequest.getName());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setAddress(registerRequest.getAddress());
        user.setPhone(registerRequest.getPhone());
        user.setRole(Role.USER);
        userRepository.save(user);
        return "User registered successfully!.";
    }

    @Override
    public UserDto addUser(UserRequest userRequest) {
        if(userRepository.existsByUsername(userRequest.getUsername())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Username is already exists!");
        }
        Branch branch = branchRepository.findById(userRequest.getChiNhanh_id()).orElseThrow(
                ()->new ResourceNotFoundException("Branch","id", userRequest.getChiNhanh_id())
        );
        User user = new User();
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
        user.setRole(userRequest.getRole());
        user.setBranch(branch);
        UserDto userDto = modelMapper.map(userRepository.save(user),UserDto.class);
        return userDto;
    }

    @Override
    public UserDto editUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User","id", id)
        );
        Branch branch = branchRepository.findById(userRequest.getChiNhanh_id()).orElseThrow(
                ()->new ResourceNotFoundException("Branch","id", userRequest.getChiNhanh_id())
        );
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
        user.setRole(userRequest.getRole());
        user.setBranch(branch);
        return null;
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User","id", id)
        );
        user.setStatus(Boolean.FALSE);
        userRepository.save(user);
        return "Deleted user successfully!";
    }

    @Override
    public String activeUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User","id", id)
        );
        user.setStatus(Boolean.TRUE);
        userRepository.save(user);
        return "Actived user successfully!";
    }
}
