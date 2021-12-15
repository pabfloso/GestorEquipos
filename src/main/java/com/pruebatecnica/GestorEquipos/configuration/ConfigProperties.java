package com.pruebatecnica.GestorEquipos.configuration;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class ConfigProperties {

}
