package com.devgbrl.domain.services.impl;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.devgbrl.domain.exceptions.BadArgumentException;
import com.devgbrl.domain.exceptions.ItemDoesntExistsException;
import com.devgbrl.domain.models.dtos.AgendaEventEditDTO;
import com.devgbrl.domain.models.entities.Account;
import com.devgbrl.domain.models.entities.Agenda;
import com.devgbrl.domain.models.entities.AgendaEvent;
import com.devgbrl.domain.services.AgendaEventService;
import com.devgbrl.domain.services.AgendaService;
import com.devgbrl.domain.services.AuthService;
import com.devgbrl.infrastructure.data.repositories.AgendaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendaServiceImpl extends BasicCrudServiceImpl<Agenda, Long> implements AgendaService {

    private final AgendaRepository agendaRepository;
    private final AgendaEventService agendaEventService;
    private final AuthService authService;

    @Override
    public Optional<Agenda> findByAccount(Account account) {
        return agendaRepository.findByAccount(account);
    }

    @Override
    public Optional<Agenda> findByAccountId(Long accountId) {
        return agendaRepository.findByAccount_Id(accountId);
    }
    
    @Override
    @Transactional
    public void clear(Agenda agenda) {
        clearById(agenda.getId());
    }

    @Override
    @Transactional
    public void clearById(Long agendaId) {
        Agenda agenda = getById(agendaId).orElseThrow(() -> new ItemDoesntExistsException(agendaId.toString(), "A agenda informada não foi encontrada;"));
        
        Collection<AgendaEvent> events = agenda.getEvents();

        agendaEventService.deleteAllByIds(events.stream().map(AgendaEvent::getId).toList());
    }

    @Override
    public JpaRepository<Agenda, Long> getRepository() {
        return agendaRepository;
    }

    @Override
    @Transactional
    public Agenda getOrCreateForAccount(Account account) {
        Agenda agenda = findByAccount(account).orElse(null);

        if (Objects.nonNull(agenda)) {
            return agenda;
        }
        
        agenda = new Agenda();
        agenda.setAccount(account);

        return save(agenda);
    }

    @Override
    public Agenda getMyAgenda() {
        Account account = authService.getCurrentUser();

        return getOrCreateForAccount(account);
    }


    @Override
    public Collection<AgendaEvent> getMyAgendaEvents() {
        Agenda agenda = getMyAgenda();

        return agendaEventService.listAllByAgenda(agenda);
    }

    @Override
    public AgendaEvent getMyAgendaEventById(Long agendaEventId) {
        Agenda agenda = getMyAgenda();

        AgendaEvent agendaEvent = agendaEventService.getById(agendaEventId).orElseThrow(() -> new BadArgumentException("Evento não encontrado"));

        if (agendaEvent.getAgenda().getId() != agenda.getId()) {
            throw new BadArgumentException("Evento não encontrado");
        }

        return agendaEvent;
    }

    @Override
    public void deleteEventById(Long agendaEventId) {
        Agenda agenda = getMyAgenda();

        AgendaEvent agendaEvent = agendaEventService.getById(agendaEventId).orElseThrow(() -> new BadArgumentException("Evento não encontrado"));

        if (agendaEvent.getAgenda().getId() != agenda.getId()) {
            throw new BadArgumentException("Evento não encontrado");
        }

        agendaEventService.deleteById(agendaEventId);
    }

    @Override
    public AgendaEvent updateEvent(Long id, AgendaEventEditDTO eventEdit) {
        AgendaEvent agendaEvent = getMyAgendaEventById(eventEdit.getId());

        agendaEvent.setDate(eventEdit.getDate());
        agendaEvent.setDescription(eventEdit.getDescription());
        agendaEvent.setName(eventEdit.getName());

        agendaEvent = agendaEventService.save(agendaEvent);

        return agendaEvent;
    }

    @Override
    public void addEvent(AgendaEvent entity) {
        Agenda agenda = getMyAgenda();

        entity.setAgenda(agenda);

        agendaEventService.save(entity);
    }
    
}
