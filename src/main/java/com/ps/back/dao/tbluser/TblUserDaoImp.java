package com.ps.back.dao.tbluser;

import com.ps.back.model.entity.TblUser;
import com.ps.back.model.exception.ResourceNotFoundException;
import com.ps.back.repository.TblUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TblUserDaoImp implements TblUserDao {

    private final TblUserRepository tblUserRepository;

    @Override
    public TblUser createOrUpdateTbluser(TblUser user){
        return tblUserRepository.save(user);
    }

    @Override
    public boolean existsTblUserByEmailUser(String emailUser){
        return tblUserRepository.existsTblUserByEmailUser(emailUser);
    }

    @Override
    public boolean existsTblUserByLoginUser(String loginUser){
        return tblUserRepository.existsTblUserByLoginUser(loginUser);
    }

    @Override
    public boolean existsTblUserByCveUser(Long cveUser){
        return tblUserRepository.existsTblUserByCveUser(cveUser);
    }

    @Override
    public boolean existsTblUserByUsernameOrEmailUser(String param) {
        return tblUserRepository.existsTblUserByEmailUser(param) || tblUserRepository.existsTblUserByLoginUser(param);
    }

    @Override
    public void deleteTblUserByCveUser(Long cveUser) {
        if (!tblUserRepository.existsTblUserByCveUser(cveUser)) {
            throw new ResourceNotFoundException("There is no user with cveUser: "+cveUser);
        }
        tblUserRepository.deleteTblUserByCveUser(cveUser);
    }

    @Override
    public boolean existsTblUserWithCarPlate(String plate) {
        return tblUserRepository.existsTblUserWithCarPlate(plate)>0;
    }

    @Override
    public TblUser findTblUserByUsername(String username) {
        return tblUserRepository.findTblUserByLoginUser(username);
    }

    @Override
    public TblUser findTblUserByEmailUser(String emailUser){
        return tblUserRepository.findTblUserByEmailUser(emailUser);
    }

    @Override
    public TblUser findTblUserByCveUser(long cveUser){
        if (existsTblUserByCveUser(cveUser)){
            return tblUserRepository.findTblUserByCveUser(cveUser);
        }
        throw new ResourceNotFoundException("There is no user with cveUser: "+cveUser);
    }

    @Override
    public TblUser findTblUserByEmailUserOrUserName(String param) {
        log.warn("finding User");
        if (existsTblUserByEmailUser(param)){
            log.info("User find by email");
            return tblUserRepository.findTblUserByEmailUser(param);
        } else if (existsTblUserByLoginUser(param)) {
            log.info("User find by username");
            return tblUserRepository.findTblUserByLoginUser(param);
        } else {
            throw new ResourceNotFoundException("There is no user with those credentials");
        }
    }

}
