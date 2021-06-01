package algamoneyapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Table(name = "pessoa")
@Entity
@Getter
@Setter
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;

    @NotEmpty
    @Column(name = "nome")
    private String nome;

    @NotNull
    @Column(name = "ativo")
    private Boolean ativo;

    @Embedded
    private Endereco endereco;
}
