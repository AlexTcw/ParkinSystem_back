package com.ps.back.model.pojos.consume;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumeJsonUser {
    private Long cveUser = 0L;
    private String loginUser;
    private String nameUser;
    private String lastNameUser;
    private String emailUser;
    private String passwordUser;
    private String genderUser;
    private Set<Integer> rolSetUser;
}
