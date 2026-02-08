package com.example.address.type;

import com.example.address.constant.AddressConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReferenceType {
    CENTER(AddressConstant.CENTER),
    COMPANY(AddressConstant.COMPANY);

    private final String value;
}
