package com.minegocio.backend.dto.faq;

import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Builder
public class FaqResponse {

    private Long id;
    private String question;
    private String answer;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}