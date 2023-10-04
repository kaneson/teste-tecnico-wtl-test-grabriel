package com.devgbrl.domain.services;

import java.util.Collection;
import java.util.Optional;

import com.devgbrl.domain.models.dtos.AgendaEventEditDTO;
import com.devgbrl.domain.models.entities.Account;
import com.devgbrl.domain.models.entities.Agenda;
import com.devgbrl.domain.models.entities.AgendaEvent;

public interface AgendaService extends WithCrud<Agenda, Long> {

    Agenda getOrCreateForAccount(Account account);
    
    Optional<Agenda> findByAccount(Account account);

    Optional<Agenda> findByAccountId(Long accountId);

    void clear(Agenda agenda);

    void clearById(Long agendaId);

    Agenda getMyAgenda();

    Collection<AgendaEvent> getMyAgendaEvents();

    AgendaEvent getMyAgendaEventById(Long agendaEventId);

    void deleteEventById(Long id);

    AgendaEvent updateEvent(Long id, AgendaEventEditDTO eventEdit);

    void addEvent(AgendaEvent entity);

}
