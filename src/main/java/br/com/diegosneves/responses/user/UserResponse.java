package br.com.diegosneves.responses.user;

import br.com.diegosneves.enums.UserProfile;

public record UserResponse(
        String name,
        String email,
        String phone,
        UserProfile profile,
        String username,
        String permissions
) {

    public static UserResponse of(
            final String name,
            final String email,
            final String phone,
            final UserProfile profile,
            final String username
    ) {
        return new UserResponse(name, email, phone, profile, username, profile.getDescription());
    }

}
