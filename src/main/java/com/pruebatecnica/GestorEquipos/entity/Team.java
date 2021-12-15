package com.pruebatecnica.GestorEquipos.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
@Table(name="equipos")
public class Team {
    @Id
    @NotNull
    @Column(name = "nombre")
    @Size(max = 45)
    private String nombre;

    @Column(name = "ciudad")
    @Size(max = 45)
    private String ciudad;

    @Lob
    @Column(name = "escudo")
    @Size(max = 45)
    private byte[] escudo;

    @Column(name = "capacidad")
    private Integer capacidadEstadio;

    @Column(name = "numerojugadores")
    private Integer numeroJugadores;

    @Column(name = "fechafundacion")
    private LocalDate fechaFundacion;

}
