package br.com.diegosneves.requests.user;

import br.com.diegosneves.enums.UserProfile;

public record UserCreateRequest(
        String name,
        String email,
        String phone,
        UserProfile profile,
        String username
) {

    public static UserCreateRequest of(
            final String name,
            final String email,
            final String phone,
            final UserProfile profile,
            final String username
    ) {
        return new UserCreateRequest(name, email, phone, profile, username);
    }

}
