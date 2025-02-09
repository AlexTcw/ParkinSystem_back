package com.ps.back.model.entity;

import com.ps.back.model.entity.enums.ParkingStateEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBLREGISTRY")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TblRegistry {

    @Id
    @Column(name = "CVEREG")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cveReg;

    @Column(name = "PARKINGSTATE")
    @Enumerated(EnumType.STRING)
    private ParkingStateEnum parkingState;

    @Column(name = "ENTRYDATE")
    private LocalDateTime entrydate;

    @Column(name = "EXITDATE")
    private LocalDateTime exitdate;

    @Column(name = "TOTAL", precision = 10, scale = 2)
    private BigDecimal total;

}
