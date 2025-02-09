package com.ps.back.model.pojos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJsonRegistry {

    private long cveRegistry;
    private String parkingState;
    private String entryDate;
    private String exitDate;
    private BigDecimal total;
}
