package com.module.email_improver.model;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

@Data
public class EmailRequest {
    @NotEmpty(message = "El texto del email no puede estar vacío")
    private String emailText;

    @NotEmpty(message = "Debe seleccionar un tono")
    private String tone;
}
