package com.example.center.entity;

import com.example.address.constant.AddressConstant;
import com.example.address.entity.AddressEntity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue(AddressConstant.CENTER)
public class CenterAddressEntity extends AddressEntity {}