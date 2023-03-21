package br.com.megasoftgyn.projetogoogleanalytics.pesquisas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.stereotype.Repository;

import br.com.megasoftgyn.projetogoogleanalytics.Pagina;
import br.com.megasoftgyn.projetogoogleanalytics.RequisicaoFiltro;
import br.com.megasoftgyn.projetogoogleanalytics.configuracoes.Configuracao;

@Repository
public class PesquisaRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public void inserir(final Pesquisa paginas) {
        this.entityManager.persist(paginas);
    }
    
    public void cadastrarTodosComQueryNativa(final List<PesquisaDTO> relatorios) {
        final String sql = this.montarQueryNativaParaCadastrarOsRelatorios();
        
        for (final PesquisaDTO relatorio : relatorios) {
            final Query query = this.entityManager.createNativeQuery(sql);
            this.setarParametrosDaQuery(query, relatorio);
            query.executeUpdate();
        }
    }
    
    private String montarQueryNativaParaCadastrarOsRelatorios() {
        final StringBuilder sql = new StringBuilder();
        
        sql.append(" INSERT INTO pesquisa");
        sql.append(" (url, modulo, funcionalidade, complemento, numero_vizualizacoes, tempo_pagina,");
        sql.append(" tempo_medio_pagina, sessoes, duracao_sessoes, media_sessoes, usuarios, data_pesquisa)");
        sql.append(" VALUES(:url, :modulo, :funcionalidade, :complemento, :numero_vizualizacoes, :tempo_pagina,");
        sql.append(" :tempo_medio_pagina, :sessoes, :duracao_sessoes, :media_sessoes, :usuarios, :data_pesquisa)");
        sql.append(" ON CONFLICT DO NOTHING;");
        
        return sql.toString();
    }
    
    private void setarParametrosDaQuery(final Query query, final PesquisaDTO relatorio) {
        query.setParameter("url", relatorio.getUrl());
        query.setParameter("modulo", relatorio.getModulo());
        query.setParameter("funcionalidade", relatorio.getFuncionalidade());
        query.setParameter("complemento", relatorio.getComplemento());
        query.setParameter("numero_vizualizacoes", relatorio.getNumeroDeVizualizacoes());
        query.setParameter("tempo_pagina", relatorio.getTempoNaPagina());
        query.setParameter("tempo_medio_pagina", relatorio.getTempoMedioNaPagina());
        query.setParameter("sessoes", relatorio.getSessoes());
        query.setParameter("duracao_sessoes", relatorio.getDuracaoDasSessoes());
        query.setParameter("media_sessoes", relatorio.getMediaDeSessoes());
        query.setParameter("usuarios", relatorio.getUsuarios());
        query.setParameter("data_pesquisa", relatorio.getDataDaPesquisa());
    }
    
    public TypedQuery<PesquisaDTO> buscarDadosDoBanco(final RequisicaoFiltro requisicao, final String tipoDePesquisa) {
        final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<PesquisaDTO> criteria = cb.createQuery(PesquisaDTO.class);
        final Root<Pesquisa> from = criteria.from(Pesquisa.class);
        
        final List<Predicate> predicados = new ArrayList<>();
        if (Objects.nonNull(requisicao.getDataInicial())) {
            predicados.add(cb.greaterThanOrEqualTo(from.get(Pesquisa_.dataDaPesquisa), requisicao.getDataInicial()));
        }
        if (Objects.nonNull(requisicao.getDataFinal())) {
            predicados.add(cb.lessThanOrEqualTo(from.get(Pesquisa_.dataDaPesquisa), requisicao.getDataFinal()));
        }
        
        if (tipoDePesquisa.equals(Configuracao.MODULO)) {
            criteria.multiselect(this.montarCamposParaBuscarPorCodigoParaFormularioModulo(from, cb))
                    .where(predicados.toArray(new Predicate[] {}))
                    .groupBy(from.get(Pesquisa_.modulo));
        } else {
            criteria.multiselect(this.montarCamposParaBuscarPorCodigoParaFormularioFuncionalidade(from, cb))
                    .where(predicados.toArray(new Predicate[] {}))
                    .groupBy(from.get(Pesquisa_.modulo), from.get(Pesquisa_.funcionalidade));
        }
        return this.entityManager.createQuery(criteria);
    }
    
    private List<Selection<?>> montarCamposParaBuscarPorCodigoParaFormularioModulo(final Root<Pesquisa> from, final CriteriaBuilder cb) {
        return Arrays.asList(
                from.get(Pesquisa_.modulo),
                cb.sum(from.get(Pesquisa_.numeroDeVizualizacoes)),
                cb.sum(from.get(Pesquisa_.tempoNaPagina)),
                cb.sum(from.get(Pesquisa_.tempoMedioNaPagina)),
                cb.sum(from.get(Pesquisa_.sessoes)),
                cb.sum(from.get(Pesquisa_.duracaoDasSessoes)),
                cb.sum(from.get(Pesquisa_.mediaDeSessoes)),
                cb.sum(from.get(Pesquisa_.usuarios)));
    }
    
    private List<Selection<?>> montarCamposParaBuscarPorCodigoParaFormularioFuncionalidade(final Root<Pesquisa> from, final CriteriaBuilder cb) {
        return Arrays.asList(
                from.get(Pesquisa_.modulo),
                from.get(Pesquisa_.funcionalidade),
                cb.sum(from.get(Pesquisa_.numeroDeVizualizacoes)),
                cb.sum(from.get(Pesquisa_.tempoNaPagina)),
                cb.sum(from.get(Pesquisa_.tempoMedioNaPagina)),
                cb.sum(from.get(Pesquisa_.sessoes)),
                cb.sum(from.get(Pesquisa_.duracaoDasSessoes)),
                cb.sum(from.get(Pesquisa_.mediaDeSessoes)),
                cb.sum(from.get(Pesquisa_.usuarios)));
    }
    
    public Pagina<PesquisaDTO> buscarPorModulo(final RequisicaoFiltro requisicao) {
        final TypedQuery<PesquisaDTO> todosRelatorios = this.buscarDadosDoBanco(requisicao, Configuracao.MODULO);
        return new Pagina<>(
                (long) todosRelatorios.getResultList().size(),
                requisicao.getTamanhoDaPagina(),
                todosRelatorios.setFirstResult(requisicao.getOffset()).setMaxResults(requisicao.getTamanhoDaPagina()).getResultList(),
                requisicao.getNumeroDaPagina());
    }
    
    public Pagina<PesquisaDTO> buscarPorFuncionalidade(final RequisicaoFiltro requisicao) {
        final TypedQuery<PesquisaDTO> todosRelatorios = this.buscarDadosDoBanco(requisicao, Configuracao.FUNCIONALIDADE);
        return new Pagina<>((long) todosRelatorios.getResultList().size(),
                requisicao.getTamanhoDaPagina(),
                todosRelatorios.setFirstResult(requisicao.getOffset()).setMaxResults(requisicao.getTamanhoDaPagina()).getResultList(),
                requisicao.getNumeroDaPagina());
    }
}
