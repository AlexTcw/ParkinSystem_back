package com.ps.back.repository;

import com.ps.back.model.entity.TblRol;
import com.ps.back.model.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblRolRepository extends JpaRepository<TblRol, Long> {

    boolean existsTblRolByNameRol(RoleEnum nameRol);

    TblRol findTblRolByNameRol(RoleEnum nameRol);

    TblRol findTblRolByCveRol(Long cveRol);
}
