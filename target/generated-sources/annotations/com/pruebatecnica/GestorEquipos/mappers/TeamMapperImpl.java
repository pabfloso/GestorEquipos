package com.pruebatecnica.GestorEquipos.mappers;

import com.pruebatecnica.GestorEquipos.dto.TeamDto;
import com.pruebatecnica.GestorEquipos.entity.Team;
import java.io.UnsupportedEncodingException;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-16T17:13:10+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_161 (Oracle Corporation)"
)
@Component
public class TeamMapperImpl implements TeamMapper {

    @Override
    public TeamDto toDto(Team team) {
        if ( team == null ) {
            return null;
        }

        TeamDto teamDto = new TeamDto();

        teamDto.setNombre( team.getNombre() );
        teamDto.setCiudad( team.getCiudad() );
        try {
            teamDto.setEscudo( map( team.getEscudo() ) );
        }
        catch ( UnsupportedEncodingException e ) {
            throw new RuntimeException( e );
        }
        teamDto.setCapacidadEstadio( team.getCapacidadEstadio() );
        teamDto.setNumeroJugadores( team.getNumeroJugadores() );
        teamDto.setFechaFundacion( team.getFechaFundacion() );

        return teamDto;
    }

    @Override
    public Team toEntity(TeamDto teamDto) {
        if ( teamDto == null ) {
            return null;
        }

        Team team = new Team();

        team.setNombre( teamDto.getNombre() );
        team.setCiudad( teamDto.getCiudad() );
        try {
            team.setEscudo( map( teamDto.getEscudo() ) );
        }
        catch ( UnsupportedEncodingException e ) {
            throw new RuntimeException( e );
        }
        team.setCapacidadEstadio( teamDto.getCapacidadEstadio() );
        team.setNumeroJugadores( teamDto.getNumeroJugadores() );
        team.setFechaFundacion( teamDto.getFechaFundacion() );

        return team;
    }

    @Override
    public void updateModel(TeamDto teamDto, Team team) {
        if ( teamDto == null ) {
            return;
        }

        team.setNombre( teamDto.getNombre() );
        team.setCiudad( teamDto.getCiudad() );
        try {
            team.setEscudo( map( teamDto.getEscudo() ) );
        }
        catch ( UnsupportedEncodingException e ) {
            throw new RuntimeException( e );
        }
        team.setCapacidadEstadio( teamDto.getCapacidadEstadio() );
        team.setNumeroJugadores( teamDto.getNumeroJugadores() );
        team.setFechaFundacion( teamDto.getFechaFundacion() );
    }
}
