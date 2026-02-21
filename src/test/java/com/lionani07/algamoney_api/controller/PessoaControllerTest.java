package com.lionani07.algamoney_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lionani07.algamoney_api.model.Endereco;
import com.lionani07.algamoney_api.model.Pessoa;
import com.lionani07.algamoney_api.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("PessoaController Integration Tests")
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PessoaRepository pessoaRepository;

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
    @DisplayName("Should create a pessoa successfully")
    void testCreatePessoa() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)));

        // Assert
        result.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigo").isNumber())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.ativo").value(true))
                .andExpect(jsonPath("$.endereco.logradouro").value("Rua das Flores"))
                .andExpect(jsonPath("$.endereco.numero").value("123"));

        // Verify data in database
        assertThat(pessoaRepository.findAll()).hasSize(1);
        Pessoa pessoaCriada = pessoaRepository.findAll().get(0);
        assertThat(pessoaCriada.getNome()).isEqualTo("João Silva");
        assertThat(pessoaCriada.getAtivo()).isTrue();
    }

    @Test
    @DisplayName("Should return 201 with Location header when person created")
    void testCreatePessoaReturnsLocationHeader() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)));

        // Assert
        result.andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @DisplayName("Should fail to create pessoa with invalid data (missing nome)")
    void testCreatePessoaWithInvalidData() throws Exception {
        // Arrange
        pessoaValida.setNome(null);

        // Act
        ResultActions result = mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)));

        // Assert
        result.andDo(print())
                .andExpect(status().isBadRequest());

        // Verify no person was saved
        assertThat(pessoaRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Should fail to create pessoa with blank nome")
    void testCreatePessoaWithBlankNome() throws Exception {
        // Arrange
        pessoaValida.setNome("   ");

        // Act
        ResultActions result = mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)));

        // Assert
        result.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail to create pessoa with null ativo")
    void testCreatePessoaWithNullAtivo() throws Exception {
        // Arrange
        pessoaValida.setAtivo(null);

        // Act
        ResultActions result = mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)));

        // Assert
        result.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail to create pessoa with null endereco")
    void testCreatePessoaWithNullEndereco() throws Exception {
        // Arrange
        pessoaValida.setEndereco(null);

        // Act
        ResultActions result = mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)));

        // Assert
        result.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail to create pessoa with invalid endereco (missing logradouro)")
    void testCreatePessoaWithInvalidEndereco() throws Exception {
        // Arrange
        pessoaValida.getEndereco().setLogradouro(null);

        // Act
        ResultActions result = mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)));

        // Assert
        result.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should retrieve all pessoas")
    void testFindAll() throws Exception {
        // Arrange
        Pessoa pessoa1 = pessoaRepository.save(pessoaValida);

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Maria Santos");
        pessoa2.setAtivo(false);
        pessoa2.setEndereco(enderecoValido);
        pessoaRepository.save(pessoa2);

        // Act
        ResultActions result = mockMvc.perform(get("/pessoas")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome").value("João Silva"))
                .andExpect(jsonPath("$[1].nome").value("Maria Santos"));
    }

    @Test
    @DisplayName("Should retrieve all pessoas when list is empty")
    void testFindAllEmpty() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/pessoas")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Should retrieve pessoa by codigo")
    void testFindByCodigo() throws Exception {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);
        Long codigo = pessoaSalva.getCodigo();

        // Act
        ResultActions result = mockMvc.perform(get("/pessoas/{codigo}", codigo)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(codigo))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.ativo").value(true))
                .andExpect(jsonPath("$.endereco.logradouro").value("Rua das Flores"));
    }

    @Test
    @DisplayName("Should return 404 when pessoa not found by codigo")
    void testFindByCodigoNotFound() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/pessoas/{codigo}", 999L)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should delete pessoa by codigo")
    void testDeletePessoa() throws Exception {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);
        Long codigo = pessoaSalva.getCodigo();

        // Act
        ResultActions result = mockMvc.perform(delete("/pessoas/{codigo}", codigo));

        // Assert
        result.andExpect(status().isNoContent());

        // Verify person was deleted
        assertThat(pessoaRepository.findById(codigo)).isEmpty();
    }

    @Test
    @DisplayName("Should return 404 when deleting non-existent pessoa")
    void testDeleteNonExistentPessoa() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(delete("/pessoas/{codigo}", 999L));

        // Assert
        result.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update pessoa completely")
    void testUpdatePessoa() throws Exception {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);
        Long codigo = pessoaSalva.getCodigo();

        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setNome("João Silva Atualizado");
        pessoaAtualizada.setAtivo(false);

        Endereco enderecoAtualizado = new Endereco();
        enderecoAtualizado.setLogradouro("Rua Nova");
        enderecoAtualizado.setNumero("999");
        enderecoAtualizado.setComplemento("Apt 999");
        enderecoAtualizado.setBairro("Bairro Novo");
        enderecoAtualizado.setCep("99999-999");
        enderecoAtualizado.setCidade("Rio de Janeiro");
        enderecoAtualizado.setEstado("RJ");
        pessoaAtualizada.setEndereco(enderecoAtualizado);

        // Act
        ResultActions result = mockMvc.perform(put("/pessoas/{codigo}", codigo)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaAtualizada)));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(codigo))
                .andExpect(jsonPath("$.nome").value("João Silva Atualizado"))
                .andExpect(jsonPath("$.ativo").value(false))
                .andExpect(jsonPath("$.endereco.logradouro").value("Rua Nova"))
                .andExpect(jsonPath("$.endereco.cidade").value("Rio de Janeiro"));

        // Verify in database
        Pessoa pessoaBd = pessoaRepository.findById(codigo).orElseThrow();
        assertThat(pessoaBd.getNome()).isEqualTo("João Silva Atualizado");
        assertThat(pessoaBd.getAtivo()).isFalse();
    }

    @Test
    @DisplayName("Should fail to update pessoa with invalid data")
    void testUpdatePessoaWithInvalidData() throws Exception {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);
        Long codigo = pessoaSalva.getCodigo();

        pessoaValida.setNome(null);

        // Act
        ResultActions result = mockMvc.perform(put("/pessoas/{codigo}", codigo)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)));

        // Assert
        result.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 404 when updating non-existent pessoa")
    void testUpdateNonExistentPessoa() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(put("/pessoas/{codigo}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)));

        // Assert
        result.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update ativo field of pessoa")
    void testUpdateFieldAtivo() throws Exception {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);
        Long codigo = pessoaSalva.getCodigo();

        // Act
        ResultActions result = mockMvc.perform(put("/pessoas/{codigo}/ativo", codigo)
                .contentType(MediaType.APPLICATION_JSON)
                .content("false"));

        // Assert
        result.andExpect(status().isNoContent());

        // Verify in database
        Pessoa pessoaBd = pessoaRepository.findById(codigo).orElseThrow();
        assertThat(pessoaBd.getAtivo()).isFalse();
    }

    @Test
    @DisplayName("Should update ativo field to true")
    void testUpdateFieldAtivoToTrue() throws Exception {
        // Arrange
        pessoaValida.setAtivo(false);
        Pessoa pessoaSalva = pessoaRepository.save(pessoaValida);
        Long codigo = pessoaSalva.getCodigo();

        // Act
        ResultActions result = mockMvc.perform(put("/pessoas/{codigo}/ativo", codigo)
                .contentType(MediaType.APPLICATION_JSON)
                .content("true"));

        // Assert
        result.andExpect(status().isNoContent());

        // Verify in database
        Pessoa pessoaBd = pessoaRepository.findById(codigo).orElseThrow();
        assertThat(pessoaBd.getAtivo()).isTrue();
    }

    @Test
    @DisplayName("Should return 404 when updating ativo of non-existent pessoa")
    void testUpdateFieldAtivoNonExistent() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(put("/pessoas/{codigo}/ativo", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("true"));

        // Assert
        result.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should handle pessoa with all endereco fields")
    void testCreatePessoaWithCompleteEndereco() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)));

        // Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.endereco.logradouro").value("Rua das Flores"))
                .andExpect(jsonPath("$.endereco.numero").value("123"))
                .andExpect(jsonPath("$.endereco.complemento").value("Apt 456"))
                .andExpect(jsonPath("$.endereco.bairro").value("Centro"))
                .andExpect(jsonPath("$.endereco.cep").value("01234-567"))
                .andExpect(jsonPath("$.endereco.cidade").value("São Paulo"))
                .andExpect(jsonPath("$.endereco.estado").value("SP"));
    }

    @Test
    @DisplayName("Should create pessoa with minimal endereco fields")
    void testCreatePessoaWithMinimalEndereco() throws Exception {
        // Arrange
        Endereco enderecoMinimal = new Endereco();
        enderecoMinimal.setLogradouro("Rua Principal");
        enderecoMinimal.setNumero("1");
        pessoaValida.setEndereco(enderecoMinimal);

        // Act
        ResultActions result = mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaValida)));

        // Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.endereco.logradouro").value("Rua Principal"))
                .andExpect(jsonPath("$.endereco.numero").value("1"));
    }
}

