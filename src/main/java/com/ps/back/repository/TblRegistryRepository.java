package com.ps.back.repository;

import com.ps.back.model.entity.TblRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TblRegistryRepository extends JpaRepository<TblRegistry, Integer> {

    TblRegistry findTblRegistryByCveReg(Long cveReg);

    boolean existsTblRegistryByCveReg(Long cveReg);

    @Query(value = """
            SELECT r.* \s
            FROM tblregistry r
            INNER JOIN usu_reg ur ON ur.cvereg = r.cvereg
            INNER JOIN tbluser u ON ur.cveuser = u.cveuser
            WHERE u.cveuser = :cveUser
            AND r.cvereg = (
                SELECT MAX(r2.cvereg)\s
                FROM tblregistry r2
                INNER JOIN usu_reg ur2 ON ur2.cvereg = r2.cvereg
                WHERE ur2.cveuser = u.cveuser
            )
           \s""", nativeQuery = true)
    TblRegistry findTblRegistryByCveUser(@Param("cveUser") long cveUser);
}
