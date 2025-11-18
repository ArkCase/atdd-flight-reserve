package com.armedia.atddaccelerator.template.monolith.service;

import com.armedia.atddaccelerator.template.monolith.service.factory.ImportDataAbstractFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Component
public class StartupDataImporter  implements ApplicationListener<ApplicationReadyEvent>
{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());
    private final ImportDataAbstractFactory importDataFactory;

    public StartupDataImporter(ImportDataAbstractFactory importDataFactory)
    {
        this.importDataFactory = importDataFactory;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event)
    {
        new Thread(() -> {
            try
            {
                File file = new File("/app/data/airports.txt");
                MultipartFile multipartFile = new FileSystemMultipartFile(file);

                List<String> result = importDataFactory
                        .getImportDataService("airports")
                        .importData(multipartFile);

                if (result != null)
                {
                    LOGGER.info("Import completed for airports with total records: " + result.size());
                }

                File fileRoutes = new File("/app/data/routes.txt");
                MultipartFile multipartFileRoute = new FileSystemMultipartFile(fileRoutes);

                List<String> resultRoutes = importDataFactory
                        .getImportDataService("routes")
                        .importData(multipartFileRoute);

                if (resultRoutes != null)
                {
                    LOGGER.info("Import completed for routes with total records: " + resultRoutes.size());
                }
            }
            catch (Exception e)
            {
                LOGGER.error("Error during background import", e);
            }
        }).start();

    }

}

