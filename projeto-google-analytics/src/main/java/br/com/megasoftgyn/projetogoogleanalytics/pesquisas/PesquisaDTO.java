package br.com.megasoftgyn.projetogoogleanalytics.pesquisas;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class PesquisaDTO implements Comparable<PesquisaDTO> {
    
    private String url;
    
    private String modulo;
    
    private String funcionalidade;
    
    private String complemento;
    
    private Long numeroDeVizualizacoes = 0L;
    
    private BigDecimal tempoNaPagina = BigDecimal.ZERO;
    
    private BigDecimal tempoMedioNaPagina = BigDecimal.ZERO;
    
    private Long sessoes = 0L;
    
    private BigDecimal duracaoDasSessoes = BigDecimal.ZERO;
    
    private BigDecimal mediaDeSessoes = BigDecimal.ZERO;
    
    private Long usuarios = 0L;
    
    private LocalDate dataDaPesquisa;
    
    public PesquisaDTO(
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
    
    public PesquisaDTO(
            final String modulo,
            final Long numeroDeVizualizacoes,
            final BigDecimal tempoNaPagina,
            final BigDecimal tempoMedioNaPagina,
            final Long sessoes,
            final BigDecimal duracaoDasSessoes,
            final BigDecimal mediaDeSessoes,
            final Long usuarios) {
        this.modulo = modulo;
        this.numeroDeVizualizacoes = numeroDeVizualizacoes;
        this.tempoNaPagina = tempoNaPagina;
        this.tempoMedioNaPagina = tempoMedioNaPagina;
        this.sessoes = sessoes;
        this.duracaoDasSessoes = duracaoDasSessoes;
        this.mediaDeSessoes = mediaDeSessoes;
        this.usuarios = usuarios;
    }
    
    public PesquisaDTO(
            final String modulo,
            final String funcionalidade,
            final Long numeroDeVizualizacoes,
            final BigDecimal tempoNaPagina,
            final BigDecimal tempoMedioNaPagina,
            final Long sessoes,
            final BigDecimal duracaoDasSessoes,
            final BigDecimal mediaDeSessoes,
            final Long usuarios) {
        this.modulo = modulo;
        this.funcionalidade = funcionalidade;
        this.numeroDeVizualizacoes = numeroDeVizualizacoes;
        this.tempoNaPagina = tempoNaPagina;
        this.tempoMedioNaPagina = tempoMedioNaPagina;
        this.sessoes = sessoes;
        this.duracaoDasSessoes = duracaoDasSessoes;
        this.mediaDeSessoes = mediaDeSessoes;
        this.usuarios = usuarios;
    }
    
    public PesquisaDTO(
            final String url,
            final String modulo,
            final String funcionalidade,
            final String complemento,
            final String dataDaPesquisa,
            final String numeroDeVizualizacoes,
            final String tempoNaPagina,
            final String tempoMedioNaPagina,
            final String sessoes,
            final String duracaoDasSessoes,
            final String mediaDeSessoes,
            final String usuarios) {
        this.url = url;
        this.modulo = modulo;
        this.funcionalidade = funcionalidade;
        this.complemento = complemento;
        this.dataDaPesquisa = this.converterData(dataDaPesquisa);
        this.numeroDeVizualizacoes = Long.parseLong(numeroDeVizualizacoes);
        this.tempoNaPagina = new BigDecimal(tempoNaPagina);
        this.tempoMedioNaPagina = new BigDecimal(tempoMedioNaPagina);
        this.sessoes = Long.parseLong(sessoes);
        this.duracaoDasSessoes = new BigDecimal(duracaoDasSessoes);
        this.mediaDeSessoes = new BigDecimal(mediaDeSessoes);
        this.usuarios = Long.parseLong(usuarios);
    }
    
    public PesquisaDTO(final Pesquisa paginas) {
        this.url = paginas.getUrl();
        this.modulo = paginas.getModulo();
        this.funcionalidade = paginas.getFuncionalidade();
        this.complemento = paginas.getComplemento();
        this.dataDaPesquisa = paginas.getDataDaPesquisa();
        this.numeroDeVizualizacoes = paginas.getNumeroDeVizualizacoes();
        this.tempoNaPagina = paginas.getTempoNaPagina();
        this.tempoMedioNaPagina = paginas.getTempoMedioNaPagina();
        this.sessoes = paginas.getSessoes();
        this.duracaoDasSessoes = paginas.getDuracaoDasSessoes();
        this.mediaDeSessoes = paginas.getMediaDeSessoes();
        this.usuarios = paginas.getUsuarios();
    }
    
    public PesquisaDTO() {}
    
    public LocalDate converterData(final String data) {
        return LocalDate.parse(data.substring(0, 4) + "-" + data.substring(4, 6) + "-" + data.substring(6, 8));
    }
    
    public void mesclar(final PesquisaDTO paginasDTO) {
        this.setNumeroDeVizualizacoes(this.getNumeroDeVizualizacoes() + paginasDTO.getNumeroDeVizualizacoes());
        this.setTempoNaPagina(this.getTempoNaPagina().add(paginasDTO.getTempoNaPagina()));
        this.setTempoMedioNaPagina(this.getTempoMedioNaPagina().add(paginasDTO.getTempoMedioNaPagina()));
        this.setSessoes(this.getSessoes() + paginasDTO.getSessoes());
        this.setDuracaoDasSessoes(this.getDuracaoDasSessoes().add(paginasDTO.getDuracaoDasSessoes()));
        this.setMediaDeSessoes(this.getMediaDeSessoes().add(paginasDTO.getMediaDeSessoes()));
        this.setUsuarios(this.getUsuarios() + paginasDTO.getUsuarios());
    }
    
    public void preencher(final Pesquisa paginas) {
        paginas.setUrl(this.getUrl());
        paginas.setModulo(this.getModulo());
        paginas.setFuncionalidade(this.getFuncionalidade());
        paginas.setComplemento(this.getComplemento());
        paginas.setDataDaPesquisa(this.getDataDaPesquisa());
        paginas.setNumeroDeVizualizacoes(this.getNumeroDeVizualizacoes());
        paginas.setTempoNaPagina(this.getTempoNaPagina());
        paginas.setTempoMedioNaPagina(this.getTempoMedioNaPagina());
        paginas.setSessoes(this.getSessoes());
        paginas.setDuracaoDasSessoes(this.getDuracaoDasSessoes());
        paginas.setMediaDeSessoes(this.getMediaDeSessoes());
        paginas.setUsuarios(this.getUsuarios());
        
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
    
    @Override
    public int compareTo(final PesquisaDTO o) {
        if (this.numeroDeVizualizacoes.compareTo(o.getNumeroDeVizualizacoes()) > 0) {
            return -1;
        }
        if (this.numeroDeVizualizacoes.compareTo(o.getNumeroDeVizualizacoes()) < 0) {
            return 1;
        }
        return 0;
    }
    
}
