package com.tutorial.crud.content.actividad.domain.entity;

import com.tutorial.crud.content.actividad_fotos.domain.entity.ActividadFoto;
import com.tutorial.crud.content.actividad_usuario.domain.entity.ActividadUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MSTR_ACTIVIDAD")
public class Actividad {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actividad_id")
    private Integer id;

    //Esto quizás realizar un enum
    @Column(name = "titulo")
    private String titulo;

    @Column(name = "fecha")
    private String fecha;

    //Imagenes guardar url
    @Column(name = "etapa_educativa")
    private String etapaEducativa;


    @Column(name="descripcion")
    private String descripcion;

    @JoinColumn(name="actividad_id")
    @OneToMany
    private List<ActividadUsuario> actividadUsuarioList=new ArrayList<>();


    @JoinColumn(name="actividad_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<ActividadFoto> actividadFotoList=new ArrayList<>();
            // puedes usar esto dentro del oneToMany mappedBy = "actividad")



}
