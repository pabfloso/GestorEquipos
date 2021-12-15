package com.pruebatecnica.GestorEquipos.controller;

import com.pruebatecnica.GestorEquipos.configuration.ConfigProperties;
import com.pruebatecnica.GestorEquipos.entity.Team;
import com.pruebatecnica.GestorEquipos.exceptions.CustomException;
import com.pruebatecnica.GestorEquipos.mappers.TeamMapper;
import com.pruebatecnica.GestorEquipos.service.impl.TeamServiceImpl;
import com.pruebatecnica.GestorEquipos.dto.TeamDto;
import com.pruebatecnica.GestorEquipos.exceptions.ResourceNotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamController {

    private final ConfigProperties configProperties;
    private final TeamServiceImpl teamService;
    private final TeamMapper teamMapper;

    @Autowired
    public TeamController(ConfigProperties configProperties, TeamServiceImpl teamService, TeamMapper teamMapper) {
        this.configProperties = configProperties;
        this.teamService = teamService;
        this.teamMapper = teamMapper;
    }

    @ApiOperation(value = "Crear un nuevo equipo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = TeamDto.class)
    })
    @PostMapping
    public ResponseEntity<TeamDto> createTeam(
            @ApiParam(value = "Datos del DTO del equipo a crear.", type = "TeamDto") @RequestBody TeamDto teamDto
    ) {
        Team team = teamService.createTeam(teamMapper.toEntity(teamDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(teamMapper.toDto(team));
    }

    @ApiOperation(value = "Obtener un equipo por su nombre")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = TeamDto.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ResourceNotFoundException.class)
    })
    @GetMapping("{nombre}")
    public ResponseEntity<TeamDto> getTeam(
            @ApiParam(value = "Nombre del equipo", type = "String") @PathVariable String nombre
    ) {
        Team team = teamService.getTeam(nombre).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el equipo"));
        return ResponseEntity.status(HttpStatus.OK).body(teamMapper.toDto(team));
    }

    @ApiOperation(value = "Obtener todos los equipos. Si no se envia ningun parametro no se ordenan. Si se indica el paramentro order=true los equipos se ordenan por capacidad de estadio.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = TeamDto.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ResourceNotFoundException.class)
    })
    @GetMapping()
    public ResponseEntity<List<TeamDto>> getAllTeams(
            @ApiParam(value = "Condicion ordenar la busqueda", type = "boolean") @RequestParam(required = false) boolean order
    ) {
        List<TeamDto> res = new ArrayList<>();
        if(order) {
            res = teamService.getOrderedTeams();
        } else {
            res = teamService.getAllTeams();
        }
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @ApiOperation(value = "Actualizar un equipo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = TeamDto.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ResourceNotFoundException.class)
    })
    @PutMapping()
    public ResponseEntity<TeamDto> updateTeam(
            @ApiParam(value = "Datos del DTO del equipo a actualizar.", type = "TeamDto") @RequestBody TeamDto teamDto
    ) {
        Team t = teamService.updateTeam(teamMapper.toEntity(teamDto)).orElseThrow(
                () -> new CustomException("No se ha podido actualizar el equipo"));

        return ResponseEntity.status(HttpStatus.OK).body(teamMapper.toDto(t));
    }

    @ApiOperation(value = "Eliminar un equipo.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = TeamDto.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ResourceNotFoundException.class)
    })
    @DeleteMapping()
    public ResponseEntity<TeamDto> deleteTeam(
            @ApiParam(value = "Nombre del equipo", type = "String") @RequestParam String name
    ) {
        Team t = teamService.deleteTeam(name).orElseThrow(
                () -> new CustomException("No se ha podido eliminar el equipo"));
        return ResponseEntity.status(HttpStatus.OK).body(teamMapper.toDto(t));
    }


}
