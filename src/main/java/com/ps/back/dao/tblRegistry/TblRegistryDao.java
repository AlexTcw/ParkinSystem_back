package com.ps.back.dao.tblRegistry;

import com.ps.back.model.entity.TblRegistry;

public interface TblRegistryDao {

    boolean existTblRegistryByCveReg(long cveReg);

    TblRegistry findTblRegistryByCveReg(long cveReg);

    TblRegistry createOrUpdateTblRegistry(TblRegistry tblRegistry);

    TblRegistry findTblRegistryByCveUser(long cveUser);
}
