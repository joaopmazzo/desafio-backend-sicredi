package br.com.joaopmazzo.desafio_backend_sicredi.mappers;

import br.com.joaopmazzo.desafio_backend_sicredi.dtos.response.AssociadoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.entities.AssociadoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {

    AssociadoMapper INSTANCE = Mappers.getMapper(AssociadoMapper.class);

    AssociadoResponseDTO toResponseDTO(AssociadoEntity entity);

}
