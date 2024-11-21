package pl.infoshare.clinicweb.doctor;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.infoshare.clinicweb.patient.Address;
import pl.infoshare.clinicweb.user.PersonDetails;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public void addDoctor(Doctor user) {
        doctorRepository.save(user);
    }

    public DoctorDto findById(long id) {
        return doctorRepository
                .findById(id)
                .map(doctorMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Doctor not found with ID: %s", id)));

    }

    public List<DoctorDto> findAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<DoctorDto> findAllPage(int pageNumber) {

        final int pageSize = 10;
        Page<DoctorDto> doctors;

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id"));
        Page<Doctor> entities = doctorRepository.findAll(pageable);


        doctors = entities.map(doctor -> {
            DoctorDto doctorDto = doctorMapper.toDto(doctor);

            return doctorDto;
        });
        return doctors;

    }


    public void deleteDoctor(Long idDoctor) {

        doctorRepository.deleteById(idDoctor);
    }

    public void updateDoctor(DoctorDto doctorDto, Address address) {

        Doctor doctor = doctorMapper.toEntity(doctorDto);
        doctor.setAddress(address);

        doctorRepository.save(doctor);
    }

    public void findDoctorByKey(String name, String surname) {

    }


    Page<DoctorDto> findDoctorBySpecialization(int pageNumber, Specialization specialization) {

        final int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));

        List<DoctorDto> doctorDtos = doctorRepository.findAll()
                .stream()
                .filter(doctor -> doctor.getSpecialization().equals(specialization))
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());


        return new PageImpl<>(doctorDtos);
    }

    public void setDoctorAttributes(Doctor doctor, PersonDetails personDetails,
                                    Address address, Specialization specialization) {

        doctor.setDetails(personDetails);
        doctor.setAddress(address);
        doctor.setSpecialization(specialization);


    }

    public DoctorDto findByPesel(String pesel) {

        return doctorRepository.findByPesel(pesel.trim())
                .map(doctorMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Doctor not found with pesel %s", pesel)));
    }

    public boolean existsByPesel(String pesel) {

        return doctorRepository.findByPesel(pesel).isEmpty() ? false : true;
    }


}
