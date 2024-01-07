package com.postech.parquimetro.domain.session;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.enums.PaymentMethod;
import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.vehicle.Vehicle;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSession {

    @Id
    private String id;

    @DBRef
    @NotNull
    private Customer customer;

    @DBRef
    @NotNull
    private Vehicle vehicle;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private SessionType sessionType;

    @NotNull
    private LocalDateTime startSession;

    private LocalDateTime endSession;

    private Double price;

    private Integer status;

    public ParkingSessionDTO convertToDTO() {
        return new ParkingSessionDTO(
                this.id,
                this.customer != null ? this.customer.getId() : null,
                this.vehicle != null ? this.vehicle.getLicenseplate() : null,
                this.paymentMethod,
                this.sessionType,
                this.startSession,
                this.endSession,
                this.price,
                this.status
        );
    }
}
