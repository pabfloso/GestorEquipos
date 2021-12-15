package com.pruebatecnica.GestorEquipos.service.impl;

import com.google.common.collect.Lists;
import com.pruebatecnica.GestorEquipos.dto.TeamDto;
import com.pruebatecnica.GestorEquipos.entity.Team;
import com.pruebatecnica.GestorEquipos.exceptions.ResourceNotFoundException;
import com.pruebatecnica.GestorEquipos.mappers.TeamMapper;
import com.pruebatecnica.GestorEquipos.repository.TeamRepository;
import com.pruebatecnica.GestorEquipos.service.ITeamService;
import com.pruebatecnica.GestorEquipos.specs.GenericSpec;
import com.pruebatecnica.GestorEquipos.specs.SearchCriteria;
import com.pruebatecnica.GestorEquipos.specs.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements ITeamService {
    private final String NOMBRE = "nombre";
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }


    public Team createTeam(Team team) {
        teamRepository.save(team);
        return team;
    }

    public Optional<Team> getTeam(String name) {
        GenericSpec<Team> specificationTeam = new GenericSpec<>(
                Arrays.asList(
                        new SearchCriteria(NOMBRE, name, SearchOperation.EQUAL)
                )
        );
        return teamRepository.findOne(specificationTeam);
    }

    public List<TeamDto> getAllTeams() {
        List<TeamDto> res = new ArrayList<>();
        teamRepository.findAll().forEach(entry -> {
                res.add(teamMapper.toDto(entry));
        });
        return res;
    }
    public List<TeamDto> getOrderedTeams() {
        List<TeamDto> res = new ArrayList<>();
        teamRepository.findByOrderByCapacidadEstadioAsc().forEach(entry -> {
            res.add(teamMapper.toDto(entry));
        });
        return res;
    }

    public Optional<Team> updateTeam(Team team) {
        Team t = getTeam(team.getNombre()).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el equipo"));
        return Optional.of(teamRepository.save(team));
    }

    public Optional<Team> deleteTeam(String name) {
        Team t = getTeam(name).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el equipo"));
        teamRepository.delete(t);
        return Optional.of(t);
    }

}
