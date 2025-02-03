package com.ps.back.repository;

import com.ps.back.model.entity.TblPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblPermissionRepository extends JpaRepository<TblPermission, Long> {

    boolean existsTblPermissionByNamePermission(String namePermission);

    TblPermission findTblPermissionByNamePermission(String namePermission);
}
