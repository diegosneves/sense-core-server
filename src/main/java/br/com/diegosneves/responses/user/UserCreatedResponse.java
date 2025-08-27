package br.com.diegosneves.responses.user;

import br.com.diegosneves.dto.UserEntityDTO;
import br.com.diegosneves.enums.UserProfile;

import java.time.Instant;

public record UserCreatedResponse(
        String id,
        String name,
        String email,
        String phone,
        UserProfile profile,
        String username,
        String permissions,
        Instant createdAt,
        Instant updatedAt
) {


    public static UserCreatedResponse of(UserEntityDTO aDto) {
        return new UserCreatedResponse(
                aDto.id(),
                aDto.name(),
                aDto.email(),
                aDto.phone(),
                aDto.profile(),
                aDto.username(),
                aDto.profile().getDescription(),
                aDto.createdAt(),
                aDto.updatedAt()
        );
    }

}
