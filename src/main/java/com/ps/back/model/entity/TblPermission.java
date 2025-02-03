package com.ps.back.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBLPERMISSION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TblPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CVEPERM")
    private Long cvePerm;

    @Column(unique = true, nullable = false, updatable = false)
    private String namePermission;
}
