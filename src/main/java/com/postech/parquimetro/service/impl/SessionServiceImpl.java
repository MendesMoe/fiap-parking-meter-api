package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.enums.PaymentMethod;
import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.session.ParkingSession;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.exception.BadRequestException;
import com.postech.parquimetro.exception.InvalidPaymentMethodException;
import com.postech.parquimetro.repository.CustomerRepository;
import com.postech.parquimetro.repository.SessionRepository;
import com.postech.parquimetro.service.SessionService;
import com.postech.parquimetro.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private TimeCalculatorServiceImpl timeCalculatorService;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TimeCalculatorServiceImpl timeService;

    @Override
    public List<ParkingSession> getAll() {
        return this.sessionRepository.findAll();
    }

    @Override
    public ParkingSessionDTO create(ParkingSession parkingSession) throws ValidationException {
        if (parkingSession.getSessionType() == SessionType.FREE_TIME && parkingSession.getPaymentMethod() == PaymentMethod.PIX) {
            throw new InvalidPaymentMethodException();
        }

        ParkingSession session = saveParkingSession(parkingSession);
        ParkingSessionDTO sessionDTO = session.convertToDTO();

        scheduleSessionNotifications(sessionDTO);

        return sessionDTO;
    }

    private void scheduleSessionNotifications(ParkingSessionDTO sessionDTO) {
        // Se for fixo, ele calcula os 15 minutos com base no endSession e programa o envio do email 15 minutos antes
        if (sessionDTO.sessionType() == SessionType.FIXED_TIME) {
            if (sessionDTO.endSession() != null){
                //calcula menos 15 minutos antes do fim. Se a sessionEnd é 12h00, a gente vê que horas sao (por exemplo 11h10), calcula 15 minutos antes de 12h00 (11h45) e subtrai. 11h45 - 11h10 = 35 minutos e transforma em millisegundos
                long delay = this.timeService.get15MinBeforeExpiration(sessionDTO);
                log.info("Delay -->" + delay);

                this.sendDelayedMessage(sessionDTO, (int) delay);
            } else {
                throw new BadRequestException("Obrigatorio informar o horario de fim para sessão fixa");
            }
           
        } else if (sessionDTO.sessionType() == SessionType.FREE_TIME) {
            // Se for free, ele calcula +45 minutos a partir da hora de criacao e programa um envio para esta hora
            long delay = this.timeService.get45MinInMilliseconds(sessionDTO);
            log.info("Delay -->" + delay);
            this.sendDelayedMessage(sessionDTO, (int) delay);
        }
    }

    private ParkingSession saveParkingSession(ParkingSession parkingSession) {
        //check s'il a un customer avec cet id
        Customer customer = this.customerRepository.findById(parkingSession.getCustomer().getCustomerID())
                        .orElseThrow(() -> new IllegalArgumentException("Customer not found with the ID: " + parkingSession.getCustomer().getCustomerID()));
        parkingSession.setCustomer(customer);


        Vehicle vehicle = this.vehicleService.getById(parkingSession.getVehicle().getLicensePlate());
        parkingSession.setVehicle(vehicle);

        return this.sessionRepository.save(parkingSession);
    }

    private void sendDelayedMessage(ParkingSessionDTO messageSession, int delay) {
        rabbitTemplate.convertAndSend("myDelayedExchange", "myRoutingKey", messageSession, message -> {
            message.getMessageProperties().setDelay(delay);
            return message;
        });
    }
    @Override
    public ParkingSession getById(String id) {
        return this.sessionRepository.findById(Long.valueOf(id))
                .orElseThrow(()-> new IllegalArgumentException("The Session has not found with ID: " + id));
    }

    @Override // funciona com um id existente mas se for um customerid inexistente devolve um 403. Procurar docs sobre excessoes
    public List<ParkingSession> getByCustomer(String customerID) {
        Customer customer = this.customerRepository.findById(customerID)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerID));

        return this.sessionRepository.findByCustomer(customer);
    }

    @Override
    public ParkingSessionDTO endTheSession(String sessionID) {
        log.info("ending session: [{}]", sessionID);
        ParkingSession parkingSession = sessionRepository.getById(sessionID);

        LocalDateTime now = LocalDateTime.now();
        parkingSession.setEndSession(now);
        Duration totalTime = Duration.between(parkingSession.getStartSession(), parkingSession.getEndSession());
        String duration = formatterDuration(totalTime);
        BigDecimal totalPrice = timeCalculatorService.calculatePriceForDuration(totalTime);

        parkingSession.setStatus(0);
        parkingSession.setPrice(totalPrice.doubleValue());
        parkingSession = sessionRepository.save(parkingSession);
        log.info("session: [{}] from customer: [{}], successfully ended", sessionID, parkingSession.getCustomer().getCustomerID());

        emailService.sendMailWithInvoice(parkingSession, duration);
        ParkingSessionDTO sessionDTO = parkingSession.convertToDTO(duration);
        return sessionDTO;
    }

    private static String formatterDuration(Duration totalTime) {
        long h = totalTime.toHours();
        long m = totalTime.toMinutesPart();
        long s = totalTime.toSecondsPart();
        String duration = String.format("%02d:%02d:%02d", h, m, s);
        return duration;
    }

    @Override
    public ParkingSession update(ParkingSession updateSession) {
        return this.sessionRepository.save(updateSession);
    }

    @Override
    public void deleteById(String id) {
        this.sessionRepository.deleteById(id);
    }
}
