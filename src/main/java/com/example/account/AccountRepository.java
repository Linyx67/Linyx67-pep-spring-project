package com.example.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    @Modifying
    @Query(
        value = "INSERT INTO account (username, password) VALUES (:usernameVar, :passwordVar)",
        nativeQuery = true
    )
    void registerAccount (@Param("usernameVar") String username, @Param("passwordVar") String password);
 
    @Query(
        value = "SELECT * FROM account WHERE username = :usernameVar AND password = :passwordVar",
        nativeQuery = true 
    )
    Optional<Account> login(@Param("usernameVar") String username, @Param("passwordVar") String password);

    Optional<Account> findByUsername(String username);

    Optional<Account> findById(int account_id);
     
}
