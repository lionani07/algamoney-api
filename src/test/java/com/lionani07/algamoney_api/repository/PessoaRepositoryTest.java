package com.lionani07.algamoney_api.repository;

import com.lionani07.algamoney_api.model.Endereco;
import com.lionani07.algamoney_api.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("PessoaRepository Unit Tests")
class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EntityManager entityManager;

    private Pessoa pessoaValida;
    private Endereco enderecoValido;

    @BeforeEach
    void setUp() {
        pessoaRepository.deleteAll();

        enderecoValido = new Endereco();
        enderecoValido.setLogradouro("Rua das Flores");
        enderecoValido.setNumero("123");
        enderecoValido.setComplemento("Apt 456");
        enderecoValido.setBairro("Centro");
        enderecoValido.setCep("01234-567");
        enderecoValido.setCidade("São Paulo");
        enderecoValido.setEstado("SP");

        pessoaValida = new Pessoa();
        pessoaValida.setNome("João Silva");
        pessoaValida.setAtivo(true);
        pessoaValida.setEndereco(enderecoValido);
    }

    @Test
    @DisplayName("Should save a pessoa successfully")
    void testSavePessoa() {
        // Act
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);

        // Assert
        assertThat(pessoaSalva).isNotNull();
        assertThat(pessoaSalva.getCodigo()).isNotNull();
        assertThat(pessoaSalva.getNome()).isEqualTo("João Silva");
        assertThat(pessoaSalva.getAtivo()).isTrue();
        assertThat(pessoaSalva.getEndereco().getLogradouro()).isEqualTo("Rua das Flores");
    }

    @Test
    @DisplayName("Should retrieve pessoa by id")
    void testFindById() {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);

        // Act
        var pessoaEncontrada = pessoaRepository.findById(pessoaSalva.getCodigo());

        // Assert
        assertThat(pessoaEncontrada).isPresent();
        assertThat(pessoaEncontrada.get().getNome()).isEqualTo("João Silva");
        assertThat(pessoaEncontrada.get().getAtivo()).isTrue();
    }

    @Test
    @DisplayName("Should return empty when pessoa not found by id")
    void testFindByIdNotFound() {
        // Act
        var pessoaEncontrada = pessoaRepository.findById(999L);

        // Assert
        assertThat(pessoaEncontrada).isEmpty();
    }

    @Test
    @DisplayName("Should retrieve all pessoas")
    void testFindAll() {
        // Arrange
        Pessoa pessoa1 = pessoaRepository.save(pessoaValida);

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Maria Santos");
        pessoa2.setAtivo(false);
        pessoa2.setEndereco(enderecoValido);
        pessoaRepository.save(pessoa2);

        // Act
        var pessoas = pessoaRepository.findAll();

        // Assert
        assertThat(pessoas).hasSize(2);
        assertThat(pessoas).contains(pessoa1, pessoa2);
    }

    @Test
    @DisplayName("Should return empty list when no pessoas exist")
    void testFindAllEmpty() {
        // Act
        var pessoas = pessoaRepository.findAll();

        // Assert
        assertThat(pessoas).isEmpty();
    }

    @Test
    @DisplayName("Should delete pessoa by id")
    void testDeleteById() {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);
        Long codigo = pessoaSalva.getCodigo();

        // Act
        pessoaRepository.deleteById(codigo);

        // Assert
        assertThat(pessoaRepository.findById(codigo)).isEmpty();
    }

    @Test
    @DisplayName("Should delete pessoa entity")
    void testDelete() {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);

        // Act
        pessoaRepository.delete(pessoaSalva);

        // Assert
        assertThat(pessoaRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Should return total count of pessoas")
    void testCount() {
        // Arrange
        pessoaRepository.save(pessoaValida);
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Maria Santos");
        pessoa2.setAtivo(false);
        pessoa2.setEndereco(enderecoValido);
        pessoaRepository.save(pessoa2);

        // Act
        long count = pessoaRepository.count();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Should return count as zero when no pessoas exist")
    void testCountEmpty() {
        // Act
        long count = pessoaRepository.count();

        // Assert
        assertThat(count).isZero();
    }

    @Test
    @DisplayName("Should check if pessoa exists by id")
    void testExistsById() {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);

        // Act & Assert
        assertThat(pessoaRepository.existsById(pessoaSalva.getCodigo())).isTrue();
        assertThat(pessoaRepository.existsById(999L)).isFalse();
    }

    @Test
    @DisplayName("Should update pessoa")
    void testUpdatePessoa() {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);
        pessoaSalva.setNome("João Silva Atualizado");
        pessoaSalva.setAtivo(false);

        // Act
        Pessoa pessoaAtualizada = pessoaRepository.save(pessoaSalva);

        // Assert
        assertThat(pessoaAtualizada.getNome()).isEqualTo("João Silva Atualizado");
        assertThat(pessoaAtualizada.getAtivo()).isFalse();
    }

    @Test
    @DisplayName("Should update ativo field by id")
    @Transactional
    void testUpdateAtivoById() {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);
        Long codigo = pessoaSalva.getCodigo();
        assertThat(pessoaSalva.getAtivo()).isTrue();

        // Act
        pessoaRepository.updateAtivoById(false, codigo);
        entityManager.flush();
        entityManager.clear();

        // Assert
        var pessoaAtualizada = pessoaRepository.findById(codigo).orElseThrow();
        assertThat(pessoaAtualizada.getAtivo()).isFalse();
    }

    @Test
    @DisplayName("Should update ativo from false to true")
    @Transactional
    void testUpdateAtivoFromFalseToTrue() {
        // Arrange
        pessoaValida.setAtivo(false);
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);
        Long codigo = pessoaSalva.getCodigo();
        assertThat(pessoaSalva.getAtivo()).isFalse();

        // Act
        pessoaRepository.updateAtivoById(true, codigo);
        entityManager.flush();
        entityManager.clear();

        // Assert
        var pessoaAtualizada = pessoaRepository.findById(codigo).orElseThrow();
        assertThat(pessoaAtualizada.getAtivo()).isTrue();
    }

    @Test
    @DisplayName("Should persist multiple pessoas")
    void testSaveMultiplePessoas() {
        // Arrange
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("João Silva");
        pessoa1.setAtivo(true);
        pessoa1.setEndereco(enderecoValido);

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Maria Santos");
        pessoa2.setAtivo(false);
        pessoa2.setEndereco(enderecoValido);

        Pessoa pessoa3 = new Pessoa();
        pessoa3.setNome("Pedro Oliveira");
        pessoa3.setAtivo(true);
        pessoa3.setEndereco(enderecoValido);

        // Act
        pessoaRepository.saveAll(java.util.List.of(pessoa1, pessoa2, pessoa3));

        // Assert
        assertThat(pessoaRepository.count()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should delete all pessoas")
    void testDeleteAll() {
        // Arrange
        pessoaRepository.save(pessoaValida);
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Maria Santos");
        pessoa2.setAtivo(false);
        pessoa2.setEndereco(enderecoValido);
        pessoaRepository.save(pessoa2);

        // Act
        pessoaRepository.deleteAll();

        // Assert
        assertThat(pessoaRepository.count()).isZero();
    }

    @Test
    @DisplayName("Should handle pessoa with minimal endereco data")
    void testSavePessoaWithMinimalEndereco() {
        // Arrange
        Endereco enderecoMinimal = new Endereco();
        enderecoMinimal.setLogradouro("Rua Principal");
        enderecoMinimal.setNumero("1");
        pessoaValida.setEndereco(enderecoMinimal);

        // Act
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);

        // Assert
        assertThat(pessoaSalva.getEndereco().getLogradouro()).isEqualTo("Rua Principal");
        assertThat(pessoaSalva.getEndereco().getNumero()).isEqualTo("1");
        assertThat(pessoaSalva.getEndereco().getComplemento()).isNull();
    }

    @Test
    @DisplayName("Should persist pessoa with all endereco fields")
    void testSavePessoaWithCompleteEndereco() {
        // Act
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);

        // Assert
        assertThat(pessoaSalva.getEndereco())
                .isNotNull()
                .satisfies(endereco -> {
                    assertThat(endereco.getLogradouro()).isEqualTo("Rua das Flores");
                    assertThat(endereco.getNumero()).isEqualTo("123");
                    assertThat(endereco.getComplemento()).isEqualTo("Apt 456");
                    assertThat(endereco.getBairro()).isEqualTo("Centro");
                    assertThat(endereco.getCep()).isEqualTo("01234-567");
                    assertThat(endereco.getCidade()).isEqualTo("São Paulo");
                    assertThat(endereco.getEstado()).isEqualTo("SP");
                });
    }

    @Test
    @DisplayName("Should maintain id after save")
    void testIdPersistence() {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);
        Long id = pessoaSalva.getCodigo();

        // Act
        var pessoaRecuperada = pessoaRepository.findById(id).orElseThrow();

        // Assert
        assertThat(pessoaRecuperada.getCodigo()).isEqualTo(id);
    }
}

