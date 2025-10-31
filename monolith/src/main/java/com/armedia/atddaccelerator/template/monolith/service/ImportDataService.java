package com.armedia.atddaccelerator.template.monolith.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface ImportDataService {
    default List<List<String>> parse(MultipartFile file) {
        List<List<String>> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));) {
            String line = bufferedReader.readLine();

            while (line != null) {

                List<String> lineData = Arrays.asList(line.split(",")).stream()
                        .map(x -> x.replace("\"", ""))
                        .collect(Collectors.toList());

                lines.add(lineData);

                line = bufferedReader.readLine();
            }
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return lines;
    }

    String getType();

    List<String> importData(MultipartFile file);
}
