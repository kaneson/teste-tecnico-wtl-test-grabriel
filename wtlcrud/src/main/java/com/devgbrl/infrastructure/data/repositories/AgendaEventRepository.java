package com.devgbrl.infrastructure.data.repositories;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devgbrl.domain.models.entities.Agenda;
import com.devgbrl.domain.models.entities.AgendaEvent;

@Repository
public interface AgendaEventRepository extends JpaRepository<AgendaEvent, Long> {

    Collection<AgendaEvent> findByAgendaOrderById(Agenda agenda);

    Collection<AgendaEvent> findByAgendaAndDateBetweenOrderById(Agenda agenda, LocalDate today,
            LocalDate someDaysAhead);

}
