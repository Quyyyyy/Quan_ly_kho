package com.example.quan_ly_kho.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="[NhanVien]", schema = "[dbo]")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="ho_ten",columnDefinition = "NVARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS")
    private String name;
    @Column(name="so_dien_thoai")
    private String phone;
    @Column(name="tai_khoan")
    private String username;
    @Column(name="mat_khau")
    private String password;
    @Column(name="dia_chi",columnDefinition = "NVARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS")
    private String address;
    @Column(name="ngay_sinh")
    private Date born;
    @Column(name="trang_thai")
    private Boolean status = Boolean.TRUE;
    @Column(name="chuc_vu")
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToOne
    @JoinColumn(name="ChiNhanh_ID")
    private Branch branch;
}
