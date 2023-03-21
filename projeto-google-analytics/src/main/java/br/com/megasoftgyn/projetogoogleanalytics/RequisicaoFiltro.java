package br.com.megasoftgyn.projetogoogleanalytics;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.megasoftgyn.projetogoogleanalytics.configuracoes.Configuracao;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequisicaoFiltro {
    
    private LocalDate dataInicial;
    
    private LocalDate dataFinal;
    
    private final Integer numeroDaPagina;
    
    private final Integer tamanhoDaPagina;
    
    public RequisicaoFiltro(
            final LocalDate dataInicial,
            final LocalDate dataFinal,
            final Integer numeroDaPagina,
            final Integer tamanhoDaPagina) {
        
        this.dataInicial = (Objects.nonNull(dataInicial) ? dataInicial : LocalDate.now().minusMonths(3));
        this.dataFinal = (Objects.nonNull(dataFinal) ? dataFinal : LocalDate.now());
        this.numeroDaPagina = (Objects.nonNull(numeroDaPagina) && numeroDaPagina >= Configuracao.VALOR_ZERO_COMPARADOR) ? numeroDaPagina : Configuracao.NUMERO_DA_PAGINA_INICIAL;
        this.tamanhoDaPagina = (Objects.nonNull(tamanhoDaPagina) && tamanhoDaPagina > Configuracao.VALOR_ZERO_COMPARADOR) ? tamanhoDaPagina : Configuracao.TAMANHO_MAXIMO_DE_RESULTADOS_POR_PAGINAS;
    }
    
    public LocalDate getDataInicial() {
        return this.dataInicial;
    }
    
    public LocalDate getDataFinal() {
        return this.dataFinal;
    }
    
    public void setDataInicial(final LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }
    
    public void setDataFinal(final LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }
    
    public int getTamanhoDaPagina() {
        return this.tamanhoDaPagina;
    }
    
    public int getNumeroDaPagina() {
        return this.numeroDaPagina;
    }
    
    public int getOffset() {
        return this.numeroDaPagina * this.tamanhoDaPagina;
    }
    
}
