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
@Table(name="VatTu_ChiNhanh", schema = "dbo")
public class ProductBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="ChiNhanh_ID")
    private Branch branch;
    @ManyToOne
    @JoinColumn(name="VatTu_ID")
    private Product product;
    @Column(name="so_luong")
    private Long quantity;
}

