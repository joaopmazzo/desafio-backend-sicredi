package br.com.joaopmazzo.desafio_backend_sicredi.mappers;

import br.com.joaopmazzo.desafio_backend_sicredi.dtos.request.SessaoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.entities.SessaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessaoMapper {

    SessaoMapper INSTANCE = Mappers.getMapper(SessaoMapper.class);

    SessaoEntity toEntity(SessaoRequestDTO dto);

    SessaoResponseDTO toResponseDTO(SessaoEntity entity);

}
