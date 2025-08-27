package br.com.diegosneves.dto;

import br.com.diegosneves.enums.UserProfile;
import br.com.diegosneves.modal.UserEntity;

import java.time.Instant;

public record UserEntityDTO(
        String id,
        String name,
        String email,
        String phone,
        UserProfile profile,
        String username,
        Boolean enabled,
        Instant createdAt,
        Instant updatedAt
) {
    public static UserEntityDTO from(final UserEntity anEntity) {
        return new UserEntityDTO(
                anEntity.id,
                anEntity.name,
                anEntity.email,
                anEntity.phone,
                anEntity.profile,
                anEntity.username,
                anEntity.enabled,
                anEntity.createdAt,
                anEntity.updatedAt
        );
    }
}
