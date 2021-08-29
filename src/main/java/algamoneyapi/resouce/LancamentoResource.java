package algamoneyapi.resouce;

import algamoneyapi.model.Lancamento;
import algamoneyapi.publisher.ResourceCreatedPublisher;
import algamoneyapi.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("lancamentos")
public class LancamentoResource {

    @Autowired
    private ResourceCreatedPublisher resourceCreatedPublisher;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @PostMapping
    public ResponseEntity<Lancamento> save(final @RequestBody @Valid Lancamento lancamento, HttpServletResponse response) {
        final var lancamentoCreated = this.lancamentoRepository.save(lancamento);

        // Ussando isso mas melhor criar service para crear location. Logo troca.
        this.resourceCreatedPublisher.publish(response, lancamento.getCodigo());
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoCreated);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
         return ResponseEntity.ok(this.lancamentoRepository.findAll());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> findByCodigo(@PathVariable final Long codigo) {
        return this.lancamentoRepository.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
