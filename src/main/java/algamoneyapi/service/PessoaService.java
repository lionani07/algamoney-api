package algamoneyapi.service;

import algamoneyapi.model.Pessoa;
import algamoneyapi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository  pessoaRepository;

    public Pessoa update(final Long codigo, final Pessoa pessoa) {
        final var pessoaFound = foundByCodigo(codigo);
        BeanUtils.copyProperties(pessoa, pessoaFound, "codigo");
        return this.pessoaRepository.save(pessoaFound);
    }

    public void updateAtivo(final Long codigo, final Boolean ativo) {
        final var pessoaFound = foundByCodigo(codigo);
        pessoaFound.setAtivo(ativo);
        this.pessoaRepository.save(pessoaFound);
    }

    private Pessoa foundByCodigo(final Long codigo) {
        return this.pessoaRepository
                .findById(codigo)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

}
