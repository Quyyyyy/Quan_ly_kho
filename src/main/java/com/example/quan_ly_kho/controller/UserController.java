package com.example.quan_ly_kho.controller;

import com.example.quan_ly_kho.dto.ResultResponse;
import com.example.quan_ly_kho.dto.UserDto;
import com.example.quan_ly_kho.dto.request.UserRequest;
import com.example.quan_ly_kho.service.UserService;
import com.example.quan_ly_kho.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/users")
public class UserController {
    private UserService userService;

    @GetMapping
    public ResponseEntity<ResultResponse> getAllUsersByPage(
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        return ResponseEntity.ok(userService.getAllUser(pageNo, pageSize, sortBy, sortDir));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequest userRequest){
        UserDto userDto = userService.createUser(userRequest);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable("id") Long userId,
                                            @RequestBody UserRequest userRequest){
        UserDto userDto = userService.editUser(userId,userRequest);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long userId){
        UserDto userDto = userService.deleteUser(userId);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserDto> activeUser(@PathVariable("id") Long userId){
        UserDto userDto = userService.activeUser(userId);
        return ResponseEntity.ok(userDto);
    }

}
