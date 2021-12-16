package com.pruebatecnica.GestorEquipos.mappers;

import com.pruebatecnica.GestorEquipos.dto.TeamDto;
import com.pruebatecnica.GestorEquipos.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamDto toDto(Team team);

    Team toEntity(TeamDto teamDto);

    void updateModel(TeamDto teamDto, @MappingTarget Team team);

    default byte[] map(String string) throws UnsupportedEncodingException {
            return Base64.getDecoder().decode(string);
    }

    default String map(byte[] bytes) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
