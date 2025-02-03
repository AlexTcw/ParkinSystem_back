package com.ps.back.dao.tblrol;

import com.ps.back.model.entity.TblPermission;
import com.ps.back.model.entity.TblRol;
import com.ps.back.model.entity.enums.RoleEnum;

import java.util.Set;

public interface TblRolDao {
    boolean existsTblRolByNameRol(RoleEnum role);

    TblRol findTblRolByNameRol(RoleEnum role);

    TblRol findTblRolByCveRol(Long cveRol);

    TblRol createOrUpdateTblRol(TblRol tblRol);

    TblRol createRoleIfNotExists(RoleEnum role, Set<TblPermission> permissions);

}
