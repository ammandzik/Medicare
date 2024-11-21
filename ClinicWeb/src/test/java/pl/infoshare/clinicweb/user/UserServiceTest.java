package pl.infoshare.clinicweb.user;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pl.infoshare.clinicweb.DataTest;
import pl.infoshare.clinicweb.user.repository.UserRepository;
import pl.infoshare.clinicweb.user.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    UserService service;
    @Mock
    UserRepository userRepository;

    @Test
    public void testUserCreation() {

        var user = DataTest.createUser();

        Assertions.assertAll(() -> service.addUser(user));
    }

    @Test
    public void testFindUserByEmail() {

        var user = DataTest.createUser();

        when(userRepository.findUserByEmail("marek.kozlowski@wp.pl"))
                .thenReturn(Optional.ofNullable(user));

        var savedUser = service.findUserWithEmail(user.getEmail()).get();

        assertThat(savedUser).isNotNull();
    }

    @Test
    public void testSuccessUserRemovalById() {

        var user = DataTest.createUser();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        Assertions.assertAll(() -> service.deleteUserById(1L));


    }


}
