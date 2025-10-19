package br.com.diegosneves.services;

import br.com.diegosneves.domain.pagination.PageControl;
import br.com.diegosneves.domain.pagination.Pagination;
import br.com.diegosneves.domain.users.factory.UserFactory;
import br.com.diegosneves.dto.UserEntityDTO;
import br.com.diegosneves.exceptions.ErrorData;
import br.com.diegosneves.exceptions.NotFoundException;
import br.com.diegosneves.exceptions.UserConstraintsException;
import br.com.diegosneves.modal.UserEntity;
import br.com.diegosneves.repositories.UserRepository;
import br.com.diegosneves.requests.user.UserCreateRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class UserService {

	private final UserRepository userRepository;

	@Inject
	public UserService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserEntityDTO createUser(final UserCreateRequest request) {
		if (request == null) {
			throw UserConstraintsException.with(ErrorData.of("request", "Request cannot be null"));
		}

		final var aNewUser = UserFactory.create(
			request.name(),
			request.username(),
			request.email(),
			request.phone(),
			request.profile()
		);
		final var savedUser = UserEntity.create(aNewUser);
		final var createdUser = this.userRepository.save(savedUser);
		return createdUser.toDTO();
	}


	public UserEntityDTO fetchUser(final String userId) {
		if (userId == null) {
			throw UserConstraintsException.with(ErrorData.of("userId", "User ID cannot be null"));
		}
		final var fetchUser = this.userRepository.findByIdString(userId)
			.orElseThrow(() -> NotFoundException.with(ErrorData.of("userId", "User not found")));
		return fetchUser.toDTO();
	}

	public Pagination<UserEntityDTO> fetchAllUsers(final PageControl pageControl) {
		// Contar o total
		final var filteredUsers = this.userRepository.findAllByPageControl(pageControl);
		final long totalCount = filteredUsers.size();

		// Aplicar paginação
		final var paginatedUsers = filteredUsers.stream()
			.skip((long) pageControl.page() * pageControl.perPage())
			.limit(pageControl.perPage())
			.map(UserEntity::toDTO)
			.toList();

		return new Pagination<>(pageControl.page(), pageControl.perPage(), totalCount, paginatedUsers);
	}

}
