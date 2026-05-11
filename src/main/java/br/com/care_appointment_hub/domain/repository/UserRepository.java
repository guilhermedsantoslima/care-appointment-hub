package br.com.care_appointment_hub.domain.repository;

import br.com.care_appointment_hub.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
