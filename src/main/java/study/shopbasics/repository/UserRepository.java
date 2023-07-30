package study.shopbasics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shopbasics.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
