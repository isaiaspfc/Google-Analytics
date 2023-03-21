package br.com.megasoftgyn.projetogoogleanalytics.pesquisas;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "pesquisa", uniqueConstraints = {@UniqueConstraint(name = "pesquisa_unique", columnNames = {"url", "data_pesquisa"})})
public class Pesquisa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codigo_seq")
    @SequenceGenerator(name = "codigo_seq", sequenceName = "codigo_seq", allocationSize = 1)
    
    @Column(name = "codigo", nullable = true)
    private Long codigo;
    
    @Column(name = "modulo", nullable = true, length = 255)
    private String modulo;
    
    @Column(name = "funcionalidade", nullable = true, length = 255)
    private String funcionalidade;
    
    @Column(name = "complemento", nullable = true, length = 255)
    private String complemento;
    
    @Column(name = "numero_vizualizacoes", nullable = true)
    private Long numeroDeVizualizacoes;
    
    @Column(name = "tempo_pagina", nullable = true, precision = 8, scale = 2)
    private BigDecimal tempoNaPagina;
    
    @Column(name = "tempo_medio_pagina", nullable = true, precision = 8, scale = 2)
    private BigDecimal tempoMedioNaPagina;
    
    @Column(name = "sessoes", nullable = true)
    private Long sessoes;
    
    @Column(name = "duracao_sessoes", nullable = true, precision = 8, scale = 2)
    private BigDecimal duracaoDasSessoes;
    
    @Column(name = "media_sessoes", nullable = true, precision = 8, scale = 2)
    private BigDecimal mediaDeSessoes;
    
    @Column(name = "usuarios", nullable = true)
    private Long usuarios;
    
    @Column(name = "data_pesquisa", nullable = true)
    private LocalDate dataDaPesquisa;
    
    @Column(name = "url", nullable = true, length = 255)
    private String url;
    
    public Pesquisa(
            final String url,
            final String modulo,
            final String funcionalidade,
            final String complemento,
            final Long numeroDeVizualizacoes,
            final BigDecimal tempoNaPagina,
            final BigDecimal tempoMedioNaPagina,
            final Long sessoes,
            final BigDecimal duracaoDasSessoes,
            final BigDecimal mediaDeSessoes,
            final Long usuarios,
            final LocalDate dataDaPesquisa) {
        this.url = url;
        this.modulo = modulo;
        this.funcionalidade = funcionalidade;
        this.complemento = complemento;
        this.numeroDeVizualizacoes = numeroDeVizualizacoes;
        this.tempoNaPagina = tempoNaPagina;
        this.tempoMedioNaPagina = tempoMedioNaPagina;
        this.sessoes = sessoes;
        this.duracaoDasSessoes = duracaoDasSessoes;
        this.mediaDeSessoes = mediaDeSessoes;
        this.usuarios = usuarios;
        this.dataDaPesquisa = dataDaPesquisa;
    }
    
    public Pesquisa() {}
    
    public Long getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }
    
    public String getModulo() {
        return this.modulo;
    }
    
    public void setModulo(final String modulo) {
        this.modulo = modulo;
    }
    
    public String getFuncionalidade() {
        return this.funcionalidade;
    }
    
    public void setFuncionalidade(final String funcionalidade) {
        this.funcionalidade = funcionalidade;
    }
    
    public String getComplemento() {
        return this.complemento;
    }
    
    public void setComplemento(final String complemento) {
        this.complemento = complemento;
    }
    
    public Long getNumeroDeVizualizacoes() {
        return this.numeroDeVizualizacoes;
    }
    
    public void setNumeroDeVizualizacoes(final Long numeroDeVizualizacoes) {
        this.numeroDeVizualizacoes = numeroDeVizualizacoes;
    }
    
    public BigDecimal getTempoNaPagina() {
        return this.tempoNaPagina;
    }
    
    public void setTempoNaPagina(final BigDecimal tempoNaPagina) {
        this.tempoNaPagina = tempoNaPagina;
    }
    
    public BigDecimal getTempoMedioNaPagina() {
        return this.tempoMedioNaPagina;
    }
    
    public void setTempoMedioNaPagina(final BigDecimal tempoMedioNaPagina) {
        this.tempoMedioNaPagina = tempoMedioNaPagina;
    }
    
    public Long getSessoes() {
        return this.sessoes;
    }
    
    public void setSessoes(final Long sessoes) {
        this.sessoes = sessoes;
    }
    
    public BigDecimal getDuracaoDasSessoes() {
        return this.duracaoDasSessoes;
    }
    
    public void setDuracaoDasSessoes(final BigDecimal duracaoDasSessoes) {
        this.duracaoDasSessoes = duracaoDasSessoes;
    }
    
    public BigDecimal getMediaDeSessoes() {
        return this.mediaDeSessoes;
    }
    
    public void setMediaDeSessoes(final BigDecimal mediaDeSessoes) {
        this.mediaDeSessoes = mediaDeSessoes;
    }
    
    public Long getUsuarios() {
        return this.usuarios;
    }
    
    public void setUsuarios(final Long usuarios) {
        this.usuarios = usuarios;
    }
    
    public LocalDate getDataDaPesquisa() {
        return this.dataDaPesquisa;
    }
    
    public void setDataDaPesquisa(final LocalDate dataDaPesquisa) {
        this.dataDaPesquisa = dataDaPesquisa;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
}
