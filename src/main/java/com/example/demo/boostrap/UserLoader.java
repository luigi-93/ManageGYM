package com.example.demo.boostrap;


import com.example.demo.model.security.Authority;
import com.example.demo.model.security.Role;
import com.example.demo.model.security.User;
import com.example.demo.repository.security.AuthorityRepository;
import com.example.demo.repository.security.RoleRepository;
import com.example.demo.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.control.MappingControl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {
            loadUserOnDb();
        }
    }

    private void loadUserOnDb() {

        Authority createUser = authorityRepository.save(Authority.builder().permission("user.create").build());
        Authority updateUser = authorityRepository.save(Authority.builder().permission("user.update").build());
        Authority deleteUser = authorityRepository.save(Authority.builder().permission("user.delete").build());
        Authority readUser = authorityRepository.save(Authority.builder().permission("user.read").build());

        Authority createSubscription = authorityRepository.save(Authority.builder().permission("subscription.create").build());
        Authority updateSubscription = authorityRepository.save(Authority.builder().permission("subscription.update").build());
        Authority deleteSubscription = authorityRepository.save(Authority.builder().permission("subscription.delete").build());
        Authority readSubscription = authorityRepository.save(Authority.builder().permission("subscription.read").build());

        Authority createTrainer = authorityRepository.save(Authority.builder().permission("trainer.create").build());
        Authority updateTrainer = authorityRepository.save(Authority.builder().permission("trainer.update").build());
        Authority deleteTrainer = authorityRepository.save(Authority.builder().permission("trainer.delete").build());
        Authority readTrainer = authorityRepository.save(Authority.builder().permission("trainer.read").build());

        Authority createTrainingCard = authorityRepository.save(Authority.builder().permission("trainingCard.create").build());
        Authority updateTrainingCard = authorityRepository.save(Authority.builder().permission("trainingCard.update").build());
        Authority deleteTrainingCard = authorityRepository.save(Authority.builder().permission("trainingCard.delete").build());
        Authority readTrainingCard = authorityRepository.save(Authority.builder().permission("trainingCard.read").build());


        Role roleAdmin = roleRepository.save(Role.builder().name("ADMIN").build());
        Role roleCustomer = roleRepository.save(Role.builder().name("CUSTOMER").build());

        roleAdmin.setAuthorities(Set.of(createUser, updateUser, deleteUser, readUser,
                                        createSubscription, updateSubscription, deleteSubscription, readSubscription,
                                        createTrainer, updateTrainer, deleteTrainer, readTrainer,
                                        createTrainingCard, updateTrainingCard, deleteTrainingCard, readTrainingCard
        ));

        roleCustomer.setAuthorities(Set.of( readUser,
                                            createSubscription, updateSubscription, deleteSubscription, readSubscription));


        roleRepository.saveAll(Set.of(roleAdmin,roleCustomer));

        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(roleAdmin)
                .build();

        userRepository.save(admin);

        User customer = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .role(roleCustomer)
                .build();


        userRepository.save(customer);


        log.debug("Users created");


    }




}
