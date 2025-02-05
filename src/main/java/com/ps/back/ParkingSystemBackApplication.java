package com.ps.back;

import com.ps.back.dao.tblPermission.TblPermissionDao;
import com.ps.back.dao.tblrol.TblRolDao;
import com.ps.back.dao.tbluser.TblUserDao;
import com.ps.back.model.entity.TblPermission;
import com.ps.back.model.entity.TblRol;
import com.ps.back.model.entity.TblUser;
import com.ps.back.model.entity.enums.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class ParkingSystemBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingSystemBackApplication.class, args);
	}

	@Bean
	CommandLineRunner init(TblUserDao tblUserDao, TblPermissionDao tblPermissionDao, TblRolDao tblRolDao) {
		return args -> {
			/*CREATE PERMISSIONS*/
			TblPermission cPermission = tblPermissionDao.createPermissionIfNotExists("CREATE");
			TblPermission rPermission = tblPermissionDao.createPermissionIfNotExists("READ");
			TblPermission uPermission = tblPermissionDao.createPermissionIfNotExists("UPDATE");
			TblPermission dPermission = tblPermissionDao.createPermissionIfNotExists("DELETE");

			/*CREATE ROLES*/
			TblRol roleAdmin = tblRolDao.createRoleIfNotExists(RoleEnum.ADMIN,
					Set.of(cPermission, rPermission, uPermission, dPermission));
			TblRol roleUser = tblRolDao.createRoleIfNotExists(RoleEnum.USER, Set.of(rPermission));
			TblRol roleGeneric = tblRolDao.createRoleIfNotExists(RoleEnum.GENERIC, Set.of(rPermission));
			TblRol roleStorage = tblRolDao.createRoleIfNotExists(RoleEnum.STORAGE, Set.of(rPermission));

			/*CREATE DEFAULT ADMIN USER*/
			if (!tblUserDao.existsTblUserByEmailUser("admonsp@parking.com")) {
				TblUser tblUserAdmin = TblUser.builder()
						.nameUser("ADMIN")
						.lastNameUser("DEFAULT")
						.genderUser("M")
						.emailUser("admonsp@parking.com")
						.cveUsuCar(Set.of("DEFAULT"))
						.loginUser("admonsp")
						.passwordUser("$2a$10$K.cb32QxY1K56v8vtuuUjuf7iGsA389PfVwSz2yTDhBQXoJGw8dC.")
						.tblRoles(Set.of(roleAdmin, roleUser,roleGeneric, roleStorage))
						.build();
				tblUserDao.createOrUpdateTbluser(tblUserAdmin);
			}
		};
	}
}
