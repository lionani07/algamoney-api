package algamoneyapi;

import algamoneyapi.model.Categoria;
import algamoneyapi.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
