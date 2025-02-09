package com.ps.back.dao.tblRegistry;

import com.ps.back.model.entity.TblRegistry;
import com.ps.back.repository.TblRegistryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TblRegistryDaoImp implements TblRegistryDao {

    private final TblRegistryRepository tblRegistryRepository;

    @Override
    public boolean existTblRegistryByCveReg(long cveReg) {
        return tblRegistryRepository.existsTblRegistryByCveReg(cveReg);
    }

    @Override
    public TblRegistry findTblRegistryByCveReg(long cveReg) {
        return tblRegistryRepository.findTblRegistryByCveReg(cveReg);
    }

    @Override
    public TblRegistry createOrUpdateTblRegistry(TblRegistry tblRegistry) {
        return tblRegistryRepository.save(tblRegistry);
    }

    @Override
    public TblRegistry findTblRegistryByCveUser(long cveUser) {
        return tblRegistryRepository.findTblRegistryByCveUser(cveUser);
    }
}
