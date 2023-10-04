package com.devgbrl.application.seeds;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import com.devgbrl.domain.exceptions.BadArgumentException;
import com.devgbrl.domain.models.dtos.SignUpDTO;
import com.devgbrl.domain.models.entities.Account;
import com.devgbrl.domain.models.entities.Agenda;
import com.devgbrl.domain.models.entities.AgendaEvent;
import com.devgbrl.domain.services.AuthService;
import com.devgbrl.domain.services.AccountService;
import com.devgbrl.domain.services.AgendaEventService;
import com.devgbrl.domain.services.AgendaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class Seeder implements ApplicationListener<ApplicationStartedEvent> {

    private final AuthService authService;
    private final AccountService userService;
    private final AgendaService agendaService;
    private final AgendaEventService agendaEventService;

    private static final Logger log = LogManager.getLogger();

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        seedUsers();
        seedEvents();
    }

    private void seedUsers() {
        List<SignUpDTO> users = Arrays.asList(
                SignUpDTO
                        .builder()
                        .username("admin")
                        .name("Someone")
                        .password("admin@123")
                        .passwordConfirm("admin@123")
                        .build());

        for (SignUpDTO signUpItem : users) {
            if (userService.existsByUsername(signUpItem.getUsername())) {
                log.info("Usuário '{}' já existe, ignorando", signUpItem.getUsername());
                continue;
            }

            Account acountUser = authService.register(signUpItem);
            log.info("Usuário '{}' cadastrado", signUpItem.getUsername());
            userService.save(acountUser);
        }
    }

    private void seedEvents() {
        Account adminAccount = userService.findByUsername("admin")
                .orElseThrow(() -> new BadArgumentException("Usuário 'admin' não encontrado"));
        Agenda adminAgenda = agendaService.getOrCreateForAccount(adminAccount);

        Collection<AgendaEvent> events = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            events.add(AgendaEvent.builder()
                    .name("event" + String.valueOf(i))
                    .description("Um evento incrível")
                    .date(LocalDate.now())
                    .agenda(adminAgenda)
                    .build());
        }

        agendaEventService.saveAll(events);
    }

}
