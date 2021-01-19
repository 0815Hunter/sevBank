package de.sevdesk.config.mapstruct;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface QuarkusMapperConfig {
}
