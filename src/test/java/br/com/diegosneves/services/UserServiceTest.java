package br.com.diegosneves.services;

import br.com.diegosneves.domain.users.factory.UserFactory;
import br.com.diegosneves.enums.UserProfile;
import br.com.diegosneves.exceptions.NotFoundException;
import br.com.diegosneves.exceptions.UserConstraintsException;
import br.com.diegosneves.modal.UserEntity;
import br.com.diegosneves.repositories.UserRepository;
import br.com.diegosneves.requests.user.UserCreateRequest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
//@QuarkusTest
class UserServiceTest {

//    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @BeforeEach
    void cleanUp() {
        MockitoAnnotations.openMocks(this);
        this.service = new UserService(this.repository);
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

    @Test
    void givenAnUserNameInvalid_whenCreateAnUser_thenShouldThrowUserConstraintsException() {
        final var expectedName = "";
        final var expectedUserName = "Sneves";
        final var expectedEmail = "diegosneves@gmail.com";
        final var expectedPhone = "123456";
        final var expectedProfile = UserProfile.ADMIN;
        final var expectedErrorSize = 1;
        final var expectedErrorMessage = "Field 'Name' cannot be empty or null";

        final var request = UserCreateRequest.of(expectedName, expectedEmail, expectedPhone, expectedProfile, expectedUserName);

        final var actual = assertThrows(UserConstraintsException.class, () -> this.service.createUser(request));

        verify(this.repository, never()).save(any(UserEntity.class));

        assertNotNull(actual);
        assertInstanceOf(UserConstraintsException.class, actual);
        assertEquals(expectedErrorSize, actual.getErrors().size());
        assertEquals(expectedErrorMessage, actual.getErrors().getFirst().getMessage());
    }

    @Test
    void givenAnUserNameAndUserProfileInvalid_whenCreateAnUser_thenShouldThrowUserConstraintsException() {
        final var expectedName = "";
        final var expectedUserName = "Sneves";
        final var expectedEmail = "diegosneves@gmail.com";
        final var expectedPhone = "123456";
        final var expectedErrorSize = 2;
        final var expectedFirstErrorMessage = "Field 'Name' cannot be empty or null";
        final var expectedLastErrorMessage = "Profile cannot be null";

        final var request = UserCreateRequest.of(expectedName, expectedEmail, expectedPhone, null, expectedUserName);

        final var actual = assertThrows(UserConstraintsException.class, () -> this.service.createUser(request));

        verify(this.repository, never()).save(any(UserEntity.class));

        assertNotNull(actual);
        assertInstanceOf(UserConstraintsException.class, actual);
        assertEquals(expectedErrorSize, actual.getErrors().size());
        assertEquals(expectedFirstErrorMessage, actual.getErrors().getFirst().getMessage());
        assertEquals(expectedLastErrorMessage, actual.getErrors().getLast().getMessage());
    }

    @Test
    void givenANullUserCreateRequest_whenCreateAnUser_thenShouldThrowUserConstraintsException() {
        final var expectedErrorSize = 1;
        final var expectedErrorMessage = "Request cannot be null";

        final var actual = assertThrows(UserConstraintsException.class, () -> this.service.createUser(null));

        verify(this.repository, never()).save(any(UserEntity.class));

        assertNotNull(actual);
        assertInstanceOf(UserConstraintsException.class, actual);
        assertEquals(expectedErrorSize, actual.getErrors().size());
        assertEquals(expectedErrorMessage, actual.getErrors().getFirst().getMessage());
    }

    @Test
    void givenANullUserId_whenFetchUser_thenShouldThrowUserConstraintsException() {
        final var expectedErrorSize = 1;
        final var expectedErrorMessage = "User ID cannot be null";

        final var actual = assertThrows(UserConstraintsException.class, () -> this.service.fetchUser(null));

        verify(this.repository, never()).findByIdString(any(String.class));

        assertNotNull(actual);
        assertInstanceOf(UserConstraintsException.class, actual);
        assertEquals(expectedErrorSize, actual.getErrors().size());
        assertEquals(expectedErrorMessage, actual.getErrors().getFirst().getMessage());
    }

    @Test
    void givenAnUserIdNotRegistered_whenFetchUser_thenShouldThrowNotFoundException() {
        final var expectedErrorSize = 1;
        final var expectedErrorMessage = "User not found";
        final var expectedUserId = "123";
        when(this.repository.findByIdString(any(String.class))).thenReturn(Optional.empty());

        final var actual = assertThrows(NotFoundException.class, () -> this.service.fetchUser(expectedUserId));

        verify(this.repository, times(1)).findByIdString(any(String.class));

        assertNotNull(actual);
        assertInstanceOf(NotFoundException.class, actual);
        assertEquals(expectedErrorSize, actual.getErrors().size());
        assertEquals(expectedErrorMessage, actual.getErrors().getFirst().getMessage());
    }

    @Test
    void givenAnUserIdValid_whenFetchUser_thenReturnAnUserEntityDTO() {

        final var expectedName = "Diego";
        final var expectedUserName = "Sneves";
        final var expectedEmail = "diegosneves@gmail.com";
        final var expectedPhone = "123456";
        final var expectedProfile = UserProfile.ADMIN;
        final var expectedId = "123";

        final var user = UserFactory.create(expectedName, expectedUserName, expectedEmail, expectedPhone, expectedProfile);
        final var entity = UserEntity.create(user);
        entity.id = expectedId;

        when(this.repository.findByIdString(eq(expectedId))).thenReturn(Optional.of(entity));

        final var actual = this.service.fetchUser(expectedId);

        verify(this.repository, times(1)).findByIdString(eq(expectedId));

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
