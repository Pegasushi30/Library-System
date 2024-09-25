package com.project.library;



import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.library.model.Role;
import com.project.library.model.User;
import com.project.library.repository.RoleRepository;
import com.project.library.service.UserService;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {


	private final RoleRepository roleRepository;
	private final UserService userService;

	@Autowired
	public LibraryApplication(RoleRepository roleRepository, UserService userService) {
		this.roleRepository = roleRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Role adminRole = new Role();
		adminRole.setRoleName("ADMIN");
		roleRepository.save(adminRole);

		Role userRole = new Role();
		userRole.setRoleName("USER");
		roleRepository.save(userRole);

		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword("adminpassword");
		admin.setFirstname("Admin");
		admin.setLastname("User");
		admin.setEmail("admin@example.com");
		admin.setPhoneNumber("1234567890");
		admin.setBirthdate("1990-01-01");
		admin.setRole(adminRole);
		userService.saveUser(admin);

		User user = new User();
		user.setUsername("user");
		user.setPassword("userpassword");
		user.setFirstname("Regular");
		user.setLastname("User");
		user.setEmail("user@example.com");
		user.setPhoneNumber("0987654321");
		user.setBirthdate("1995-01-01");
		user.setRole(userRole);
		userService.saveUser(user);
	}
}


