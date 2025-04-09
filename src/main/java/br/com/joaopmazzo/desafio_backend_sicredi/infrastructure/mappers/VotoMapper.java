package br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.VotoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.VotoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VotoMapper {

    VotoMapper INSTANCE = Mappers.getMapper(VotoMapper.class);

    VotoResponseDTO toResponseDTO(VotoEntity entity);

}
