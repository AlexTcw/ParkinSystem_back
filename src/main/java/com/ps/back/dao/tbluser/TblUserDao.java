package com.ps.back.dao.tbluser;

import com.ps.back.model.entity.TblUser;

public interface TblUserDao {

    TblUser createOrUpdateTbluser(TblUser user);

    TblUser findTblUserByUsername(String username);

    TblUser findTblUserByEmailUser(String emailUser);

    TblUser findTblUserByCveUser(long cveUser);

    TblUser findTblUserByEmailUserOrUserName(String param);

    boolean existsTblUserByEmailUser(String emailUser);

    boolean existsTblUserByLoginUser(String loginUser);

    boolean existsTblUserByCveUser(Long cveUser);

    boolean existsTblUserByUsernameOrEmailUser(String param);

    void deleteTblUserByCveUser(Long cveUser);
}
