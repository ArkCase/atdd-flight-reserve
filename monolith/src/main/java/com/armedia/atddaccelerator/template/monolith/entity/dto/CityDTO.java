package com.armedia.atddaccelerator.template.monolith.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityDTO {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private String countryName;
 //   private List<CommentDTO> comments;
}

