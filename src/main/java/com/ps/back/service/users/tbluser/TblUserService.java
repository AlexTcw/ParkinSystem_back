package com.ps.back.service.users.tbluser;

import com.ps.back.model.pojos.consume.ConsumeJsonLogin;
import com.ps.back.model.pojos.consume.ConsumeJsonString;
import com.ps.back.model.pojos.consume.ConsumeJsonUser;
import com.ps.back.model.pojos.response.ResponseJsonLogin;
import com.ps.back.model.pojos.response.ResponseJsonString;
import com.ps.back.model.pojos.response.ResponseJsonUser;

public interface TblUserService {

    ResponseJsonString bcrypt(ConsumeJsonString consume);

    String bcrypt(String password);

    ResponseJsonLogin login(ConsumeJsonLogin consume);

    ResponseJsonUser findUserByCveUser(Long cveUser);

    ResponseJsonUser createOrUpdateUser(ConsumeJsonUser consume);

    ResponseJsonString deleteTblUserByCveUser(Long cveUser);
}
