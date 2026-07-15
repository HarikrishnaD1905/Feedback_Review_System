package com.examly.springapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.examly.springapp.model.Admin;
import com.examly.springapp.model.Category;
import com.examly.springapp.repository.AdminRepository;
import com.examly.springapp.repository.CategoryRepository;

/**
 * Seeds the database with a default admin account and default categories on first startup.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Seed default admin if none exists
        if (!adminRepository.existsByUsername("admin")) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("Admin@123"));
            admin.setEmail("admin@feedbacksystem.com");
            adminRepository.save(admin);
            logger.info("Default admin account created: username=admin, password=Admin@123");
        } else {
            logger.info("Admin account already exists, skipping seed.");
        }

        // Seed default categories per FR6.1
        String[] defaultCategories = {"Bug", "Feature", "Usability", "General"};
        for (String catName : defaultCategories) {
            if (!categoryRepository.existsByName(catName)) {
                categoryRepository.save(new Category(catName));
                logger.info("Default category created: {}", catName);
            }
        }
    }
}
