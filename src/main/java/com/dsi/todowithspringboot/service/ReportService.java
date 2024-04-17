package com.dsi.todowithspringboot.service;

import com.dsi.todowithspringboot.entity.Todo;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private final TodoService todoService;

    public ReportService(TodoService todoService) {
        this.todoService = todoService;
    }

    public void exportTodoList(String fileFormat, HttpServletResponse response) throws IOException, JRException {
        String basePath = "/home/rakib/Documents/reports/";

        List<Todo> todos = todoService.findAll();

        File file = ResourceUtils.getFile("classpath:reports/todo-list-pdf.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(todos);

        Map<String, Object> params = new HashMap<>();
        params.put("createdBy", "Abdur Rakib");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        if (fileFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, basePath + "/todo_list.html");
        } else if (fileFormat.equalsIgnoreCase("pdf")) {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=todo-list-" + System.currentTimeMillis() + ".pdf");

            exporter.exportReport();
        } else {
            //
        }
    }
}
