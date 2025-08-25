package br.com.diegosneves.domain.users.entity;

import br.com.diegosneves.enums.UserProfile;
import br.com.diegosneves.exceptions.UserConstraintsException;

public interface UserContract {

    void validate() throws UserConstraintsException;
    void enable() throws UserConstraintsException;
    void disable() throws UserConstraintsException;
    Boolean isEnabled();

    String getName();
    String getUsername();
    String getEmail();
    String getPhone();
    UserProfile getProfile();

}
