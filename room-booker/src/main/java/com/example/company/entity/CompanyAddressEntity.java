package com.example.company.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import com.example.address.constant.AddressConstant;
import com.example.address.entity.AddressEntity;

@Entity
@DiscriminatorValue(AddressConstant.COMPANY)
public class CompanyAddressEntity extends AddressEntity {}
