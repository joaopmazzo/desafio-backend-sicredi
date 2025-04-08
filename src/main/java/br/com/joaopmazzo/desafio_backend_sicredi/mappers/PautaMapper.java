package br.com.joaopmazzo.desafio_backend_sicredi.mappers;

import br.com.joaopmazzo.desafio_backend_sicredi.dtos.request.PautaRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.entities.PautaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PautaMapper {

    PautaMapper INSTANCE = Mappers.getMapper(PautaMapper.class);

    PautaEntity toEntity(PautaRequestDTO dto);

    PautaResponseDTO toResponseDTO(PautaEntity entity);

}
