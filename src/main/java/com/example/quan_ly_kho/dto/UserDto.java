package com.example.quan_ly_kho.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String phone;
    private Date bornDate;
    private String username;
    private String address;
    private Boolean status;
    private BranchDto branch;
}
