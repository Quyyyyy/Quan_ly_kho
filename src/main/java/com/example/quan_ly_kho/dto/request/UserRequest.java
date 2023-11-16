package com.example.quan_ly_kho.dto.request;

import com.example.quan_ly_kho.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String name;
    private String phone;
//    private Date bornDate;
    private String username;
    private String password;
    private String address;
    private String role;
    private Long ChiNhanh_id;
}
