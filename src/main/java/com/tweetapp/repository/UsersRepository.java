package com.tweetapp.repository;

import com.tweetapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Repository
public interface UsersRepository extends JpaRepository<User,Long> {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findByUsernameContains(String username);

    List<User> findByUsernameIn(List<String> usernames);

}
