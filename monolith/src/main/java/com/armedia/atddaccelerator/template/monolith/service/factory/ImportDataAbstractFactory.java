package com.armedia.atddaccelerator.template.monolith.service.factory;

import com.armedia.atddaccelerator.template.monolith.service.ImportDataService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImportDataAbstractFactory {
    private Map<String, ImportDataService> importServicesCache = new HashMap<>();

    private final List<ImportDataService> importDataServices;

    @PostConstruct
    public void initImportServicesCache() {
        importServicesCache = importDataServices.stream().collect(Collectors.toMap(ImportDataService::getType, Function.identity()));
    }

    public ImportDataService getImportDataService(String dataType) {
        ImportDataService service = importServicesCache.get(dataType);
        if (service == null) {
            throw new IllegalArgumentException("There is no import service for this type of files");
        }
        return service;
    }
}
