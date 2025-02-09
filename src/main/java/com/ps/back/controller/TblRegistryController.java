package com.ps.back.controller;

import com.ps.back.model.entity.TblRegistry;
import com.ps.back.model.pojos.consume.ConsumeJsonLong;
import com.ps.back.model.pojos.response.ResponseJsonRegistry;
import com.ps.back.service.registry.TblRegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@RequestMapping(value = {"/registry"})
public class TblRegistryController {

    private final TblRegistryService tblRegistryService;

    @PostMapping(value = "createOrUpdateRegistryByType", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseJsonRegistry> createOrUpdateRegistryByType(@RequestBody ConsumeJsonLong consume) {
        return ResponseEntity.ok(tblRegistryService.createOrUpdateRegistryByCveUser(consume));
    }
}
