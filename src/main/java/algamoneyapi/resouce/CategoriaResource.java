package algamoneyapi.resouce;

import algamoneyapi.model.Categoria;
import algamoneyapi.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaResource {

    private final CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> findAll() {
        return this.categoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@Valid @RequestBody final Categoria categoria) {
        final var categoriaCreated = this.categoriaRepository.save(categoria);
        final var location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(categoriaCreated.getCodigo())
                .toUri();

        return ResponseEntity.created(location).body(categoriaCreated);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> findByCodigo(@PathVariable final Long codigo) {
        return this.categoriaRepository.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
