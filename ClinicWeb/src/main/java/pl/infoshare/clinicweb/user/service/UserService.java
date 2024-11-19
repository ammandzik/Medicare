package pl.infoshare.clinicweb.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.infoshare.clinicweb.user.entity.Role;
import pl.infoshare.clinicweb.user.entity.User;
import pl.infoshare.clinicweb.user.mapper.UserMapper;
import pl.infoshare.clinicweb.user.registration.UserDto;
import pl.infoshare.clinicweb.user.repository.UserRepository;

import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var user = findUserWithEmail(email).get();

        if (!user.equals(null)) {

            log.info("User was found with email: {}", email);

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(String.valueOf(user.getRole()))
                    .build();

        } else {

            throw new UsernameNotFoundException(String.format("User not found with email %s", email));
        }

    }

    public void addUser(User user) {

        userRepository.save(user);
    }

    public void saveUser(UserDto user) {

        user.setRole(Role.PATIENT);
        var appUser = userMapper.toEntity(user);
        userRepository.save(appUser);
        log.info("User patient saved with ID: {}", appUser.getId());

    }

    public void deleteUserById(Long id) {

        userRepository.deleteById(id);
    }

    public Optional<User> findUserWithEmail(String email) {

        return userRepository.findUserByEmail(email);

    }

    public Optional<User> findUserWithId(Long id) {

        return userRepository.findById(id);
    }

    public boolean isUserAlreadyRegistered(String email) {

        Optional<User> user = userRepository.findUserByEmail(email);

        return user.isPresent();

    }


}
