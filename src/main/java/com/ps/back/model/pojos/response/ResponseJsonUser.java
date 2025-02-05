package com.ps.back.model.pojos.response;

import lombok.*;

import java.util.Map;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseJsonUser {
    private Long cveUser = 0L;
    private String loginUser;
    private String emailUser;
    private String genderUser;
    private String nameUser;
    private String lastNameUser;
    private boolean isParked;
    private Set<String> rolList;
    private Map<String,Object> extraData;
}
