package com.pruebatecnica.GestorEquipos.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TeamDto {
    private String nombre;
    private String ciudad;
    private String escudo;
    private Integer capacidadEstadio;
    private Integer numeroJugadores;
    private LocalDate fechaFundacion;

}
