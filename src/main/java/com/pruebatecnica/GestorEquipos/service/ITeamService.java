package com.pruebatecnica.GestorEquipos.service;

import com.pruebatecnica.GestorEquipos.entity.Team;
import com.pruebatecnica.GestorEquipos.exceptions.ResourceNotFoundException;
import com.pruebatecnica.GestorEquipos.specs.GenericSpec;
import com.pruebatecnica.GestorEquipos.specs.SearchCriteria;
import com.pruebatecnica.GestorEquipos.specs.SearchOperation;

import java.util.Arrays;
import java.util.Optional;

public interface ITeamService {

    public Team createTeam(Team team);

    public Optional<Team> getTeam(String name);

    public Optional<Team> updateTeam(Team team);

    public Optional<Team> deleteTeam(String name);
}
