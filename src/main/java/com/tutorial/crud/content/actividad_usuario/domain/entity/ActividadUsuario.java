package com.tutorial.crud.content.actividad_usuario.domain.entity;

import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.security.domain.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RELA_ACTIVIDAD_USUARIO")
public class ActividadUsuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="actividad_id")
    private Actividad actividad;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @Column(name = "favorito")
    private Boolean favorito;

    @Column(name="inscrito")
    private Boolean inscrito;


}
