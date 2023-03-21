package br.com.megasoftgyn.jasper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/*
 * 1. Classe responsável pela gerência do Jasper.
 */
public final class RelatorioUtil {
    
    private RelatorioUtil() {}
    
    public static byte[] gerarPdf(
            final Map<String, Object> parametros,
            final List<?> dataSource,
            final InputStream inputStream) {
        
        final JasperPrint relatorio;
        
        try {
            if (dataSource != null && !CollectionUtils.isEmpty(dataSource)) {
                relatorio = JasperFillManager.fillReport(inputStream, parametros, new JRBeanCollectionDataSource(dataSource, false));
            } else {
                relatorio = JasperFillManager.fillReport(inputStream, parametros, new JREmptyDataSource(1));
            }
            
            return JasperExportManager.exportReportToPdf(relatorio);
            
        } catch (final JRException e) {
            throw new RuntimeException("Ocorreu um erro durante a geração do seu relatório", e);
        } finally {
            fecharInputStream(inputStream);
        }
    }
    
    private static void fecharInputStream(final InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (final IOException e) {
                throw new RuntimeException("Ocorreu um erro durante a finalização do seu relatório", e);
            }
        }
    }
    
}
