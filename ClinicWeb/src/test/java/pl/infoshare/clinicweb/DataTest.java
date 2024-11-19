package pl.infoshare.clinicweb;

import pl.infoshare.clinicweb.user.entity.Role;
import pl.infoshare.clinicweb.user.entity.User;

 public class DataTest {
    public static User createUser(){

        return User.builder()
                .id(1L)
                .email("marek.kozlowski@wp.pl")
                .password("Puszek423")
                .role(Role.PATIENT)
                .build();

    }
}
