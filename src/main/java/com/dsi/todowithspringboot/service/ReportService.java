package com.dsi.todowithspringboot.service;

import com.dsi.todowithspringboot.constants.ContentDispositionType;
import com.dsi.todowithspringboot.constants.ExportType;
import com.dsi.todowithspringboot.entity.Todo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private final TodoService todoService;

    public ReportService(TodoService todoService) {
        this.todoService = todoService;
    }

    public void exportTodoList(ExportType exportType, HttpServletRequest request, HttpServletResponse response) throws IOException, JRException {
        List<Todo> todos = todoService.findAll();

        String fileName = "todo-list-" + System.currentTimeMillis();
        String title = "Todo List";
        String jrxmlPath = "classpath:reports/todo/todo-list-" + exportType.toString().toLowerCase() + ".jrxml";

        exportReport(jrxmlPath, todos, exportType, ContentDispositionType.INLINE, fileName, title, response);
    }

    private void exportReport(String jrxmlPath, Collection<?> dataCollection, ExportType exportType, ContentDispositionType contentDispositionType, String fileName, String title, HttpServletResponse response) throws IOException, JRException {
        response.setContentType(exportType.getContentType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDispositionType.toString().toLowerCase() + ";filename=" + fileName + exportType.getExtension());

        File file = ResourceUtils.getFile(jrxmlPath);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataCollection);

        Map<String, Object> params = new HashMap<>();
        params.put("title", title);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        if (exportType == ExportType.PDF) {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            exporter.exportReport();
        } else if (exportType == ExportType.EXCEL) {
            SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
            reportConfigXLS.setSheetNames(new String[]{title});
            reportConfigXLS.setDetectCellType(true);
            reportConfigXLS.setCollapseRowSpan(false);

            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setConfiguration(reportConfigXLS);
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

            exporter.exportReport();
        } else if (exportType == ExportType.CSV) {
            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream()));
            exporter.exportReport();
        } else if (exportType == ExportType.DOCX) {
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            exporter.exportReport();
        } else {
            throw new RemoteException("File format doesn't match.");
        }
    }
}
