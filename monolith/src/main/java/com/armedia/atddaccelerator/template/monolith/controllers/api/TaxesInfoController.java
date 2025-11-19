package com.armedia.atddaccelerator.template.monolith.controllers.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequestMapping("/api/taxes/info")
@RestController
public class TaxesInfoController {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${frs.host}")
    private String api;
    @Value("${frs.access-key}")
    private String accessKey;

    @GetMapping
    public ResponseEntity<String> taxesInfo() {
        try {
            String result = restTemplate.getForObject(api + "/v1/taxes?access_key=" + accessKey, String.class);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            log.warn("Exception: ", ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}
