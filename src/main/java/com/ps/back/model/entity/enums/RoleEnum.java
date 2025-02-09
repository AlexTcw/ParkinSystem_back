package com.ps.back.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN(0.0),
    USER(10.0),
    GENERIC(15.0),
    STORAGE(20.0);


    private final double price;

}
