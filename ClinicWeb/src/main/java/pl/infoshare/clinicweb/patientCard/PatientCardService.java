package pl.infoshare.clinicweb.patientCard;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.infoshare.clinicweb.visit.VisitDto;

import java.util.List;

@Service
@Slf4j
public class PatientCardService {

    private final PatientCardRepository patientCardRepository;
    private final PatientCardMapper patientCardMapper;

    @Autowired
    public PatientCardService(PatientCardRepository patientCardRepository, PatientCardMapper patientCardMapper) {
        this.patientCardRepository = patientCardRepository;
        this.patientCardMapper = patientCardMapper;

    }

    public PatientCardDTO findById(Long id) {
        return patientCardRepository
                .findById(id)
                .map(patientCardMapper::toDto)
                .orElseThrow(()
                        -> new EntityNotFoundException(String.format("Patient card not found with given ID: %s", id)));
    }

    public List<PatientCard> findAllPatientCardByPatientId(Long patientId) {
        return patientCardRepository.findByPatientId(patientId);
    }

    public void patientCardSave(PatientCard patientCard) {
        PatientCard result = patientCardRepository.save(patientCard);
        log.info("Patient card saved with ID: {}", result.getId());
    }

    static PatientCardDTO getPatientCardDTO(VisitDto visit) {
        PatientCardDTO patientCardDTO = new PatientCardDTO();
        patientCardDTO.setPatientFirstName(visit.getPatientName());
        patientCardDTO.setPatientLastName(visit.getPatientSurname());
        patientCardDTO.setDoctorFirstName(visit.getDoctorName());
        patientCardDTO.setDoctorLastName(visit.getDoctorSurname());
        patientCardDTO.setPatientId(visit.getPatientId());
        patientCardDTO.setDoctorId(visit.getDoctorId());
        patientCardDTO.setPatientPesel(visit.getPatientPesel());
        patientCardDTO.setDateOfVisit(visit.getVisitDate());
        return patientCardDTO;
    }


}
