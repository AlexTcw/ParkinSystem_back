package com.ps.back.dao.tblRegistry;

import com.ps.back.repository.TblRegistryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TblRegistryDaoImp implements TblRegistryDao {

    private final TblRegistryRepository tblRegistryRepository;
}
