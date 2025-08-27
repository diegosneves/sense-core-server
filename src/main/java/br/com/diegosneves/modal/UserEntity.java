package br.com.diegosneves.modal;

import br.com.diegosneves.domain.users.entity.UserContract;
import br.com.diegosneves.dto.UserEntityDTO;
import br.com.diegosneves.enums.UserProfile;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;
    public String name;
    public String email;
    public String phone;
    public UserProfile profile;
    public String username;
    public Boolean enabled;
    public Instant createdAt;
    public Instant updatedAt;

    public static UserEntity create(UserContract userContract) {
        final var userEntityInstance = new UserEntity();
        userEntityInstance.name = userContract.getName();
        userEntityInstance.email = userContract.getEmail();
        userEntityInstance.phone = userContract.getPhone();
        userEntityInstance.profile = userContract.getProfile();
        userEntityInstance.username = userContract.getUsername();
        userEntityInstance.enabled = userContract.isEnabled();
        userEntityInstance.createdAt = Instant.now();
        userEntityInstance.updatedAt = Instant.now();
        return userEntityInstance;
    }

    public UserEntityDTO toDTO() {
        return UserEntityDTO.from(this);
    }

}
