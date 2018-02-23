package pl.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import pl.java.model.User;
 
public interface UserRepository extends JpaRepository<User, Long> {
     
    User findByEmail(String email);
    
    @Modifying
    @Transactional
    @Query("UPDATE User SET email = ?1 WHERE email = ?2")
    public int changeEmail(String newEmail, String oldEmail);
}