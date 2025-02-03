package com.ps.back.model.pojos.consume;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsumeJsonLogin {
    private String username;
    private String password;
}
