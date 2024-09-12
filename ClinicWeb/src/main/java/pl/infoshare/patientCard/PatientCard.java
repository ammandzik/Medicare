package pl.infoshare.patientCard;

import jakarta.persistence.*;
import lombok.Data;
import pl.infoshare.clinicweb.doctor.Doctor;
import pl.infoshare.clinicweb.patient.Patient;

import java.time.LocalDate;

@Data
@Entity

public class PatientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;
    private String symptoms;
    private LocalDate dateOfVisit;
    private String noteDoctor;
    private String noteMedicalHistory;
    private String diagnosis;
    private String treatment;


}