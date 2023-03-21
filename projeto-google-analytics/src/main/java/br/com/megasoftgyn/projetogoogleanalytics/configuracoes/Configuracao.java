package br.com.megasoftgyn.projetogoogleanalytics.configuracoes;

import com.google.api.client.json.gson.GsonFactory;

public class Configuracao {
    
    private Configuracao() {}
    
    public static final String LIMITE_MINIMO_DE_DIAS = "50DaysAgo";
    
    public static final String LIMITE_MAXIMO_DE_DIAS = "Today";
    
    public static final String BUSCA_URL = "ga:pagePath";
    
    public static final String BUSCA_MODULO_NA_URL = "ga:pagePathLevel2";
    
    public static final String BUSCA_FUNCIONALIDADE_NA_URL = "ga:pagePathLevel3";
    
    public static final String BUSCA_COMPLEMENTO_NA_URL = "ga:pagePathLevel4";
    
    public static final String DATA_DA_PESQUISA = "ga:date";
    
    public static final String METRICA_VIZUALIZACOES_NA_PAGINA = "ga:pageviews";
    
    public static final String METRICA_TEMPO_NA_PAGINA = "ga:timeOnPage";
    
    public static final String METRICA_MEDIA_TEMPO_NA_PAGINA = "ga:avgTimeOnPage";
    
    public static final String METRICA_SESSOES = "ga:sessions";
    
    public static final String METRICA_DURACAO_SESSOES = "ga:sessionDuration";
    
    public static final String METRICA_MEDIA_SESSOES = "ga:avgSessionDuration";
    
    public static final String METRICA_USUARIOS = "ga:users";
    
    public static final String APPLICATION_NAME = "Hello Analytics Reporting";
    
    public static final String DADO_NAO_ENCONTRADO = "Nenhum dado encontrado";
    
    public static final String ERRO_INESPERADO = "Ocorreu um erro inesperado. Tente novamente mais tarde ou contate o Administrador";
    
    public final static String DADO_JA_EXISTE = "Esse dado já existe no banco de dados";
    
    public static final String VALOR_PAGE_TOKEN_INICIAL = "0";
    
    public static final Integer TAMANHO_MAXIMO_DE_PAGINAS_BUSCADAS = 10000;
    
    public static final Integer TAMANHO_MAXIMO_DE_RESULTADOS_POR_PAGINAS = 10;
    
    public static final Integer NUMERO_DA_PAGINA_INICIAL = 0;
    
    public static final Integer VALOR_ZERO_COMPARADOR = 0;
    
    public static final String DATA_NAO_PODE_SER_NULA = "A data não pode ser nula";
    
    public static final String MODULO = "Modulo";
    
    public static final String FUNCIONALIDADE = "Funcionalidade";
    
    public static final int INDICE_URL = 0;
    
    public static final int INDICE_MODULO = 1;
    
    public static final int INDICE_FUNCIONALIDADE = 2;
    
    public static final int INDICE_COMPLEMENTO = 3;
    
    public static final int INDICE_DATA_DA_PESQUISA = 4;
    
    public static final int INDICE_NUMERO_VISUALIZACAO = 0;
    
    public static final int INDICE_TEMPO_NA_PAGINA = 1;
    
    public static final int INDICE_TEMPO_MEDIO_NA_PAGINA = 2;
    
    public static final int INDICE_SESSOES = 3;
    
    public static final int INDICE_DURACAO_DAS_SESSOES = 4;
    
    public static final int INDICE_MEDIAS_DAS_SESSOES = 5;
    
    public static final int INDICE_USUARIOS = 6;
    
    public static final com.google.api.client.json.JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    
}
