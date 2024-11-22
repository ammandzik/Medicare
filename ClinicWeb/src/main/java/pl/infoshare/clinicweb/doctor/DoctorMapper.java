package pl.infoshare.clinicweb.doctor;

import lombok.Builder;
import org.springframework.stereotype.Component;
import pl.infoshare.clinicweb.user.PersonDetails;


@Component
@Builder
public class DoctorMapper {

    public DoctorDto toDto(Doctor doctor) {
//
//        DoctorDto doctorDto = new DoctorDto();
//        doctorDto.setId(doctor.getId());
//        doctorDto.setName(doctor.getDetails().getName());
//        doctorDto.setSurname(doctor.getDetails().getSurname());
//        doctorDto.setSpecialization(doctor.getSpecialization());
        return DoctorDto.builder()
                .name(doctor.getDetails().getName())
                .id(doctor.getId())
                .surname(doctor.getDetails().getSurname())
                .specialization(doctor.getSpecialization())
                .build();

    }


    public Doctor toEntity(DoctorDto doctorDto) {
        return Doctor.builder()
                .id(doctorDto.getId())
                .details(PersonDetails.builder()
                        .name(doctorDto.getName())
                        .surname(doctorDto.getSurname())
                        .build())
                .specialization(doctorDto.getSpecialization())
                .build();
    }
}
