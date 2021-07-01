package algamoneyapi.resouce;

import algamoneyapi.model.Categoria;
import algamoneyapi.publisher.ResourceCreatedPublisher;
import algamoneyapi.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaResource {

    private final CategoriaRepository categoriaRepository;

    private final ResourceCreatedPublisher resourceCreatedPublisher;

    @GetMapping
    public List<Categoria> findAll() {
        return this.categoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@Valid @RequestBody final Categoria categoria, HttpServletResponse response ) {
        final var categoriaCreated = this.categoriaRepository.save(categoria);

        this.resourceCreatedPublisher.publish(response, categoriaCreated.getCodigo());

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreated);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> findByCodigo(@PathVariable final Long codigo) {
        return this.categoriaRepository.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
