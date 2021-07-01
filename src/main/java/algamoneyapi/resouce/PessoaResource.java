package algamoneyapi.resouce;

import algamoneyapi.model.Pessoa;
import algamoneyapi.publisher.ResourceCreatedPublisher;
import algamoneyapi.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public List<Pessoa> findAll() {
        return this.pessoaRepository.findAll();
    }
}
