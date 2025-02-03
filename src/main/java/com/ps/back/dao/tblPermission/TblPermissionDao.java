package com.ps.back.dao.tblPermission;

import com.ps.back.model.entity.TblPermission;

public interface TblPermissionDao {
    TblPermission createOrUpdateTblPermission(TblPermission tblPermission);

    boolean existsTblPermissionByNamePermission(String namePermission);

    TblPermission findTblPermissionByNamePermission(String namePermission);

    TblPermission createPermissionIfNotExists(String namePermission);
}
