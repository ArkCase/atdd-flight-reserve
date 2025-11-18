package com.armedia.atddaccelerator.template.monolith.service;

import com.armedia.atddaccelerator.template.monolith.service.factory.ImportDataAbstractFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class StartupDataImporter implements ApplicationRunner {
    private final ImportDataAbstractFactory importDataFactory;

    public StartupDataImporter(ImportDataAbstractFactory importDataFactory) {
        this.importDataFactory = importDataFactory;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        File file = new File("/app/data/airports.txt");
        MultipartFile multipartFile = new FileSystemMultipartFile(file);

        File fileRoutes = new File("/app/data/routes.txt");
        MultipartFile multipartFileRoute = new FileSystemMultipartFile(fileRoutes);

        CompletableFuture
                .supplyAsync(() -> importDataFactory
                        .getImportDataService("airports")
                        .importData(multipartFile).size())
                .thenApply(airportsCount -> {
                    log.info("Imported {} airports", airportsCount);
                    return importDataFactory
                            .getImportDataService("routes")
                            .importData(multipartFileRoute).size();
                })
                .thenAccept(routesCount -> log.info("Imported {} routes", routesCount))
                .exceptionally(ex -> {
                    log.error("Import failed at some step", ex);
                    return null;
                });
    }
}
