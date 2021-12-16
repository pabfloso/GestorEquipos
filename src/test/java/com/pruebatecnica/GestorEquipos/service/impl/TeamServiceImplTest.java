package com.pruebatecnica.GestorEquipos.service.impl;

import com.pruebatecnica.GestorEquipos.controller.TeamController;
import com.pruebatecnica.GestorEquipos.dto.TeamDto;
import com.pruebatecnica.GestorEquipos.entity.Team;
import com.pruebatecnica.GestorEquipos.exceptions.CustomException;
import com.pruebatecnica.GestorEquipos.exceptions.ResourceNotFoundException;
import com.pruebatecnica.GestorEquipos.mappers.TeamMapper;
import com.pruebatecnica.GestorEquipos.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Test sobre el servicio de la aplicacion")
public class TeamServiceImplTest {
    private  final String NOMBRE = "Equipo prueba";
    private  final String CIUDAD = "Ciudad prueba";
    private  final Integer CAPACIDAD_ESTADIO = 200;
    private  final Integer NUMERO_JUGADORES = 12;
    private  final LocalDate FECHA_FUNDACION = LocalDate.of(1998,12,12);
    private String escudo;

    @Autowired
    private TeamMapper teamMapper;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        this.teamRepository = Mockito.mock(TeamRepository.class);
        teamService = new TeamServiceImpl(teamRepository, teamMapper);
        setEscudo();
    }

    @Test
    void createTeamTest() {
        Team t = getTeamEntity();
        Mockito.when(teamRepository.save(t)).thenReturn(t);
        Team res = teamService.createTeam(t);

        Assertions.assertEquals(t,res);
    }

    @Test
    void getTeamTest() {
        Team t = getTeamEntity();
        Mockito.when(teamRepository.findOne(Mockito.any())).thenReturn(Optional.of(t));

        Optional<Team> teamOptional = teamService.getTeam(NOMBRE);
        Assertions.assertEquals(t, teamOptional.get());
    }

    @Test
    void getTeamNotFoundTest() {
        Mockito.when(teamRepository.findOne(Mockito.any())).thenReturn(Optional.empty());
        Optional<Team> teamOptional = teamService.getTeam(NOMBRE);
        Assertions.assertTrue(!teamOptional.isPresent());
    }

    @Test
    void getAllTeamsOrderedTest() {
        Mockito.when(teamRepository.findByOrderByCapacidadEstadioAsc()).thenReturn(getTeamEntityList());

        List<TeamDto> listResponse = teamService.getOrderedTeams();
        Assertions.assertTrue(!listResponse.isEmpty());
    }

    @Test
    void getAllTeamsTest() {
        Mockito.when(teamRepository.findAll()).thenReturn(getTeamEntityList());


        List<TeamDto> listResponse = teamService.getAllTeams();
        Assertions.assertTrue(!listResponse.isEmpty());

    }

    @Test
    void updateTeamTest() {
        Team t = getTeamEntity();
        Mockito.when(teamRepository.findOne(Mockito.any())).thenReturn(Optional.of(t));
        Mockito.when(teamRepository.save(t)).thenReturn(t);

        Optional<Team> teamOptional = teamService.updateTeam(t);
        Assertions.assertTrue(teamOptional.isPresent());
    }

    @Test
    void updateTeamNotFoundTest() {
        Team t = getTeamEntity();
        Mockito.when(teamService.getTeam(NOMBRE)).thenThrow(ResourceNotFoundException.class);
        Mockito.when(teamRepository.findOne(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(teamRepository.save(t)).thenReturn(t);

        Assertions.assertThrows(ResourceNotFoundException.class, () ->teamService.updateTeam(t));
    }

    @Test
    void deleteTeamTest() {
        Team t = getTeamEntity();
        Mockito.when(teamRepository.findOne(Mockito.any())).thenReturn(Optional.of(t));
        doNothing().when(teamRepository).delete(t);

        Optional<Team> teamOptional = teamService.deleteTeam(NOMBRE);
        Assertions.assertTrue(teamOptional.isPresent());
    }

    @Test
    void deleteTeamNotFoundTest() {
        Team t = getTeamEntity();
        Mockito.when(teamService.getTeam(NOMBRE)).thenThrow(ResourceNotFoundException.class);
        Mockito.when(teamRepository.findOne(Mockito.any())).thenReturn(Optional.empty());
        doNothing().when(teamRepository).delete(t);

        Assertions.assertThrows(ResourceNotFoundException.class, () ->teamService.deleteTeam(NOMBRE));
    }

    Team getTeamEntity() {
        return teamMapper.toEntity(getTeamDto());
    }

    TeamDto getTeamDto() {
        TeamDto teamDto = new TeamDto();
        teamDto.setNombre(NOMBRE);
        teamDto.setCiudad(CIUDAD);
        teamDto.setCapacidadEstadio(CAPACIDAD_ESTADIO);
        teamDto.setNumeroJugadores(NUMERO_JUGADORES);
        teamDto.setFechaFundacion(FECHA_FUNDACION);
        teamDto.setEscudo(escudo);
        return teamDto;
    }

    List<TeamDto> getTeamList() {
        List<TeamDto> teams = new ArrayList<>();
        teams.add(getTeamDto());
        teams.add(getTeamDto());
        teams.add(getTeamDto());
        return teams;
    }

    List<Team> getTeamEntityList() {
        List<Team> teams = new ArrayList<>();
        teams.add(getTeamEntity());
        teams.add(getTeamEntity());
        teams.add(getTeamEntity());
        return teams;
    }

    void setEscudo() throws IOException {
        this.escudo = new String(Files.readAllBytes(Paths.get("escudoBase64.txt")));
    }
}
