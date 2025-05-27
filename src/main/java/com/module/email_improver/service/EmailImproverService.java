package com.module.email_improver.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailImproverService {
    private static final Logger logger = LoggerFactory.getLogger(EmailImproverService.class);

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public String improveEmail(String emailText, String tone) {
        logger.info("Begin Service improveEmail");

        OpenAiService service = new OpenAiService(openaiApiKey);

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", "Eres un experto en mejorar la redacción de emails. Tu tarea es mejorar el texto manteniendo la esencia del mensaje pero ajustándolo al tono solicitado."));
        messages.add(new ChatMessage("user", String.format("Mejora el siguiente email utilizando un tono %s. Texto original: %s", tone, emailText)));
        logger.info("Message: "+messages.toString());

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-3.5-turbo")
                .build();

        return service.createChatCompletion(completionRequest)
                .getChoices().get(0).getMessage().getContent();
    }
}