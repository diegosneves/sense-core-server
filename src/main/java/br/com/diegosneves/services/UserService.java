package br.com.diegosneves.services;

import br.com.diegosneves.domain.users.factory.UserFactory;
import br.com.diegosneves.dto.UserEntityDTO;
import br.com.diegosneves.exceptions.ErrorData;
import br.com.diegosneves.exceptions.UserConstraintsException;
import br.com.diegosneves.modal.UserEntity;
import br.com.diegosneves.repositories.UserRepository;
import br.com.diegosneves.requests.user.UserCreateRequest;
import br.com.diegosneves.responses.user.UserCreatedResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntityDTO createUser(final UserCreateRequest request) {
        if (request == null) {
            throw UserConstraintsException.with(ErrorData.of("request", "Request is null"));
        }

        final var aNewUser = UserFactory.create(
                request.name(),
                request.username(),
                request.email(),
                request.phone(),
                request.profile()
                );
        final var savedUser = UserEntity.create(aNewUser);
        final var createdUser = userRepository.save(savedUser);
        return createdUser.toDTO();
    }


    public UserEntityDTO fetchUser(final String userId) {
        if (userId == null) {
            throw UserConstraintsException.with(ErrorData.of("userId", "User ID is null"));
        }
        final var fetchUser = this.userRepository.findByIdString(userId)
                .orElseThrow(() -> UserConstraintsException.with(ErrorData.of("userId", "User not found")));
        return fetchUser.toDTO();
    }
}
