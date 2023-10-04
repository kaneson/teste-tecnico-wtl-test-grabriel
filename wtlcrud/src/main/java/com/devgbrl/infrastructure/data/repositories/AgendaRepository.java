package com.devgbrl.infrastructure.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devgbrl.domain.models.entities.Account;
import com.devgbrl.domain.models.entities.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    Optional<Agenda> findByAccount_Id(Long accountId);

    Optional<Agenda> findByAccount(Account account);
    
}
