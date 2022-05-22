package com.tutorial.crud.jwt.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
public class NuevoUsuario {
    @NotBlank //No puede ser nulo no puede ser una cadena vacía
    private String nombre;
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;
    private Set<String> roles=new HashSet<>();
}
