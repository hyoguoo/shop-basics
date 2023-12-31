package study.shopbasics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shopbasics.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
