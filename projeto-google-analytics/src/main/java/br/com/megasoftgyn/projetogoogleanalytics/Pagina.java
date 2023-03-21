package br.com.megasoftgyn.projetogoogleanalytics;

import java.util.List;

public class Pagina<D> {
    
    private Long total;
    
    private Integer paginaAtual;
    
    private Integer totalDePaginas;
    
    private Integer registrosPorPagina;
    
    private List<D> registros;
    
    public Pagina() {
        
    }
    
    public Pagina(final Long total, final Integer registrosPorPagina, final List<D> registros, final Integer paginaAtual) {
        this.total = total;
        this.registros = registros;
        this.registrosPorPagina = registrosPorPagina;
        this.paginaAtual = paginaAtual;
        this.totalDePaginas = (int) Math.ceil((double) this.total / registrosPorPagina);
    }
    
    public Long getTotal() {
        return this.total;
    }
    
    public void setTotal(final Long total) {
        this.total = total;
    }
    
    public Integer getRegistrosPorPagina() {
        return this.registrosPorPagina;
    }
    
    public void setRegistrosPorPagina(final Integer registrosPorPagina) {
        this.registrosPorPagina = registrosPorPagina;
    }
    
    public List<D> getRegistros() {
        return this.registros;
    }
    
    public void setRegistros(final List<D> registros) {
        this.registros = registros;
    }
    
    public Integer getPaginaAtual() {
        return this.paginaAtual;
    }
    
    public void setPaginaAtual(final Integer paginaAtual) {
        this.paginaAtual = paginaAtual;
    }
    
    public Integer getTotalDePaginas() {
        return this.totalDePaginas;
    }
    
    public void setTotalDePaginas(final Integer totalDePaginas) {
        this.totalDePaginas = totalDePaginas;
    }
}
