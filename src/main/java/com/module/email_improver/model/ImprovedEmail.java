package com.module.email_improver.model;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ImprovedEmail implements Serializable {
    private String originalText;
    private String improvedText;
    private String tone;
    private LocalDateTime createdAt;
}