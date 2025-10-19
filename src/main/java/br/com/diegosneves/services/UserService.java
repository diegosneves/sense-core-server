package br.com.diegosneves.services;

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

	public Pagination<UserEntityDTO> fetchAllUsers(
		final String search,
		final int page,
		final int perPage,
		final String sort,
		final String direction
	) {
		var users = this.userRepository.findAll().stream();

		// Aplicar filtro de busca
		if (search != null && !search.isBlank()) {
			final var searchLower = search.toLowerCase();
			users = users.filter(user ->
				user.name.toLowerCase().contains(searchLower) ||
					user.username.toLowerCase().contains(searchLower) ||
					user.email.toLowerCase().contains(searchLower) ||
					(user.phone != null && user.phone.toLowerCase().contains(searchLower))
			);
		}

		// Aplicar ordenação
		if (sort != null && !sort.isBlank()) {
			final var isDescending = "desc".equalsIgnoreCase(direction);
			users = users.sorted((u1, u2) -> {
				int comparison = switch (sort.toLowerCase()) {
					case "name" -> u1.name.compareToIgnoreCase(u2.name);
					case "username" -> u1.username.compareToIgnoreCase(u2.username);
					case "email" -> u1.email.compareToIgnoreCase(u2.email);
					case "phone" -> {
						String phone1 = u1.phone != null ? u1.phone : "";
						String phone2 = u2.phone != null ? u2.phone : "";
						yield phone1.compareToIgnoreCase(phone2);
					}
					case "profile" -> u1.profile.compareTo(u2.profile);
					case "enabled" -> Boolean.compare(u1.enabled, u2.enabled);
					default -> 0;
				};
				return isDescending ? -comparison : comparison;
			});
		}

		// Contar o total após aplicar os filtros
		final var filteredUsers = users.toList();
		final long totalCount = filteredUsers.size();

		// Aplicar paginação
		final var paginatedUsers = filteredUsers.stream()
			.skip((long) page * perPage)
			.limit(perPage)
			.map(UserEntity::toDTO)
			.toList();

		return new Pagination<>(page, perPage, totalCount, paginatedUsers);
	}

}
