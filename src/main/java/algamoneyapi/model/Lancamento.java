package algamoneyapi.model;

import algamoneyapi.model.enums.TipoLancamento;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento")
@Getter
@Setter
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "codigo_pessoa")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "codigo_categoria")
    private Categoria categoria;

    private String descricao;

    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    private BigDecimal valor;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private TipoLancamento tipo;

//    @DataVencimentoConstraint
//    private String dataVencimentoTestContraint;

}
