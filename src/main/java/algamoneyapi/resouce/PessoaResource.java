package algamoneyapi.resouce;

import algamoneyapi.model.Pessoa;
import algamoneyapi.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaResource {

    private final PessoaRepository pessoaRepository;

    @PostMapping
    public ResponseEntity<Pessoa> save(@RequestBody @Valid Pessoa pessoa) {
        final var pessoaCreated = this.pessoaRepository.save(pessoa);

        final var location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(pessoa.getCodigo())
                .toUri();

        return ResponseEntity.created(location).body(pessoaCreated);
    }

    @GetMapping
    public List<Pessoa> findAll() {
        return this.pessoaRepository.findAll();
    }
}
