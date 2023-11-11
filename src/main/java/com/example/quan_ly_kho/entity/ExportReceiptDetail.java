package com.example.quan_ly_kho.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ChiTietPhieuXuat", schema = "dbo")
public class ExportReceiptDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="VatTu_ID")
    private Product product;
    @ManyToOne
    @JoinColumn(name="PhieuXuat_ID")
    private ExportReceipt exportReceipt;
    @Column(name="so_luong")
    private Long quantity;
    @Column(name="gia_xuat")
    private Double price;
}
