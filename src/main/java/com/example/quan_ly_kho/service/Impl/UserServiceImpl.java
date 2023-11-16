package com.example.quan_ly_kho.service.Impl;

import com.example.quan_ly_kho.dto.ResultResponse;
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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
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
    public ResultResponse getAllUser(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<User> users = userRepository.findAll(pageable);
        List<User> listOUser = users.getContent();
        List<UserDto> contents = listOUser.stream()
                .map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setContent(contents);
        resultResponse.setPageNo(users.getNumber());
        resultResponse.setPageSize(users.getSize());
        resultResponse.setTotalElements(users.getTotalElements());
        resultResponse.setTotalPages(users.getTotalPages());
        resultResponse.setLast(users.isLast());
        return resultResponse;
    }

    @Override
    public UserDto createUser(UserRequest userRequest) {
        if(userRepository.existsByUsername(userRequest.getUsername())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Username is already exists!");
        }
        Branch branch = branchRepository.findById(userRequest.getBranchId()).orElseThrow(
                ()->new ResourceNotFoundException("Branch","id", userRequest.getBranchId())
        );
        User user = new User();
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());

        if(userRequest.getRole().equals("ADMIN")){
            user.setRole(Role.ADMIN);
        } else user.setRole(Role.USER);

        user.setBranch(branch);
        user.setStatus(Boolean.TRUE);
        User user1 = userRepository.save(user);
        return modelMapper.map(user1,UserDto.class);
    }

    @Override
    public UserDto editUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User","id", id)
        );
        Branch branch = branchRepository.findById(userRequest.getBranchId()).orElseThrow(
                ()->new ResourceNotFoundException("Branch","id", userRequest.getBranchId())
        );
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getRole().equals("ADMIN")){
            user.setRole(Role.ADMIN);
        } else user.setRole(Role.USER);

        user.setBranch(branch);
        User user1 = userRepository.save(user);
        return modelMapper.map(user1, UserDto.class);
    }

    @Override
    public UserDto deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User","id", id)
        );
        user.setStatus(Boolean.FALSE);
        User user1 = userRepository.save(user);
        return modelMapper.map(user1, UserDto.class);
    }

    @Override
    public UserDto activeUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User","id", id)
        );
        user.setStatus(Boolean.TRUE);
        User user1 = userRepository.save(user);
        return modelMapper.map(user1, UserDto.class);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User","id", id)
        );
        return modelMapper.map(user, UserDto.class);
    }
}
