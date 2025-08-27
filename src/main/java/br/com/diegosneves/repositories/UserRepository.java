package br.com.diegosneves.repositories;

import br.com.diegosneves.enums.UserProfile;
import br.com.diegosneves.modal.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    /**
     * Salva um usuário e retorna a entidade persistida
     */
    @Transactional
    public UserEntity save(UserEntity user) {
        persist(user);
        return user;
    }

    /**
     * Busca usuário por ID (String)
     */
    public Optional<UserEntity> findByIdString(String id) {
        return find("id", id).firstResultOptional();
    }


    /**
     * Atualiza um usuário
     */
    @Transactional
    public UserEntity update(UserEntity user) {
        return getEntityManager().merge(user);
    }

    /**
     * Remove um usuário
     */
    @Transactional
    public void remove(UserEntity user) {
        delete(user);
    }

    /**
     * Busca usuário por username
     */
    public Optional<UserEntity> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }

    /**
     * Busca usuário por email
     */
    public Optional<UserEntity> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    /**
     * Busca usuários por perfil
     */
    public List<UserEntity> findByProfile(UserProfile profile) {
        return find("profile", profile).list();
    }

    /**
     * Busca usuários ativos
     */
    public List<UserEntity> findEnabledUsers() {
        return find("enabled", true).list();
    }

    /**
     * Busca usuários inativos
     */
    public List<UserEntity> findDisabledUsers() {
        return find("enabled", false).list();
    }

    /**
     * Busca usuário por telefone
     */
    public Optional<UserEntity> findByPhone(String phone) {
        return find("phone", phone).firstResultOptional();
    }

    /**
     * Verifica se existe usuário com o username informado
     */
    public boolean existsByUsername(String username) {
        return count("username", username) > 0;
    }

    /**
     * Verifica se existe usuário com o email informado
     */
    public boolean existsByEmail(String email) {
        return count("email", email) > 0;
    }

    /**
     * Busca usuários por parte do nome (LIKE)
     */
    public List<UserEntity> findByNameContaining(String name) {
        return find("LOWER(name) LIKE LOWER(?1)", "%" + name + "%").list();
    }

    /**
     * Busca usuários ativos por perfil
     */
    public List<UserEntity> findEnabledUsersByProfile(UserProfile profile) {
        return find("enabled = true AND profile = ?1", profile).list();
    }


}
