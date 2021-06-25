package algamoneyapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Table(name = "pessoa")
@Entity
@Getter
@Setter
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotBlank
    private String nome;

    @NotNull
    private Boolean ativo;

    @Embedded
    private Endereco endereco;
}
