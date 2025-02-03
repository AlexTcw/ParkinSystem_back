package com.ps.back.model.entity;

import com.ps.back.model.entity.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TBLROL")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TblRol {

    @Id
    @Column(name = "CVEROL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cveRol;

    @Column(name = "NAMEROL", length = 200)
    @Enumerated(EnumType.STRING)
    private RoleEnum nameRol;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TBLROL_TBLPERMISSION",joinColumns = @JoinColumn(name = "CVEROL"),
            inverseJoinColumns = @JoinColumn(name = "CVEPERM"))
    private Set<TblPermission> tblPermissions = new HashSet<>();
}
