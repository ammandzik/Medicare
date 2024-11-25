package pl.infoshare.clinicweb.patientCard;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.infoshare.clinicweb.doctor.Doctor;
import pl.infoshare.clinicweb.patient.Patient;
import pl.infoshare.clinicweb.user.entity.PersonDetails;


@Component
@AllArgsConstructor
public class PatientCardMapper {
    public PatientCardDTO toDto(PatientCard patientCard) {
        PatientCardDTO patientCardDTO = new PatientCardDTO();


        if (patientCard.getPatient() != null && patientCard.getPatient().getPersonDetails() != null) {
            patientCardDTO.setPatientPesel(patientCard.getPatient().getPersonDetails().getPesel());
            patientCardDTO.setPatientFirstName(patientCard.getPatient().getPersonDetails().getName());
            patientCardDTO.setPatientLastName(patientCard.getPatient().getPersonDetails().getSurname());
            patientCardDTO.setPatientId(patientCard.getPatient().getId());
        }


        if (patientCard.getDoctor() != null && patientCard.getDoctor().getDetails() != null) {
            patientCardDTO.setDoctorFirstName(patientCard.getDoctor().getDetails().getName());
            patientCardDTO.setDoctorLastName(patientCard.getDoctor().getDetails().getSurname());
            patientCardDTO.setDoctorId(patientCard.getDoctor().getId());
        }


        patientCardDTO.setSymptoms(patientCard.getSymptoms());
        patientCardDTO.setDateOfVisit(patientCard.getDateOfVisit());
        patientCardDTO.setNoteDoctor(patientCard.getNoteDoctor());
        patientCardDTO.setDiagnosis(patientCard.getDiagnosis());
        patientCardDTO.setTreatment(patientCard.getTreatment());

        return patientCardDTO;
    }


    public PatientCard toEntity(PatientCardDTO patientCardDTO) {
        PatientCard patientCard = new PatientCard();


        patientCard.setId(patientCardDTO.getId());


        Patient patient = new Patient();
        PersonDetails patientDetails = new PersonDetails();
        patient.setId(patientCardDTO.getPatientId());
        patientDetails.setName(patientCardDTO.getPatientFirstName());
        patientDetails.setSurname(patientCardDTO.getPatientLastName());
        patientDetails.setPesel(patientCardDTO.getPatientPesel());


        patient.setPersonDetails(patientDetails);
        patientCard.setPatient(patient);

        Doctor doctor = new Doctor();
        PersonDetails doctorDetails = new PersonDetails();
        doctorDetails.setName(patientCardDTO.getDoctorFirstName());
        doctorDetails.setSurname(patientCardDTO.getDoctorLastName());
        doctor.setDetails(doctorDetails);
        doctor.setId(patientCardDTO.getDoctorId());

        patientCard.setDoctor(doctor);
        patientCard.setSymptoms(patientCardDTO.getSymptoms());
        patientCard.setNoteDoctor(patientCardDTO.getNoteDoctor());
        patientCard.setDiagnosis(patientCardDTO.getDiagnosis());
        patientCard.setTreatment(patientCardDTO.getTreatment());
        patientCard.setDateOfVisit(patientCardDTO.getDateOfVisit());

        return patientCard;
    }

}

