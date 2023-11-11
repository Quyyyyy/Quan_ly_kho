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
@Table(name="ChiTietPhieuNhap", schema = "dbo")
public class ImportReceiptDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="VatTu_ID")
    private Product product;
    @ManyToOne
    @JoinColumn(name="PhieuNhap_ID")
    private ImportReceipt importReceipt;
    @Column(name="so_luong")
    private Long quantity;
    @Column(name="gia_nhap")
    private Double price;
}
