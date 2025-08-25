package br.com.diegosneves.domain.validations;

import br.com.diegosneves.domain.users.entity.User;
import br.com.diegosneves.domain.validations.handlers.ValidationHandler;

public class UserValidator extends Validator {

    private final User user;

    private UserValidator(final ValidationHandler handler, final User user) {
        super(handler);
        this.user = user;
    }

    public static UserValidator create(final ValidationHandler handler, final User user) {
        return new UserValidator(handler, user);
    }

    @Override
    public void validate() {
        this.validateStringFieldIsNullOrBlank("Name", this.user.getName());
        this.validateStringFieldIsNullOrBlank("Email", this.user.getEmail());
        this.validateStringFieldIsNullOrBlank("Phone", this.user.getPhone());
        this.validateStringFieldIsNullOrBlank("Username", this.user.getUsername());
        this.checkUserProfileContraints();
        this.checkUserEnableFieldConstraints();
    }

    private void checkUserEnableFieldConstraints() {
        if (this.user.isEnabled() == null) {
            this.getHandler().append("Enabled", "User enabled field cannot be null");
        }
    }

    private void checkUserProfileContraints() {
        if (this.user.getProfile() == null) {
            this.getHandler().append("Profile", "Profile cannot be null");
        }
    }
}
