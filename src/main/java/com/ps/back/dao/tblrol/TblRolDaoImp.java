package com.ps.back.dao.tblrol;

import com.ps.back.model.entity.TblPermission;
import com.ps.back.model.entity.TblRol;
import com.ps.back.model.entity.enums.RoleEnum;
import com.ps.back.repository.TblRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class TblRolDaoImp implements TblRolDao {

    private final TblRolRepository tblRolRepository;

    @Override
    public boolean existsTblRolByNameRol(RoleEnum role){
        return tblRolRepository.existsTblRolByNameRol(role);
    }

    @Override
    public TblRol findTblRolByNameRol(RoleEnum role){
        return tblRolRepository.findTblRolByNameRol(role);
    }

    @Override
    public TblRol findTblRolByCveRol(Long cveRol) {
        return tblRolRepository.findTblRolByCveRol(cveRol);
    }

    @Override
    public TblRol createOrUpdateTblRol(TblRol tblRol){
        return tblRolRepository.save(tblRol);
    }

    @Override
    public TblRol createRoleIfNotExists(RoleEnum role, Set<TblPermission> permissions) {
        if (!existsTblRolByNameRol(role)){
            TblRol tblRol = TblRol.builder()
                    .nameRol(role)
                    .tblPermissions(permissions)
                    .build();
            return tblRolRepository.save(tblRol);
        }
        return findTblRolByNameRol(role);
    }
}
