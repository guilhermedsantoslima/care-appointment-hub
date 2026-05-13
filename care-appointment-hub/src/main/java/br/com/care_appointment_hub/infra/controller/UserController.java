package br.com.care_appointment_hub.infra.controller;

import br.com.care_appointment_hub.application.dto.UserResponseDTO;
import br.com.care_appointment_hub.application.usecases.user.*;
import br.com.care_appointment_hub.domain.enums.Role;
import br.com.care_appointment_hub.infra.dto.CreateUserRequestDTO;
import br.com.care_appointment_hub.infra.dto.UpdateUserRequestDTO;
import br.com.care_appointment_hub.infra.persistence.mapper.UserRequestMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final FindAllUserUseCase findAllUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    private final UserRequestMapper mapper;

    public UserController(CreateUserUseCase createUserUseCase, FindAllUserUseCase findAllUserUseCase, FindUserByIdUseCase findUserByIdUseCase,
                          UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase, UserRequestMapper mapper) {
        this.createUserUseCase = createUserUseCase;
        this.findAllUserUseCase = findAllUserUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.mapper = mapper;
    }


    @PostMapping("/doctor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> createDoctor(@RequestBody @Valid CreateUserRequestDTO request){
        var userCommand = mapper.toCommand(request);
        userCommand = new br.com.care_appointment_hub.application.dto.CreateUserCommand(
                userCommand.name(),
                userCommand.email(),
                userCommand.password(),
                Role.DOCTOR
        );

        return ResponseEntity.ok(createUserUseCase.execute(userCommand));
    }

    @PostMapping("/nurse")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<UserResponseDTO> createNurse(@RequestBody @Valid CreateUserRequestDTO request){
        var userCommand = mapper.toCommand(request);
        userCommand = new br.com.care_appointment_hub.application.dto.CreateUserCommand(
                userCommand.name(),
                userCommand.email(),
                userCommand.password(),
                Role.NURSE
        );

        return ResponseEntity.ok(createUserUseCase.execute(userCommand));
    }

    @PostMapping("/patient")
    @PreAuthorize("hasAnyRole('DOCTOR','NURSE')")
    public ResponseEntity<UserResponseDTO> createPatient(@RequestBody @Valid CreateUserRequestDTO request) {
        var userCommand = mapper.toCommand(request);
        userCommand = new br.com.care_appointment_hub.application.dto.CreateUserCommand(
                userCommand.name(),
                userCommand.email(),
                userCommand.password(),
                Role.PATIENT
        );

        return ResponseEntity.ok(createUserUseCase.execute(userCommand));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        return ResponseEntity.ok(findAllUserUseCase.execute());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE')")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(findUserByIdUseCase.execute(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UpdateUserRequestDTO userRequest){
        var userCommand = mapper.toCommand(userRequest);

        return ResponseEntity.ok(updateUserUseCase.execute(id, userCommand));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
