package me.sathish.rag_app_svc.base;

import me.sathish.rag_app_svc.base.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RagInjectionService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RagInjectionService.class);
    private final VectorStore vectorStore;
    private final MailService mailService;

    @Value("classpath:/docs/article_thebeatnovember2025.pdf")
    private Resource marketPDF;

    @Value("${app.baseHost}")
    private String baseHost;

    public RagInjectionService(VectorStore vectorStore, MailService mailService) {
        this.vectorStore = vectorStore;
        this.mailService = mailService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting RAG injection process...");
        
        var pdfReader = new ParagraphPdfDocumentReader(marketPDF);
        TextSplitter textSplitter = new TokenTextSplitter();
        List<Document> documents = textSplitter.apply(pdfReader.get());
        
        vectorStore.accept(documents);
        
        log.info("RAG injection completed. Sending notification email...");
        
        // Prepare template variables
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("documentName", marketPDF.getFilename());
        templateVariables.put("completionTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        templateVariables.put("chunksProcessed", documents.size());
        templateVariables.put("baseHost", baseHost);
        
        // Send email using Thymeleaf template
        mailService.sendMailWithTemplate(
            "sjayapal@skminfotek.com",
            "RAG Injection Complete - " + marketPDF.getFilename(),
            "mail/rag-injection-complete",
            templateVariables
        );
        
        log.info("Notification email sent successfully");
    }
}
