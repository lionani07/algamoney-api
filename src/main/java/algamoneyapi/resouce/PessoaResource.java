package algamoneyapi.resouce;

import algamoneyapi.model.Pessoa;
import algamoneyapi.publisher.ResourceCreatedPublisher;
import algamoneyapi.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaResource {

    private final PessoaRepository pessoaRepository;

    private final ResourceCreatedPublisher resourceCreatedPublisher;

    @PostMapping
    public ResponseEntity<Pessoa> save(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response) {
        final var pessoaCreated = this.pessoaRepository.save(pessoa);

        this.resourceCreatedPublisher.publish(response, pessoaCreated.getCodigo());

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCreated);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long codigo) {
        this.pessoaRepository.deleteById(codigo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Pessoa> update(@PathVariable final Long codigo, @RequestBody Pessoa pessoa) {
        final var pessoaFound = this.pessoaRepository
                .findById(codigo)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        pessoa.setCodigo(pessoaFound.getCodigo());
        final var pessoaUpdated = this.pessoaRepository.save(pessoa);
        return ResponseEntity.ok(pessoaUpdated);

        /*
            BeanUtils.copyProperties(pessoa, pessoaFound, "codigo");
            final var pessoaUpdated = this.pessoaRepository.save(pessoaFound);
            return ResponseEntity.ok(pessoaUpdated);
        */

    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> find(@PathVariable final Long codigo) {
        return this.pessoaRepository.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Pessoa> findAll() {
        return this.pessoaRepository.findAll();
    }
}
