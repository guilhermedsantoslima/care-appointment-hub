package br.com.care_appointment_hub.infra.persistence.user.adapter;

import br.com.care_appointment_hub.domain.model.User;
import br.com.care_appointment_hub.domain.repository.UserRepository;
import br.com.care_appointment_hub.infra.persistence.user.entity.UserEntity;
import br.com.care_appointment_hub.infra.persistence.user.mapper.UserMapper;
import br.com.care_appointment_hub.infra.persistence.user.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;
    private final UserMapper mapper;

    public UserRepositoryImpl(UserJpaRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity userSaved = repository.save(entity);

        return mapper.toDomain(userSaved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
