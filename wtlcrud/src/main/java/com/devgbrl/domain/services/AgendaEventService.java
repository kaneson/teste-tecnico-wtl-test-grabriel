package com.devgbrl.domain.services;

import java.util.Collection;

import com.devgbrl.domain.models.entities.Agenda;
import com.devgbrl.domain.models.entities.AgendaEvent;

public interface AgendaEventService extends WithCrud<AgendaEvent, Long> {

    Collection<AgendaEvent> listAllByAgenda(Agenda agenda);

    Collection<AgendaEvent> listSomeNextDaysByAgenda(int days, Agenda agenda);

}
