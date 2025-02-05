package com.ps.back.controller;

import com.ps.back.model.pojos.consume.ConsumeJsonString;
import com.ps.back.model.pojos.consume.ConsumeJsonUser;
import com.ps.back.model.pojos.response.ResponseJsonString;
import com.ps.back.model.pojos.response.ResponseJsonUser;
import com.ps.back.service.users.tbluser.TblUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@RequestMapping(value = {"/users"})
public class TblUserController {

    private final TblUserService tblUserService;

    @PostMapping(value = {"/bcrypt"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonString> bcrypt(@RequestBody ConsumeJsonString consume) {
        return ResponseEntity.ok(tblUserService.bcrypt(consume));
    }

    @GetMapping(value = {"/findUserByCveUser"},produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonUser> findUserByCveUser(@RequestParam Long cveUser) {
        return ResponseEntity.ok(tblUserService.findUserByCveUser(cveUser));
    }

    @PostMapping(value = {"/createOrUpdateUser"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonUser> createOrUpdateUser(@RequestBody ConsumeJsonUser consume,
                                                               @RequestParam(required = false) Long cveUser) {
        if(cveUser != null) {
            if (cveUser>0){
                consume.setCveUser(cveUser);
            }
        }

        return ResponseEntity.ok(tblUserService.createOrUpdateUser(consume));
    }

    @DeleteMapping(value = {"/deleteUserByCveUser"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonString> deleteUserByCveUser(@RequestParam Long cveUser) {
        return ResponseEntity.ok(tblUserService.deleteTblUserByCveUser(cveUser));
    }

}
