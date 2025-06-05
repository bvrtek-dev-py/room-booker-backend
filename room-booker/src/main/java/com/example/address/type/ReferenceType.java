package com.example.address.type;

import com.example.address.constant.AddressConstant;

public enum ReferenceType {
    CENTER(AddressConstant.CENTER),
    COMPANY(AddressConstant.COMPANY);

    private final String value;

    ReferenceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
