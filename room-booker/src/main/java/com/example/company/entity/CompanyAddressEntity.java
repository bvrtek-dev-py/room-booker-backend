package com.example.company.entity;

import com.example.address.constant.AddressConstant;
import com.example.address.entity.AddressEntity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(AddressConstant.COMPANY)
public class CompanyAddressEntity extends AddressEntity {}