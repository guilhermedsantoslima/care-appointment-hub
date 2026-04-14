package br.com.care_appointment_hub.infra.controller;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.dto.CreateAppointmentRequestDTO;
import br.com.care_appointment_hub.application.dto.UpdateAppointmentRequestDTO;
import br.com.care_appointment_hub.application.usecases.*;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final CreateAppointmentUseCase createUseCase;
    private final UpdateAppointmentUseCase updateUseCase;
    private final FindAppointmentUseCase findUseCase;
    private final ListAppointmentsUseCase listUseCase;
    private final DeleteAppointmentUseCase deleteUseCase;

    public AppointmentController(CreateAppointmentUseCase createUseCase, UpdateAppointmentUseCase updateUseCase,
                                 FindAppointmentUseCase findUseCase, ListAppointmentsUseCase listUseCase,
                                 DeleteAppointmentUseCase deleteUseCase) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.findUseCase = findUseCase;
        this.listUseCase = listUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> create(@RequestBody @Valid CreateAppointmentRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createUseCase.execute(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> update(@PathVariable Long id,
                                                         @RequestBody @Valid UpdateAppointmentRequestDTO request)
            throws BusinessRuleException {
        return ResponseEntity.ok(updateUseCase.execute(id,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable Long id) throws BusinessRuleException {
        return ResponseEntity.ok(findUseCase.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> listAll(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(listUseCase.execute(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws BusinessRuleException {
        deleteUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}
