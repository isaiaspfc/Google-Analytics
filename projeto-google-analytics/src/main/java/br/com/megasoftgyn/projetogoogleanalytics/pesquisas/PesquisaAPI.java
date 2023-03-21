package br.com.megasoftgyn.projetogoogleanalytics.pesquisas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.megasoftgyn.projetogoogleanalytics.Pagina;
import br.com.megasoftgyn.projetogoogleanalytics.RequisicaoFiltro;
import br.com.megasoftgyn.projetogoogleanalytics.configuracoes.Configuracao;

@RestController
@RequestMapping(path = "/pesquisa", produces = {"application/json", "text/json"})
public class PesquisaAPI {
    
    @Autowired
    private PesquisaService paginasService;
    
    @PostMapping
    public void salvarDadosNoBanco(@Validated final RequisicaoFiltro requisicao) {
        this.paginasService.salvarDadosNoBanco(requisicao);
    }
    
    @GetMapping("/modulos")
    public Pagina<PesquisaDTO> listarModulos(final RequisicaoFiltro requisicao) {
        return this.paginasService.listarModulos(requisicao);
    }
    
    @GetMapping("/funcionalidades")
    public Pagina<PesquisaDTO> listarFuncionalidades(
            final RequisicaoFiltro requisicao) {
        return this.paginasService.listarFuncionalidades(requisicao);
    }
    
    @GetMapping("/imprimir-relatorio-modulo")
    public ResponseEntity<byte[]> imprimirRelatorioModulo(final RequisicaoFiltro requisicao) {
        final byte[] relatorio = this.paginasService.imprimirRelatorios(requisicao, Configuracao.MODULO);
        return new ResponseEntity<>(relatorio, HttpStatus.OK);
    }
    
    @GetMapping("/imprimir-relatorio-funcionalidade")
    public ResponseEntity<byte[]> imprimirRelatorioFuncionalidade(final RequisicaoFiltro requisicao) {
        final byte[] relatorio = this.paginasService.imprimirRelatorios(requisicao, Configuracao.FUNCIONALIDADE);
        return new ResponseEntity<>(relatorio, HttpStatus.OK);
    }
}
