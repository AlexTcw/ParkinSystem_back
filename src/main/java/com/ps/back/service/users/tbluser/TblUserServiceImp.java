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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
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

        Set<String> carSetUser = consume.getCarSetUser().isEmpty()
                ? new HashSet<>() : consume.getCarSetUser();

        if (!carSetUser.isEmpty()) {
            validateCarSetPlate(carSetUser, consume.getRolSetUser());
        }

        user.setEmailUser(consume.getEmailUser());
        user.setGenderUser(consume.getGenderUser());
        user.setNameUser(consume.getNameUser());
        user.setLastNameUser(consume.getLastNameUser());
        user.setLoginUser(consume.getLoginUser());
        user.setPasswordUser(bcrypt(consume.getPasswordUser()));
        user.setTblRoles(consume.getRolSetUser().stream()
                .map(Integer::longValue)
                .map(tblRolDao::findTblRolByCveRol)
                .collect(Collectors.toSet()));
        user.setCveUsuCar(carSetUser);

        extraData.put("newPassword", consume.getPasswordUser());
        extraData.put("carUser", carSetUser);

        user = tblUserDao.createOrUpdateTbluser(user);

        return fillResponseJsonUser(user,extraData);
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

    private boolean validateCarPlate(String plate) {
        String carPlatePattern = "^[A-Z]{3}-?\\d{3}$";
        Pattern pattern = Pattern.compile(carPlatePattern);
        return pattern.matcher(plate).matches();
    }

    private void validateCarSetPlate(Set<String> carSetUser, Set<Integer> rolSetUser) {
        rolSetUser.forEach(rol -> {log.debug(rol.toString());});

        if (!(rolSetUser.contains(4) || rolSetUser.contains(1))) {
            throw new BadCredentialsException("You are not allowed to add car set user");
        }
        boolean allValid = carSetUser.stream()
                .allMatch(this::validateCarPlate);
        if (!allValid) {
            throw new IllegalArgumentException("Some car plate is not valid");
        }
        carSetUser.forEach(carPlate -> {
            if(tblUserDao.existsTblUserWithCarPlate(carPlate)){
                throw new IllegalArgumentException("Car plate already exists");
            }
        });

    }
}
