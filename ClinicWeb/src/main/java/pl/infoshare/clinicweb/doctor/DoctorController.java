package pl.infoshare.clinicweb.doctor;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.infoshare.clinicweb.patient.Address;
import pl.infoshare.clinicweb.user.Utils;
import pl.infoshare.clinicweb.user.entity.PersonDetails;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping(value = "/doctors")
    public String listDoctors(@RequestParam(required = false) Specialization specialization, Model model,
                              @RequestParam(value = "page")
                              @ModelAttribute Optional<Integer> page,
                              @RequestParam(value = "size")
                              @ModelAttribute Optional<Integer> size) {

        log.info("Invoked listDoctors method");
        int currentPage = page.orElse(1);
        Page<DoctorDto> doctorPage;

        if (specialization == null) {
            doctorPage = doctorService.findAllPage(currentPage);
            log.info("Searching all doctors on page: {}", currentPage);
        } else {
            doctorPage = doctorService.findDoctorBySpecialization(currentPage, specialization);
            log.info("Searching doctors with specialization: {} on page: {}", specialization, currentPage);
        }

        long totalElements = doctorPage.getTotalElements();
        int totalPages = doctorPage.getTotalPages();
        List<DoctorDto> doctors = doctorPage.getContent();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        if (totalPages == 0) {
            totalPages = 1;
        }

        model.addAttribute("specialization", specialization);
        model.addAttribute("doctorsPage", doctorPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("listDoctor", doctors);

        log.info("Found {} doctors, total {} pages", doctors.size(), totalPages);

        return "doctor/doctors";
    }

    @GetMapping("/doctor")
    public String doctorForm(Model model) {

        model.addAttribute("personDetails", new PersonDetails());
        model.addAttribute("address", new Address());
        log.info("Opening the form to create a new doctor");
        return "doctor/doctor";
    }

    @PostMapping("/doctor")
    public String doctorFormSubmission(@ModelAttribute Doctor doctor,
                                       @Valid PersonDetails doctorDetails, BindingResult detailsBinding,
                                       @Valid Address doctorAddress, BindingResult addressBinding,
                                       @RequestParam("specialization") Specialization specialization,
                                       @RequestParam("pesel") String pesel,
                                       RedirectAttributes redirectAttributes, Model model) {


        if (detailsBinding.hasErrors() || addressBinding.hasErrors() || !Utils.hasPeselCorrectDigits(pesel)) {
            log.warn("Validation errors occurred during doctor form submission.");

            if (detailsBinding.hasErrors()) {
                log.debug("Details binding errors: {}", detailsBinding.getAllErrors());
            }
            if (addressBinding.hasErrors()) {
                log.debug("Address binding errors: {}", addressBinding.getAllErrors());
            }
            if (!Utils.hasPeselCorrectDigits(pesel)) {
                log.debug("Invalid PESEL: {}", pesel);
            }

            model.addAttribute("peselError", "Wprowadzony numer PESEL jest niepoprawny.");
            return "doctor/doctor";

        } else {
            log.info("Validation successful, creating doctor with details: {}", doctorDetails);

            redirectAttributes.addFlashAttribute("success", "Utworzono nowego lekarza w bazie.");

            doctorService.setDoctorAttributes(doctor, doctorDetails, doctorAddress, specialization);
            doctorService.addDoctor(doctor);

            log.info("Doctor successfully added to the database.");
            return "redirect:/doctor";
        }
    }

    @GetMapping("/search-doctor")
    public String searchDoctorById(@ModelAttribute Doctor doctor) {

        return "doctor/search-doctor";
    }

    @PostMapping("/search-doctor")
    public String searchDoctorById(@RequestParam(value = "id", required = false) long id, Model model) {
        log.info("Invoked searchDoctorById method with id: {}", id);

        DoctorDto doctorById = doctorService.findById(id);
        if (doctorById == null) {
            log.info("No doctor found with id: {}", id);
        }
        model.addAttribute("searchForId", doctorById);
        return "doctor/search-doctor";
    }

    @GetMapping("/update-doctor")
    public String fullDetailDoctor(@RequestParam(value = "id") long id, Model model) {
        log.info("Invoked fullDetailDoctor method with id: {}", id);
        model.addAttribute("doctor", doctorService.findById(id));
        log.info("doctor with ID {} found with details ", id);
        return "doctor/update-doctor";
    }

    @PostMapping("/update-doctor")
    public String editDoctor(@ModelAttribute("doctor") DoctorDto doctor, Model model,
                             Address address, RedirectAttributes redirectAttributes, PersonDetails personDetails) {
        log.info("Invoked editDoctor method");
        try {
            doctorService.updateDoctor(doctor, address);
            model.addAttribute("doctor", doctor);
            model.addAttribute("address", address);
            redirectAttributes.addFlashAttribute("success", "Doctor data updated successfully.");
            log.info("Updated doctor with id: {}", doctor.getId());
        } catch (Exception e) {
            log.error("error update doctor with id:  {}", doctor.getId());
        }
        return "redirect:doctors";
    }

    @GetMapping("/delete-doctor")
    public String showDeleteDoctorForm(@RequestParam("id") long id, Model model) {

        DoctorDto doctorById = doctorService.findById(id);
        if (doctorById == null) {
            log.info("No doctor found with id: {}", id);
        }
        model.addAttribute("doctor", doctorById);
        return "doctor/delete-doctor";
    }

    @PostMapping("/delete-doctor")
    public String deleteDoctor(@RequestParam("id") long id, RedirectAttributes redirectAttributes) {

        DoctorDto doctorById = doctorService.findById(id);
        if (doctorById != null) {
            doctorService.deleteDoctor(doctorById.getId());
            log.info("Deleted doctor with id: {}", id);
            redirectAttributes.addFlashAttribute("success", "Doctor data deleted successfully.");
        } else {
            log.info("No doctor found with id: {}", id);
        }
        return "redirect:/doctors";
    }


}
