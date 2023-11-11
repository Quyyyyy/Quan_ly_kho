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
@Table(name="PhieuNhap", schema = "dbo")
public class ImportReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="ngay_nhap")
    private Date importDate;
    @Column(name="mo_ta",columnDefinition = "NVARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS")
    private String description;
    @ManyToOne
    @JoinColumn(name="NhanVien_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name="NhaCungCap_ID")
    private Provider provider;
}
