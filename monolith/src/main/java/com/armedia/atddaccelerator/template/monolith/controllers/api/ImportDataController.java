package com.armedia.atddaccelerator.template.monolith.controllers.api;

import com.armedia.atddaccelerator.template.monolith.service.factory.ImportDataAbstractFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class ImportDataController {
    private final ImportDataAbstractFactory importDataFactory;

    @PostMapping
    //@RolesAllowed(Roles.ADMIN_USER)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));

        List<String> result = importDataFactory.getImportDataService(name).importData(file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
