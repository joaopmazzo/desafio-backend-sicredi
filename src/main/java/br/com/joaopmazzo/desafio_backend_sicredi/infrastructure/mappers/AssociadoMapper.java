package br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.AssociadoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {

    AssociadoMapper INSTANCE = Mappers.getMapper(AssociadoMapper.class);

    AssociadoResponseDTO toResponseDTO(AssociadoEntity entity);

}
