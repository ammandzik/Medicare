package pl.infoshare.clinicweb.visit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.infoshare.clinicweb.doctor.Doctor;
import pl.infoshare.clinicweb.patient.Patient;
import pl.infoshare.clinicweb.user.PersonDetails;


@Component
@AllArgsConstructor

public class VisitMapper {


    public VisitDto toVisitDto(Visit visit) {VisitDto visitDto = new VisitDto();

        visitDto.setId(visit.getId());
        visitDto.setVisitDate(visit.getVisitDate());
        visitDto.setVisitCancelled(visit.isCancelVisit());
        visitDto.setVisitPastDate(visit.isVisitPastDate());


        if (visit.getPatient() != null && visit.getPatient().getPersonDetails() != null) {
            visitDto.setPatientName(visit.getPatient().getPersonDetails().getName());
            visitDto.setPatientSurname(visit.getPatient().getPersonDetails().getSurname());
            visitDto.setPatientPhoneNumber(visit.getPatient().getPersonDetails().getPhoneNumber());
            visitDto.setPatientPesel(visit.getPatient().getPersonDetails().getPesel());
            visitDto.setPatientId(visit.getPatient().getId());
        }

        if (visit.getDoctor() != null && visit.getDoctor().getDetails() != null) {
            visitDto.setDoctorName(visit.getDoctor().getDetails().getName());
            visitDto.setDoctorSurname(visit.getDoctor().getDetails().getSurname());
            visitDto.setDoctorSpecialization(visit.getDoctor().getSpecialization());
            visitDto.setDoctorId(visit.getDoctor().getId());
        }

        return visitDto;

    }


    public Visit toEntity(VisitDto visitDto) {

        if (visitDto == null) {
            throw new IllegalArgumentException("VisitDto cannot be null");
        }



        Patient patient = new Patient();
        PersonDetails personDetails = new PersonDetails();
        personDetails.setName(visitDto.getPatientName());
        personDetails.setSurname(visitDto.getPatientSurname());
        personDetails.setPhoneNumber(visitDto.getPatientPhoneNumber());
        personDetails.setPesel(visitDto.getPatientPesel());
        patient.setPersonDetails(personDetails);
        patient.setId(visitDto.getPatientId());


        Doctor doctor = new Doctor();
        PersonDetails doctorDetails = new PersonDetails();
        doctorDetails.setName(visitDto.getDoctorName());
        doctorDetails.setSurname(visitDto.getDoctorSurname());
        doctor.setSpecialization(visitDto.getDoctorSpecialization());
        doctor.setId(visitDto.getDoctorId());
        doctor.setDetails(doctorDetails);


        Visit visit = new Visit();
        visit.setId(visitDto.getId());
        visit.setVisitDate(visitDto.getVisitDate());
        visit.setCancelVisit(visitDto.isVisitCancelled());

        visit.setPatient(patient);
        visit.setDoctor(doctor);


        return visit;
    }
}
