package com.example.quan_ly_kho.dto.request;

import com.example.quan_ly_kho.dto.ProviderDto;
import com.example.quan_ly_kho.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportReceiptRequest {
    private Long id;
    private Date importDate;
    private String description;
    private Long providerId;
}
