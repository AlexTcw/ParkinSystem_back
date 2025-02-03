package com.ps.back.model.pojos.response;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJsonLogin {

    private Long cveuser;
    private String nameUser;
    private String lastNameUser;
    private String emailUser;
    private String userNameUser;
    private Set<String> roles;
    private String token;
}
