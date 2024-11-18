package com.rutuja.shopResistrationn.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "shopresistration")
public class ShopResistrationModel {

    @Id
    @Column(value = "shopId")
    private Integer shopId;

    @Column(value = "shopName")
    private String shopName;

    @Column(value = "shopAddress")
    private String shopAddress;

    @Column(value = "shopOwnerName")
    private String shopOwnerName;


    @Column(value = "shopOwnerContact")
    private String shopOwnerContact;

}
