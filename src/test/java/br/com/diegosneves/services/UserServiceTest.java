package br.com.diegosneves.services;

import br.com.diegosneves.domain.users.entity.User;
import br.com.diegosneves.domain.users.factory.UserFactory;
import br.com.diegosneves.enums.UserProfile;
import br.com.diegosneves.modal.UserEntity;
import br.com.diegosneves.repositories.UserRepository;
import br.com.diegosneves.requests.user.UserCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(this.repository);
    }

    @Test
    void givenAnUserValid_whenCreateAnUser_thenReturnUserEntityDTO() {
        final var expectedName = "Diego";
        final var expectedUserName = "Sneves";
        final var expectedEmail = "diegosneves@gmail.com";
        final var expectedPhone = "123456";
        final var expectedProfile = UserProfile.ADMIN;
        final var expectedId = "123";

        final var user = UserFactory.create(expectedName, expectedUserName, expectedEmail, expectedPhone, UserProfile.ADMIN);
        final var entity = UserEntity.create(user);
        entity.id = expectedId;
        final var request = UserCreateRequest.of(expectedName, expectedEmail, expectedPhone, UserProfile.ADMIN, expectedUserName);

        when(this.repository.save(any(UserEntity.class))).thenReturn(entity);

        final var actual = this.service.createUser(request);

        verify(this.repository, times(1)).save(
                argThat(newUser -> Objects.equals(expectedName, newUser.name) &&
                        Objects.equals(expectedUserName, newUser.username) &&
                        Objects.equals(expectedEmail, newUser.email) &&
                        Objects.equals(expectedPhone, newUser.phone) &&
                        Objects.equals(expectedProfile, newUser.profile) &&
                        Objects.equals(Boolean.TRUE, newUser.enabled) &&
                        Objects.nonNull(newUser.createdAt) &&
                        Objects.nonNull(newUser.updatedAt)
                )
        );

        assertNotNull(actual);
        assertEquals(expectedId, actual.id(), "User id is not the same");
        assertEquals(expectedName, actual.name(), "User name is not the same");
        assertEquals(expectedUserName, actual.username(), "User username is not the same");
        assertEquals(expectedEmail, actual.email(), "User email is not the same");
        assertEquals(expectedPhone, actual.phone(), "User phone is not the same");
        assertEquals(expectedProfile, actual.profile(), "User profile is not the same");
        assertTrue(actual.enabled(), "User is not enabled");
        assertNotNull(actual.createdAt(), "User created at is null");
        assertNotNull(actual.updatedAt(), "User updated at is null");
    }


}
