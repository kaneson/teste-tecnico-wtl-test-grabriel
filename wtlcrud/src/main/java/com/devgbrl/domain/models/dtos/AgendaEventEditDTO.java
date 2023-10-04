package com.devgbrl.domain.models.dtos;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.devgbrl.domain.models.entities.Agenda;
import com.devgbrl.domain.models.entities.AgendaEvent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendaEventEditDTO {
    
    private Long id;
    private String name;
    private String description;
    private LocalDate date;

    public static AgendaEventEditDTO fromEntity(AgendaEvent event) {
        return AgendaEventEditDTO
            .builder()
            .id(event.getId())
            .name(event.getName())
            .description(event.getDescription())
            .date(event.getDate())
            .build();
    }

    public AgendaEvent toEntity(Agenda agenda) {
        
        return AgendaEvent
            .builder()
            .id(id)
            .description(getDescription())
            .name(getName())
            .date(getDate())
            .build();
    }

}
