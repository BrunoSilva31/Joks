package com.WorkJoks.Joks.services;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WorkJoks.Joks.entities.User;
import com.WorkJoks.Joks.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;
    
    private static final int CAPACIDADE_MEZANINO = 40;
    private static final int CAPACIDADE_VARANDA = 35;
    
    public User insert(User obj) {
        validarHorario(obj.getFormattedDate());
        validarCapacidade(obj.getFormattedDate(), obj.getAmbiente());
        
        LocalDateTime horario = LocalDateTime.parse(obj.getFormattedDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        
        if(horario.isBefore(LocalDateTime.now())) {
        	throw new IllegalArgumentException("A data de agendamento passou");
        }
        
        repository.deleteByDateBefore(LocalDateTime.now());
        
        return repository.save(obj);
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public User update(Long id, User obj) {
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));
        validarHorario(obj.getFormattedDate());
        validarCapacidade(obj.getFormattedDate(), obj.getAmbiente());
        BeanUtils.copyProperties(obj, existingUser, "id");
        return repository.save(existingUser);
    }
    
    private void validarHorario(String date) {
        LocalDateTime horario = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        DayOfWeek diaSemana = horario.getDayOfWeek();
        int hora = horario.getHour();
        
        List<DayOfWeek> diasDisponiveis = Arrays.asList(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        
        if (!diasDisponiveis.contains(diaSemana)) {
            throw new IllegalArgumentException("Dia não disponível para agendamento.");
        }
        
        if ((diaSemana == DayOfWeek.FRIDAY && hora >= 19) || (diaSemana == DayOfWeek.SATURDAY && hora >= 15) || (diaSemana == DayOfWeek.SUNDAY && hora >= 15) || (hora < 8 || hora >= 23)) {
            throw new IllegalArgumentException("Horário não disponível para agendamento.");
        }
        
        if (horario.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de agendamento passou.");
        }
    }
    
    private void validarCapacidade(String horarioAgendamento, String ambiente) {
        LocalDateTime horario = LocalDateTime.parse(horarioAgendamento, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        int hora = horario.getHour();
        long totalClientesAmbiente;
        
        if ("mezanino".equalsIgnoreCase(ambiente)) {
            if ((hora >= 19 && hora <= 23) || (hora >= 0 && hora <= 1)) {
                if (hora == 0) {
                    horario = horario.plusDays(1);
                }
                totalClientesAmbiente = repository.countByDateAndAmbiente(horario, "mezanino");
                if (totalClientesAmbiente >= CAPACIDADE_MEZANINO) {
                    throw new IllegalArgumentException("Capacidade do mezanino excedida para esse horário.");
                }
            } else {
                throw new IllegalArgumentException("Horário indisponível para agendamento no mezanino.");
            }
        } 
        else if ("varanda".equalsIgnoreCase(ambiente)) {
            if ((hora >= 15 && hora <= 18) || (hora >= 19 && hora <= 23)) {
                totalClientesAmbiente = repository.countByDateAndAmbiente(horario, "varanda");
                if (totalClientesAmbiente >= CAPACIDADE_VARANDA) {
                    throw new IllegalArgumentException("Capacidade da varanda excedida para esse horário.");
                }
            } else {
                throw new IllegalArgumentException("Horário indisponível para agendamento na varanda.");
            }
        } else {
            throw new IllegalArgumentException("Ambiente inválido.");
        }
    }
    
    public List<User> findAll(){
        return repository.findAll();
    }
    
    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.get();
    }
}