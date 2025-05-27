package com.module.email_improver.controller;

import com.module.email_improver.model.EmailRequest;
import com.module.email_improver.model.ImprovedEmail;
import com.module.email_improver.model.ToneType;
import com.module.email_improver.service.EmailCacheService;
import com.module.email_improver.service.EmailImproverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDateTime;

@Controller
public class EmailController {
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    private final EmailImproverService emailImproverService;
    private final EmailCacheService emailCacheService;

    public EmailController(EmailImproverService emailImproverService, EmailCacheService emailCacheService) {
        this.emailImproverService = emailImproverService;
        this.emailCacheService = emailCacheService;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("emailRequest", new EmailRequest());
        model.addAttribute("tones", ToneType.values());
        model.addAttribute("history", emailCacheService.getHistory());
        return "email-form";
    }

    @PostMapping("/improve")
    public String improveEmail(@Valid @ModelAttribute EmailRequest emailRequest,
                               BindingResult bindingResult,
                               Model model) {
        logger.info("Start Controller-improveEmail /improve "+emailRequest.getEmailText()+" - Tone: "+emailRequest.getTone());

        if (bindingResult.hasErrors()) {
            model.addAttribute("tones", ToneType.values());
            return "email-form";
        }

        String improvedText = emailImproverService.improveEmail(
                emailRequest.getEmailText(),
                emailRequest.getTone()
        );

        // Guardar en caché
        ImprovedEmail improvedEmail = new ImprovedEmail();
        improvedEmail.setOriginalText(emailRequest.getEmailText());
        improvedEmail.setImprovedText(improvedText);
        improvedEmail.setTone(emailRequest.getTone());
        improvedEmail.setCreatedAt(LocalDateTime.now());
        emailCacheService.addToHistory(improvedEmail);

        logger.info("End Controller-improveEmail /improve "+improvedText);

        model.addAttribute("improvedEmail", improvedText);
        model.addAttribute("tones", ToneType.values());
        model.addAttribute("history", emailCacheService.getHistory());
        return "email-form";
    }
}
