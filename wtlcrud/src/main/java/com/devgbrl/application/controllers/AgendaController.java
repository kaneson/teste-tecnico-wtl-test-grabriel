package com.devgbrl.application.controllers;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devgbrl.domain.exceptions.BadArgumentException;
import com.devgbrl.domain.exceptions.NotAuthorizedException;
import com.devgbrl.domain.models.dtos.AgendaEventEditDTO;
import com.devgbrl.domain.models.entities.Agenda;
import com.devgbrl.domain.models.entities.AgendaEvent;
import com.devgbrl.domain.services.AgendaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @GetMapping("{id}")
    public String viewAgendaEvent(@PathVariable("id") Long id, Model model) {
        try {
            AgendaEvent agendaEvent = agendaService.getMyAgendaEventById(id);

            model.addAttribute("event", agendaEvent);

            return "agenda/view";
        } catch (BadArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "agenda/view";
        } catch (NotAuthorizedException ex) {
            return "redirect:/auth/sign-in";
        } catch (Exception ex) {
            System.err.println(ex);
            model.addAttribute("error", "Algo realmente grave aconteceu...");
            return "redirect:/error";
        }
    }


    @GetMapping("add")
    public String addAgendaEvent(Model model) {
        try {
            model.addAttribute("eventEdit", new AgendaEventEditDTO());

            return "agenda/add";
        } catch (BadArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "agenda/add";
        } catch (NotAuthorizedException ex) {
            return "redirect:/auth/sign-in";
        } catch (Exception ex) {
            System.err.println(ex);
            model.addAttribute("error", "Algo realmente grave aconteceu...");
            return "redirect:/error";
        }
    }

    @PostMapping("add")
    public String addAgendaEventSave(@ModelAttribute("eventEdit") AgendaEventEditDTO eventEdit,  Model model) {
        try {
            agendaService.addEvent(eventEdit.toEntity(null));

            return "redirect:/agenda";
        } catch (BadArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "agenda/add";
        } catch (NotAuthorizedException ex) {
            return "redirect:/auth/sign-in";
        } catch (Exception ex) {
            System.err.println(ex);
            model.addAttribute("error", "Algo realmente grave aconteceu...");
            return "redirect:/error";
        }
    }

    @GetMapping("edit/{id}")
    public String editAgendaEvent(@PathVariable("id") Long id, Model model) {
        try {
            AgendaEvent agendaEvent = agendaService.getMyAgendaEventById(id);

            model.addAttribute("eventEdit", AgendaEventEditDTO.fromEntity(agendaEvent));

            return "agenda/edit";
        } catch (BadArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "agenda/edit";
        } catch (NotAuthorizedException ex) {
            return "redirect:/auth/sign-in";
        } catch (Exception ex) {
            System.err.println(ex);
            model.addAttribute("error", "Algo realmente grave aconteceu...");
            return "redirect:/error";
        }
    }

    @PutMapping("edit/{id}")
    public String editAgendaEventSave(@PathVariable("id") Long id, @ModelAttribute("eventEdit") AgendaEventEditDTO eventEdit,  Model model) {
        try {
            AgendaEvent agendaEvent = agendaService.updateEvent(id, eventEdit);

            model.addAttribute("eventEdit", AgendaEventEditDTO.fromEntity(agendaEvent));

            return "agenda/edit";
        } catch (BadArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "agenda/edit";
        } catch (NotAuthorizedException ex) {
            return "redirect:/auth/sign-in";
        } catch (Exception ex) {
            System.err.println(ex);
            model.addAttribute("error", "Algo realmente grave aconteceu...");
            return "redirect:/error";
        }
    }

    @GetMapping("")
    public String listAgendaEvents(Model model) {
        try {
            var events = agendaService.getMyAgendaEvents();

            model.addAttribute("events", events);

            return "agenda/list";
        } catch (BadArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "agenda/list";
        } catch (NotAuthorizedException ex) {
            return "redirect:/auth/sign-in";
        } catch (Exception ex) {
            System.err.println(ex);
            model.addAttribute("error", "Algo realmente grave aconteceu...");
            return "redirect:/error";
        }
    }

    @GetMapping("delete/{id}")
    public String deleteAgendaEvent(@PathVariable("id") Long id, Model model) {
        try {
            agendaService.deleteEventById(id);

            return "redirect:/agenda";
        } catch (BadArgumentException ex) {
            return "redirect:/agenda";
        } catch (NotAuthorizedException ex) {
            return "redirect:/auth/sign-in";
        } catch (Exception ex) {
            System.err.println(ex);
            model.addAttribute("error", "Algo realmente grave aconteceu...");
            return "redirect:/error";
        }
    }

}
