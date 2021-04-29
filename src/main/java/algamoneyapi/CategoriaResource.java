package algamoneyapi;

import algamoneyapi.model.Categoria;
import algamoneyapi.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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

    public ResponseEntity<Categoria> create(@RequestBody Categoria categoria, UriComponentsBuilder uriComponentsBuilder) {
        final var categoriaCreated = this.categoriaRepository.save(categoria);
        final var location = uriComponentsBuilder
                .path("/codigo")
                .buildAndExpand(categoriaCreated.getCodigo())
                .toUri();

        return ResponseEntity.created(location).body(categoriaCreated);
    }

}
