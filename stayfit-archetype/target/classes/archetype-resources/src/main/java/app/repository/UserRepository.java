package ${package}.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ${package}.app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
