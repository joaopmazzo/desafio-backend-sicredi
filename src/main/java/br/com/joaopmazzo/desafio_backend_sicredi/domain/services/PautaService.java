package br.com.joaopmazzo.desafio_backend_sicredi.domain.services;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta.DuplicatePautaException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta.PautaNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Camada de serviço para pautas.
 */
@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;

    /**
     * Busca uma pauta pelo ID.
     *
     * @param id ID da pauta a ser buscada.
     * @return Entidade PautaEntity correspondente ao ID fornecido.
     * @throws PautaNotFoundException se nenhuma pauta for encontrada com o ID fornecido.
     */
    @Transactional(readOnly = true)
    public PautaEntity findPautaById(UUID id) {
        return pautaRepository
                .findById(id)
                .orElseThrow(PautaNotFoundException::new);
    }

    /**
     * Retorna uma lista paginada de todas as pautas.
     *
     * @param pageable Objeto de paginação.
     * @return Página contendo as entidades PautaEntity.
     */
    @Transactional(readOnly = true)
    public Page<PautaEntity> returnPautasPageable(Pageable pageable) {
        return pautaRepository.findAll(pageable);
    }

    /**
     * Salva uma nova pauta no banco de dados.
     *
     * @param pauta Entidade PautaEntity a ser salva.
     * @return A entidade PautaEntity salva.
     */
    @Transactional
    public PautaEntity savePauta(PautaEntity pauta) {
        return pautaRepository.save(pauta);
    }

    /**
     * Verifica se já existe uma pauta com o título fornecido.
     *
     * @param titulo Título da pauta a ser verificada.
     * @return false se não existir uma pauta com o título fornecido.
     * @throws DuplicatePautaException se já existir uma pauta com o mesmo título.
     */
    @Transactional(readOnly = true)
    public boolean existsByTitulo(String titulo) {
        if (pautaRepository.existsByTitulo(titulo)) throw new DuplicatePautaException();
        return false;
    }

}
