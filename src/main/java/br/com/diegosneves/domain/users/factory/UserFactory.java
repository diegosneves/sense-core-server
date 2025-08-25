package br.com.diegosneves.domain.users.factory;

import br.com.diegosneves.domain.users.entity.User;
import br.com.diegosneves.enums.UserProfile;

public final class UserFactory {

    private UserFactory() {
    }

    public static User create(
            String name,
            String username,
            String email,
            String phone,
            UserProfile profile
    ) {
        return new User(
                name,
                username,
                email,
                phone,
                profile,
                true
        );
    }

}
