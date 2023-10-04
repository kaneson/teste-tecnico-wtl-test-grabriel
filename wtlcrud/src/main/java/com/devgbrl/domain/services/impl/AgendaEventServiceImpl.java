package com.devgbrl.domain.services.impl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.devgbrl.domain.models.entities.Agenda;
import com.devgbrl.domain.models.entities.AgendaEvent;
import com.devgbrl.domain.services.AgendaEventService;
import com.devgbrl.infrastructure.data.repositories.AgendaEventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendaEventServiceImpl extends BasicCrudServiceImpl<AgendaEvent, Long> implements AgendaEventService {

    private final AgendaEventRepository agendaEventRepository;

    @Override
    public JpaRepository<AgendaEvent, Long> getRepository() {
        return agendaEventRepository;
    }

    @Override
    public Collection<AgendaEvent> listAllByAgenda(Agenda agenda) {
        return agendaEventRepository.findByAgendaOrderById(agenda);
    }

    @Override
    public Collection<AgendaEvent> listSomeNextDaysByAgenda(int days, Agenda agenda) {
        LocalDate today = LocalDate.now();
        LocalDate someDaysAhead = today.plusDays(Long.valueOf(days));

        return agendaEventRepository.findByAgendaAndDateBetweenOrderById(agenda, today, someDaysAhead);
    }

    
}
