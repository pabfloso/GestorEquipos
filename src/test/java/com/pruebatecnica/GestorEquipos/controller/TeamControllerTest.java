package com.pruebatecnica.GestorEquipos.controller;

import com.pruebatecnica.GestorEquipos.dto.TeamDto;
import com.pruebatecnica.GestorEquipos.entity.Team;
import com.pruebatecnica.GestorEquipos.exceptions.CustomException;
import com.pruebatecnica.GestorEquipos.exceptions.ResourceNotFoundException;
import com.pruebatecnica.GestorEquipos.mappers.TeamMapper;
import com.pruebatecnica.GestorEquipos.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Test sobre el controlador de equipos")
class TeamControllerTest {

    private  final String NOMBRE = "Equipo prueba";
    private  final String CIUDAD = "Ciudad prueba";
    private  final Integer CAPACIDAD_ESTADIO = 200;
    private  final Integer NUMERO_JUGADORES = 12;
    private  final LocalDate FECHA_FUNDACION = LocalDate.of(1998,12,12);
    private String escudo;

    @Autowired
    private TeamMapper teamMapper;

    @Mock
    private TeamServiceImpl teamService;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        this.teamService = Mockito.mock(TeamServiceImpl.class);
        teamController = new TeamController(teamService, teamMapper);
        setEscudo();
    }

    @Test
    void createTeamTest() {
        Team t = getTeamEntity();
        Mockito.when(teamService.createTeam(t)).thenReturn(t);
        ResponseEntity<TeamDto> teamResponseEntity = teamController.createTeam(getTeamDto());

        Assertions.assertEquals(HttpStatus.CREATED, teamResponseEntity.getStatusCode());
        Assertions.assertEquals(teamMapper.toDto(t),teamResponseEntity.getBody());
    }

    @Test
    void getTeamTest() {
        Team t = getTeamEntity();
        Mockito.when(teamService.getTeam(NOMBRE)).thenReturn(Optional.of(t));

        ResponseEntity<TeamDto> teamDtoResponseEntity = teamController.getTeam(NOMBRE);
        Assertions.assertEquals(HttpStatus.OK, teamDtoResponseEntity.getStatusCode());
        Assertions.assertEquals(t,teamMapper.toEntity(teamDtoResponseEntity.getBody()));
    }

    @Test
    void getTeamNotFoundTest() {
        Mockito.when(teamService.getTeam(NOMBRE)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> teamController.getTeam(NOMBRE));
    }

    @Test
    void getAllTeamsOrderedTest() {
        List<TeamDto> teams = getTeamList();
        Mockito.when(teamService.getOrderedTeams()).thenReturn(teams);

        ResponseEntity<List<TeamDto>> listResponseEntity = teamController.getAllTeams(true);

        Assertions.assertEquals(teams, listResponseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
    }

    @Test
    void getAllTeamsTest() {
        List<TeamDto> teams = getTeamList();
        Mockito.when(teamService.getAllTeams() ).thenReturn(teams);

        ResponseEntity<List<TeamDto>> listResponseEntity = teamController.getAllTeams(false);

        Assertions.assertEquals(teams, listResponseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());

    }

    @Test
    void updateTeamTest() {
        Team t = getTeamEntity();
        Mockito.when(teamService.updateTeam(t)).thenReturn(Optional.of(t));

        ResponseEntity<TeamDto> teamDtoResponseEntity = teamController.updateTeam(getTeamDto());
        Assertions.assertEquals(HttpStatus.OK, teamDtoResponseEntity.getStatusCode());
        Assertions.assertNotNull(teamDtoResponseEntity.getBody());

    }

    @Test
    void updateTeamNotFoundTest() {
        Team t = getTeamEntity();
        Mockito.when(teamService.updateTeam(t)).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomException.class, () -> teamController.updateTeam(getTeamDto()));
    }

    @Test
    void deleteTeamTest() {
        Team t = getTeamEntity();
        Mockito.when(teamService.deleteTeam(NOMBRE)).thenReturn(Optional.of(t));

        ResponseEntity<TeamDto> teamDtoResponseEntity = teamController.deleteTeam(NOMBRE);
        Assertions.assertEquals(HttpStatus.OK, teamDtoResponseEntity.getStatusCode());
        Assertions.assertEquals(t,teamMapper.toEntity(teamDtoResponseEntity.getBody()));
    }

    @Test
    void deleteTeamNotFoundTest() {
        Team t = getTeamEntity();
        Mockito.when(teamService.deleteTeam(NOMBRE)).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomException.class, () -> teamController.deleteTeam(NOMBRE));
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

    void setEscudo() throws IOException {
        this.escudo = new String(Files.readAllBytes(Paths.get("escudoBase64.txt")));
    }

}
