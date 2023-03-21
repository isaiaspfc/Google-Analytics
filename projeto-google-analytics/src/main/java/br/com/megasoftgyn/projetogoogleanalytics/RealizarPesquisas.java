package br.com.megasoftgyn.projetogoogleanalytics;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import br.com.megasoftgyn.projetogoogleanalytics.configuracoes.ApplicationProperties;
import br.com.megasoftgyn.projetogoogleanalytics.configuracoes.Configuracao;
import br.com.megasoftgyn.projetogoogleanalytics.excecoes.BadRequestException;
import br.com.megasoftgyn.projetogoogleanalytics.pesquisas.PesquisaDTO;

@Component
public class RealizarPesquisas {
    
    @Value(ApplicationProperties.KEY_FILE_LOCATION)
    private String caminhoDoArquivo;
    
    @Value(ApplicationProperties.VIEW_ID)
    private String chave;
    
    private RealizarPesquisas() {}
    
    private final Logger log = LoggerFactory.getLogger(RealizarPesquisas.class);
    
    public AnalyticsReporting initializeAnalyticsReporting() {
        try {
            final HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            final GoogleCredentials credential = ServiceAccountCredentials
                    .fromStream(new FileInputStream(this.caminhoDoArquivo))
                    .createScoped(AnalyticsReportingScopes.all());
            final HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credential);
            return new AnalyticsReporting.Builder(httpTransport, Configuracao.JSON_FACTORY, requestInitializer)
                    .setApplicationName(Configuracao.APPLICATION_NAME).build();
        } catch (GeneralSecurityException | IOException e) {
            this.log.info(String.format("Falha ao conectar com a API. Error: %s", e.getMessage()));
            throw new BadRequestException(Configuracao.ERRO_INESPERADO);
        }
        
    }
    
    public GetReportsResponse pesquisarPorPaginas(
            final AnalyticsReporting service,
            final String pageToken,
            final LocalDate dataInicial,
            final LocalDate dataFinal) {
        
        final ArrayList<Dimension> dimensoes = new ArrayList<>();
        final List<String> nomeDasDimensoes = new ArrayList<>();
        nomeDasDimensoes.add(Configuracao.BUSCA_URL);
        nomeDasDimensoes.add(Configuracao.BUSCA_MODULO_NA_URL);
        nomeDasDimensoes.add(Configuracao.BUSCA_FUNCIONALIDADE_NA_URL);
        nomeDasDimensoes.add(Configuracao.BUSCA_COMPLEMENTO_NA_URL);
        nomeDasDimensoes.add(Configuracao.DATA_DA_PESQUISA);
        for (int percorrerArrayDasDimensoes = 0; nomeDasDimensoes.size() > percorrerArrayDasDimensoes; percorrerArrayDasDimensoes++) {
            final Dimension dimension = new Dimension().setName(nomeDasDimensoes.get(percorrerArrayDasDimensoes));
            dimensoes.add(dimension);
        }
        
        final ArrayList<Metric> metricas = new ArrayList<>();
        final List<String> nomeDasMetricas = new ArrayList<>();
        nomeDasMetricas.add(Configuracao.METRICA_VIZUALIZACOES_NA_PAGINA);
        nomeDasMetricas.add(Configuracao.METRICA_TEMPO_NA_PAGINA);
        nomeDasMetricas.add(Configuracao.METRICA_MEDIA_TEMPO_NA_PAGINA);
        nomeDasMetricas.add(Configuracao.METRICA_SESSOES);
        nomeDasMetricas.add(Configuracao.METRICA_DURACAO_SESSOES);
        nomeDasMetricas.add(Configuracao.METRICA_MEDIA_SESSOES);
        nomeDasMetricas.add(Configuracao.METRICA_USUARIOS);
        
        for (int percorrerArrayDasMetricas = 0; nomeDasMetricas.size() > percorrerArrayDasMetricas; percorrerArrayDasMetricas++) {
            final Metric metric = new Metric().setExpression(nomeDasMetricas.get(percorrerArrayDasMetricas));
            metricas.add(metric);
        }
        return this.gerarPesquisas(service, dimensoes, metricas, pageToken, dataInicial, dataFinal);
        
    }
    
    public GetReportsResponse gerarPesquisas(
            final AnalyticsReporting service,
            final List<Dimension> listaDimensao,
            final List<Metric> listaMetrica,
            final String pageToken,
            final LocalDate dataInicial,
            final LocalDate dataFinal) {
        
        final DateRange dateRange = new DateRange();
        dateRange.setStartDate(dataInicial.format(DateTimeFormatter.ofPattern("yyy-MM-dd")));
        dateRange.setEndDate(dataFinal.format(DateTimeFormatter.ofPattern("yyy-MM-dd")));
        
        final ReportRequest request = new ReportRequest().setPageSize(Configuracao.TAMANHO_MAXIMO_DE_PAGINAS_BUSCADAS)
                .setViewId(this.chave)
                .setDateRanges(Arrays.asList(dateRange))
                .setMetrics(listaMetrica)
                .setDimensions(listaDimensao)
                .setPageToken(pageToken);
        
        final ArrayList<ReportRequest> requests = new ArrayList<>();
        requests.add(request);
        
        final GetReportsRequest getReport = new GetReportsRequest().setReportRequests(requests);
        try {
            return service.reports().batchGet(getReport).execute();
        } catch (final IOException e) {
            this.log.info(String.format("Falha ao buscar os dados do banco de dados. Erro: %s", e.getMessage()));
            throw new BadRequestException(Configuracao.ERRO_INESPERADO);
        }
        
    }
    
    public List<PesquisaDTO> gerarPaginas(
            final AnalyticsReporting service,
            final List<PesquisaDTO> relatorios,
            String pageToken,
            final LocalDate dataInicial,
            final LocalDate dataFinal) {
        final GetReportsResponse response = this.pesquisarPorPaginas(service, pageToken, dataInicial, dataFinal);
        
        for (final Report report : response.getReports()) {
            final List<ReportRow> rows = report.getData().getRows();
            
            for (final ReportRow row : rows) {
                final List<String> dimensions = row.getDimensions();
                final List<DateRangeValues> metrics = row.getMetrics();
                final DateRangeValues valor = metrics.get(0);
                
                final PesquisaDTO paginasDTO = new PesquisaDTO(
                        dimensions.get(Configuracao.INDICE_URL),
                        dimensions.get(Configuracao.INDICE_MODULO),
                        dimensions.get(Configuracao.INDICE_FUNCIONALIDADE),
                        dimensions.get(Configuracao.INDICE_COMPLEMENTO),
                        dimensions.get(Configuracao.INDICE_DATA_DA_PESQUISA),
                        valor.getValues().get(Configuracao.INDICE_NUMERO_VISUALIZACAO),
                        valor.getValues().get(Configuracao.INDICE_TEMPO_NA_PAGINA),
                        valor.getValues().get(Configuracao.INDICE_TEMPO_MEDIO_NA_PAGINA),
                        valor.getValues().get(Configuracao.INDICE_SESSOES),
                        valor.getValues().get(Configuracao.INDICE_DURACAO_DAS_SESSOES),
                        valor.getValues().get(Configuracao.INDICE_MEDIAS_DAS_SESSOES),
                        valor.getValues().get(Configuracao.INDICE_USUARIOS));
                
                relatorios.add(paginasDTO);
                pageToken = report.getNextPageToken();
            }
        }
        if (Objects.nonNull(pageToken)) {
            return this.gerarPaginas(service, relatorios, pageToken, dataInicial, dataFinal);
        } else {
            Collections.sort(relatorios);
            return relatorios;
        }
    }
    
}
