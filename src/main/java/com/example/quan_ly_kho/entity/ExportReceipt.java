package com.example.quan_ly_kho.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PhieuXuat", schema = "dbo")
public class ExportReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="ngay_xuat")
    private Date exportDate;
    @Column(name="mo_ta",columnDefinition = "NVARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS")
    private String description;
    @ManyToOne
    @JoinColumn(name="NhanVien_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name="KhachHang_ID")
    private Customer customer;
}
