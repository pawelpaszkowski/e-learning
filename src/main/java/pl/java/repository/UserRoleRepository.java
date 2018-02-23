package pl.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.model.UserRole;


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(String role);
    
    UserRole findById(Long id);
}