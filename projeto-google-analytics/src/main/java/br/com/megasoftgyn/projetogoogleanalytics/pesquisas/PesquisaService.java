package br.com.megasoftgyn.projetogoogleanalytics.pesquisas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;

import br.com.megasoftgyn.jasper.RelatorioUtil;
import br.com.megasoftgyn.projetogoogleanalytics.Pagina;
import br.com.megasoftgyn.projetogoogleanalytics.RealizarPesquisas;
import br.com.megasoftgyn.projetogoogleanalytics.RequisicaoFiltro;
import br.com.megasoftgyn.projetogoogleanalytics.configuracoes.Configuracao;
import br.com.megasoftgyn.projetogoogleanalytics.excecoes.BadRequestException;

@Service
public class PesquisaService {
    
    @Autowired
    private PesquisaRepository paginasRepository;
    
    @Autowired
    private RealizarPesquisas realizarPesquisas;
    
    private final Logger log = LoggerFactory.getLogger(PesquisaService.class);
    
    @Transactional
    public void salvarDadosNoBanco(final RequisicaoFiltro requisicao) {
        List<PesquisaDTO> relatorios = new ArrayList<>();
        final AnalyticsReporting service = this.realizarPesquisas.initializeAnalyticsReporting();
        relatorios = this.realizarPesquisas.gerarPaginas(
                service,
                relatorios,
                Configuracao.VALOR_PAGE_TOKEN_INICIAL,
                requisicao.getDataInicial(),
                requisicao.getDataFinal());
        this.paginasRepository.cadastrarTodosComQueryNativa(relatorios);
    }
    
    public byte[] imprimirRelatorios(final RequisicaoFiltro requisicao, final String tipoDePesquisa) {
        final Map<String, Object> parametros = new HashMap<>();
        
        List<PesquisaDTO> dtos;
        InputStream stream = null;
        String caminhoDoRelatorioJasper;
        if (tipoDePesquisa.equals("Modulo")) {
            dtos = this.paginasRepository.buscarPorModulo(requisicao).getRegistros();
            caminhoDoRelatorioJasper = "src/main/resources/relatorioModulo.jasper";
        } else {
            dtos = this.paginasRepository.buscarPorFuncionalidade(requisicao).getRegistros();
            caminhoDoRelatorioJasper = "src/main/resources/relatorioFuncionalidade.jasper";
        }
        try {
            stream = new FileInputStream(caminhoDoRelatorioJasper);
        } catch (final FileNotFoundException e) {
            this.log.info(String.format("Não foi possível encontrar o caminho para gerar o relatório %s", e.getMessage()));
            throw new BadRequestException(Configuracao.ERRO_INESPERADO);
        }
        return RelatorioUtil.gerarPdf(parametros, dtos, stream);
    }
    
    @Transactional
    public Pagina<PesquisaDTO> listarModulos(
            final RequisicaoFiltro requisicao) {
        return this.paginasRepository.buscarPorModulo(requisicao);
    }
    
    @Transactional
    public Pagina<PesquisaDTO> listarFuncionalidades(
            final RequisicaoFiltro requisicao) {
        return this.paginasRepository.buscarPorFuncionalidade(requisicao);
    }
    
}
