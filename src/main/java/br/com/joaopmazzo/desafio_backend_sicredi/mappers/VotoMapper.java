package br.com.joaopmazzo.desafio_backend_sicredi.mappers;

import br.com.joaopmazzo.desafio_backend_sicredi.dtos.request.VotoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.dtos.response.VotoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.entities.VotoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VotoMapper {

    VotoMapper INSTANCE = Mappers.getMapper(VotoMapper.class);

    VotoEntity toEntity(VotoRequestDTO dto);

    VotoResponseDTO toResponseDTO(VotoEntity entity);

}
