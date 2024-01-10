package com.postech.parquimetro.domain.email;

import com.postech.parquimetro.domain.enums.StatusEmail;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID emailId;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;

    public EmailDto convertToDTO() {
        return new EmailDto(
                this.ownerRef,
                this.emailFrom,
                this.emailTo,
                this.subject,
                this.text
        );
    }
}
