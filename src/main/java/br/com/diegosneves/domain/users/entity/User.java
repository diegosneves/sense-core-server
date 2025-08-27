package br.com.diegosneves.domain.users.entity;

import br.com.diegosneves.domain.validations.UserValidator;
import br.com.diegosneves.domain.validations.handlers.Notification;
import br.com.diegosneves.enums.UserProfile;
import br.com.diegosneves.exceptions.UserConstraintsException;

public class User implements UserContract{

    private String name;
    private String email;
    private String phone;
    private UserProfile profile;
    private String username;
    private Boolean enabled;

    public User(
            String name,
            String username,
            String email,
            String phone,
            UserProfile profile,
            Boolean enabled
    ) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.profile = profile;
        this.enabled = Boolean.TRUE.equals(enabled);
        this.validate();
    }

    @Override
    public void validate() throws UserConstraintsException {
        final var notification = Notification.create();
        final var validator = UserValidator.create(notification, this);
        validator.validate();
        if (notification.hasErrors()) {
            throw UserConstraintsException.with(notification.getErrors());
        }
    }

    @Override
    public void enable() throws UserConstraintsException {
        this.enabled = Boolean.TRUE;
    }

    @Override
    public void disable() throws UserConstraintsException {
        this.enabled = Boolean.FALSE;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPhone() {
        return this.phone;
    }

    @Override
    public UserProfile getProfile() {
        return this.profile;
    }

    @Override
    public Boolean isEnabled() {
        return Boolean.TRUE.equals(this.enabled);
    }
}
