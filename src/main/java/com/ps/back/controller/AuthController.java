package com.ps.back.controller;

import com.ps.back.model.pojos.consume.ConsumeJsonLogin;
import com.ps.back.model.pojos.response.ResponseJsonLogin;
import com.ps.back.service.users.tbluser.TblUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@RequestMapping(value = {"/auth"})
public class AuthController {

    private final TblUserService tblUserService;

    @PostMapping(value = {"/login"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonLogin> login(@RequestBody ConsumeJsonLogin consume) {
        return ResponseEntity.ok(tblUserService.login(consume));
    }
}
