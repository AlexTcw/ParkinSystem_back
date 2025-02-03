package com.ps.back.repository;

import com.ps.back.model.entity.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface TblUserRepository extends JpaRepository<TblUser, Integer> {

    TblUser findTblUserByLoginUser(String username);

    TblUser findTblUserByEmailUser(String emailUser);

    boolean existsTblUserByEmailUser(String emailUser);

    boolean existsTblUserByLoginUser(String loginUser);

    boolean existsTblUserByCveUser(Long cveUser);

    TblUser findTblUserByCveUser(Long cveUser);

    @Modifying
    void deleteTblUserByCveUser(Long cveUser);
}
