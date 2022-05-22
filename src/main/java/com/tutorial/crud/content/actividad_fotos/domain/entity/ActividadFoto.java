package com.tutorial.crud.content.actividad_fotos.domain.entity;
import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RELA_ACTIVIDAD_FOTO")
public class ActividadFoto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="actividad_id")
    private Actividad actividad;

    @Column(name = "foto",length = 1000)
    private String foto;


}
