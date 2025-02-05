package com.ps.back.repository;

import com.ps.back.model.entity.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TblUserRepository extends JpaRepository<TblUser, Integer> {

    TblUser findTblUserByLoginUser(String username);

    TblUser findTblUserByEmailUser(String emailUser);

    boolean existsTblUserByEmailUser(String emailUser);

    boolean existsTblUserByLoginUser(String loginUser);

    boolean existsTblUserByCveUser(Long cveUser);

    TblUser findTblUserByCveUser(Long cveUser);

    @Modifying
    void deleteTblUserByCveUser(Long cveUser);

    @Query(value = """
                   select count(*) from (
                                          select u.cveuser from tbluser u
                                          inner join usu_car uc on u.cveuser = uc.cveuser                                   \s
                                          where uc.cveusucar = :cveUsuCar
                                      ) as uuc
                   """,nativeQuery = true)
    int existsTblUserWithCarPlate(@Param("cveUsuCar") String cveUsuCar);
}
