package com.tutorial.crud.security.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

import com.tutorial.crud.content.actividad_usuario.domain.entity.ActividadUsuario;
import com.tutorial.crud.security.domain.enums.RolNombre;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Data
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;
    @NotNull
    @Column(name = "nombre")
    private String nombre;
    @NotNull
    @Column(unique = true, name = "nombre_usuario")
    private String nombreUsuario;
    @NotNull
    @Column(name = "email")
    private String email;
    @NotNull
    @Column(name = "password")
    private String password;
    //Un rol puede pertenecer a varios usuario muchos a muchos tabla intermedia
    @ManyToMany(fetch = FetchType.EAGER)
    //Lazy obtención de los datos cuando sea necesario con eager es inmediato antes de que se soliciten
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    @JoinColumn(name = "usuario_id")
    @OneToMany
    private List<ActividadUsuario> actividadUsuarioList = new ArrayList<>();

    @Column(name = "activo")
    private Boolean activo;

   /* @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Actividad actividad;*/


    public Usuario() {
    }

    public Usuario(@NotNull String nombre, @NotNull String nombreUsuario, @NotNull String email, @NotNull String password) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    }





}
