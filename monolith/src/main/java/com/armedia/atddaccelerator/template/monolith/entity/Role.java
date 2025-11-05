package com.armedia.atddaccelerator.template.monolith.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role //implements GrantedAuthority {
{

    @Id
    private String name;

   /* @Override
    public String getAuthority() {
        return name;
    }*/
}
