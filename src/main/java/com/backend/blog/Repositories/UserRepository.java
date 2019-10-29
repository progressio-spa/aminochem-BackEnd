package com.backend.blog.Repositories;

import com.backend.blog.Entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User repository for CRUD operations.
 */
public interface UserRepository extends JpaRepository<User,Long> {
	User findByUsername(String username);
}
