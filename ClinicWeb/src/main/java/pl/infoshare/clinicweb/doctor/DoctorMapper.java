package pl.infoshare.clinicweb.doctor;

import org.springframework.stereotype.Component;
import pl.infoshare.clinicweb.user.entity.PersonDetails;


@Component
public class DoctorMapper {

    public DoctorDto toDto(Doctor doctor) {

        DoctorDto doctorDto = new DoctorDto();

        doctorDto.setId(doctor.getId());
        doctorDto.setName(doctor.getDetails().getName());
        doctorDto.setSurname(doctor.getDetails().getSurname());
        doctorDto.setSpecialization(doctor.getSpecialization());
        return doctorDto;

    }


    public Doctor toEntity(DoctorDto doctorDto) {

        Doctor doctor = new Doctor();

        doctor.setId(doctorDto.getId());
        doctor.setDetails(new PersonDetails());
        doctor.getDetails().setName(doctorDto.getName());
        doctor.getDetails().setSurname(doctorDto.getSurname());
        doctor.setSpecialization(doctorDto.getSpecialization());
        return doctor;

    }
}
