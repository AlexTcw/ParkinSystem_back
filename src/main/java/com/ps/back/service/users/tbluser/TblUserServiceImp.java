package com.ps.back.service.users.tbluser;

import com.ps.back.dao.tblrol.TblRolDao;
import com.ps.back.dao.tbluser.TblUserDao;
import com.ps.back.model.entity.TblUser;
import com.ps.back.model.pojos.consume.ConsumeJsonLogin;
import com.ps.back.model.pojos.consume.ConsumeJsonString;
import com.ps.back.model.pojos.consume.ConsumeJsonUser;
import com.ps.back.model.pojos.response.ResponseJsonLogin;
import com.ps.back.model.pojos.response.ResponseJsonString;
import com.ps.back.model.pojos.response.ResponseJsonUser;
import com.ps.back.service.users.jwt.JwtService;
import com.ps.back.service.util.ValidUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TblUserServiceImp implements TblUserService {

    private final TblUserDao tblUserDao;
    private final TblRolDao tblRolDao;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Override
    public ResponseJsonString bcrypt(ConsumeJsonString consume) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        new ResponseJsonString();
        return ResponseJsonString.builder().key(passwordEncoder.encode(consume.getKey())).build();
    }

    @Override
    public String bcrypt(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public ResponseJsonLogin login(ConsumeJsonLogin consume) {
        String username = consume.getUsername();
        String password = consume.getPassword();
        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        TblUser tblUser = tblUserDao.findTblUserByEmailUserOrUserName(username);
        String token = jwtService.getToken(tblUser);
        return fillResponseUserLogin(tblUser, token);
    }

    private ResponseJsonLogin fillResponseUserLogin(TblUser tblUser, String token) {
        return ResponseJsonLogin.builder()
                .cveuser(tblUser.getCveUser())
                .emailUser(tblUser.getEmailUser())
                .userNameUser(tblUser.getUsername())
                .nameUser(tblUser.getNameUser())
                .lastNameUser(tblUser.getLastNameUser())
                .roles(tblUser.getTblRoles().stream()
                        .map(role -> role.getNameRol().toString())
                        .collect(Collectors.toSet()))
                .token(token)
                .build();
    }

    @Override
    public ResponseJsonUser findUserByCveUser(Long cveUser) {
        ValidUtils.validateConsume(cveUser);
        TblUser user = tblUserDao.findTblUserByCveUser(cveUser);
        return fillResponseJsonUser(user,null);
    }

    @Override
    public ResponseJsonUser createOrUpdateUser(ConsumeJsonUser consume) {
        ValidUtils.validateConsume(consume);
        TblUser user = new TblUser();
        long cveUser = consume.getCveUser();
        Map<String,Object>extraData = new LinkedHashMap<>();
        if (cveUser > 0L) {
            user = tblUserDao.findTblUserByCveUser(consume.getCveUser());
        }

        user.setEmailUser(consume.getEmailUser());
        user.setGenderUser(consume.getGenderUser());
        user.setNameUser(consume.getNameUser());
        user.setLastNameUser(consume.getLastNameUser());
        user.setLoginUser(consume.getLoginUser());
        user.setPasswordUser(bcrypt(consume.getPasswordUser()));
        extraData.put("newPassword", consume.getPasswordUser());
        user.setTblRoles(consume.getRolSetUser().stream()
                .map(Integer::longValue)
                .map(tblRolDao::findTblRolByCveRol)
                .collect(Collectors.toSet()));

        user = tblUserDao.createOrUpdateTbluser(user);

        return fillResponseJsonUser(user,extraData);
    }

    private ResponseJsonUser fillResponseJsonUser(TblUser user, Map<String,Object> extraData) {
        return ResponseJsonUser.builder()
                .cveUser(user.getCveUser())
                .loginUser(user.getLoginUser())
                .emailUser(user.getEmailUser())
                .genderUser(user.getGenderUser())
                .nameUser(user.getNameUser())
                .lastNameUser(user.getLastNameUser())
                .isParked(true)
                .rolList(user.getTblRoles().stream()
                        .map(role -> role.getNameRol().toString())
                        .collect(Collectors.toSet()))
                .extraData(extraData)
                .build();
    }

    @Transactional
    @Override
    public ResponseJsonString deleteTblUserByCveUser(Long cveUser) {
        ValidUtils.validateConsume(cveUser);

        tblUserDao.deleteTblUserByCveUser(cveUser);

        return ResponseJsonString.builder()
                .key("User "+cveUser+" deleted successfully")
                .build();
    }
}
