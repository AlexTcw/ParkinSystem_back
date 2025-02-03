package com.ps.back.dao.tblPermission;

import com.ps.back.model.entity.TblPermission;
import com.ps.back.repository.TblPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TblPermissionDaoImp implements TblPermissionDao {

    private final TblPermissionRepository tblPermissionRepository;

    @Override
    public TblPermission createOrUpdateTblPermission(TblPermission tblPermission) {
        return tblPermissionRepository.save(tblPermission);
    }

    @Override
    public boolean existsTblPermissionByNamePermission(String namePermission){
        return tblPermissionRepository.existsTblPermissionByNamePermission(namePermission);
    }

    @Override
    public TblPermission findTblPermissionByNamePermission(String namePermission){
        return tblPermissionRepository.findTblPermissionByNamePermission(namePermission);
    }

    @Override
    public TblPermission createPermissionIfNotExists(String namePermission){
        if(!existsTblPermissionByNamePermission(namePermission)){
            TblPermission permission = TblPermission.builder()
                    .namePermission(namePermission)
                    .build();
            tblPermissionRepository.save(permission);
            return permission;
        }
        return findTblPermissionByNamePermission(namePermission);
    }
}
