package pl.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.java.model.User;
import pl.java.model.UserRole;
import pl.java.repository.UserRepository;
import pl.java.repository.UserRoleRepository;

@Service
public class UserService {

	private static final String DEFAULT_ROLE = "ROLE_USER";
	private static final String TEACHER_ROLE = "ROLE_TEACHER";
	private UserRepository userRepository;
	private UserRoleRepository roleRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setRoleRepository(UserRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public void addWithDefaultRole(User user) {
		UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
		user.getRoles().add(defaultRole);
		userRepository.save(user);
	}

	public void addWithTeacherRole(User user) {
		UserRole defaultRole = roleRepository.findByRole(TEACHER_ROLE);
		user.getRoles().add(defaultRole);
		userRepository.save(user);
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public int changeEmail(String newEmail, String oldEmail) {
		return userRepository.changeEmail(newEmail, oldEmail);
	}

	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	public void deleteUserByID(Long idUser) {
		userRepository.delete(idUser);
	}

	public UserRole findRoleById(Long id) {
		return roleRepository.findById(id);
	}

	public User findUserById(Long id) {
		return userRepository.getOne(id);

	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public boolean verifyMail(String email) {
		boolean isMailCorrect = false;
		int j = 0;

		// mail nie moze byc psuty
		if (email.length() == 0) {
			return isMailCorrect;
		}

		if (email.charAt(0) != '@' && email.charAt(0) != '.' && email.charAt(0) != '_' && email.charAt(0) != '-'
				&& email.charAt(0) > '9' || email.charAt(0) < '0') {
			for (int i = 1; i < email.length(); i++) {
				if (email.charAt(i) == '@') {
					j = i;
					break;
				}
			}

			if (j != 0) {
				for (j++; j < email.length(); j++) {

					if (!(email.charAt(j) >= 'a' && email.charAt(j) <= 'z'
							|| email.charAt(j) >= '0' && email.charAt(j) <= '9' || email.charAt(j) <= '.')) {
						isMailCorrect = false;
						break;
					}
					// cyfra po . np ktos@gmail.c2m
					if (isMailCorrect && email.charAt(j) >= '0' && email.charAt(j) <= '9') {
						isMailCorrect = false;
						break;
					}

					if (email.charAt(j - 2) != '.' && email.charAt(j - 1) == '.' && email.charAt(j) != '.') {
						if (!isMailCorrect)
							// cyfra zaraz po kropce
							if (email.charAt(j) >= '0' && email.charAt(j) <= '9') {
								isMailCorrect = false;
								break;
							} else
								isMailCorrect = true;
					}
				}
				if (isMailCorrect && email.charAt(email.length() - 1) == '.')
					isMailCorrect = false;
			}
		}

		return isMailCorrect;
	}

	public boolean verifyName(String name) {
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) >= 'a' && name.charAt(i) <= 'z' || name.charAt(i) >= 'A' && name.charAt(i) <= 'Z'
					|| name.charAt(i) == 'ł' || name.charAt(i) == 'ą' || name.charAt(i) == 'ę' || name.charAt(i) == 'ć'
					|| name.charAt(i) == 'Ł' || name.charAt(i) == 'Ć' || name.charAt(i) == 'ż' || name.charAt(i) == 'Ż'
					|| name.charAt(i) == 'ź' || name.charAt(i) == 'Ż' || name.charAt(i) == 'ń' || name.charAt(i) == 'ó'
					|| name.charAt(i) == 'Ó' || name.charAt(i) == 'ś' || name.charAt(i) == 'Ś') {

			} else {
				return false;
			}
		}
		return true;
	}

}