package com.example.center.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import com.example.address.constant.AddressConstant;
import com.example.address.entity.AddressEntity;

@Entity
@DiscriminatorValue(AddressConstant.CENTER)
public class CenterAddressEntity extends AddressEntity {}
