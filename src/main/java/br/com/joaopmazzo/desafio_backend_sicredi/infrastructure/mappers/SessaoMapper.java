package br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.SessaoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SessaoMapper {

    SessaoMapper INSTANCE = Mappers.getMapper(SessaoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "inicio", ignore = true)
    @Mapping(target = "termino", ignore = true)
    SessaoEntity toEntity(SessaoRequestDTO dto);

    SessaoResponseDTO toResponseDTO(SessaoEntity entity);

}
