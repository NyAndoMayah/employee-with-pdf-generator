package com.example.prog4.service;

import com.example.prog4.config.CompanyConf;
import com.example.prog4.model.Employee;
import com.example.prog4.model.utilities.Age;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Service
public class PdfUtils {

    @Autowired
    private TemplateEngine templateEngine;

    public byte[] generatePdfFromHtml(String htmlTemplate, Employee employee) throws Exception {

        Context context = new Context();
        context.setVariable("templateEmployee", employee);
        context.setVariable("company", new CompanyConf());
        int age = Age.getAge(employee.getBirthDate());
        context.setVariable("age", age);

        String processedHtml = templateEngine.process(htmlTemplate, context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml);
            renderer.layout();
            renderer.createPDF(outputStream);

            return outputStream.toByteArray();
        }
    }
}
