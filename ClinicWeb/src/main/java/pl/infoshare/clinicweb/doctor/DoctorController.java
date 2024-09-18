package pl.infoshare.clinicweb.doctor;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.infoshare.clinicweb.patient.Address;
import pl.infoshare.clinicweb.user.PersonDetails;
import pl.infoshare.clinicweb.user.Utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;


    @RequestMapping("/doctors")
    public String viewDoctors(Model model, @RequestParam(required = false, value = "specialization") Specialization specialization) {

        List<DoctorDto> doctors;

        doctors = Utils.convertOptionalToList(doctorService.findAllDoctors());

        model.addAttribute("listDoctor", doctors);

        return "doctorsList";
    }

    @GetMapping("/doctor")
    public String doctorForm(Model model) {

        model.addAttribute("personDetails", new PersonDetails());
        model.addAttribute("address", new Address());

        return "doctor";
    }

    @PostMapping("/doctor")
    public String doctorFormSubmission(@ModelAttribute Doctor doctor,
                                       @Valid PersonDetails doctorDetails, BindingResult detailsBinding,
                                       @Valid Address doctorAddress, BindingResult addressBinding,
                                       @RequestParam("specialization") Specialization specialization,
                                       @RequestParam("pesel") String pesel,
                                       RedirectAttributes redirectAttributes, Model model) {


        if (detailsBinding.hasErrors() || addressBinding.hasErrors() || !Utils.hasPeselCorrectDigits(pesel)) {

            model.addAttribute("peselError", "Wprowadzony numer PESEL jest niepoprawny.");
            return "doctor";

        } else {

            redirectAttributes.addFlashAttribute("success", "Utworzono nowego lekarza w bazie.");

            doctorService.setDoctorAttributes(doctor, doctorDetails, doctorAddress, specialization);
            doctorService.addDoctor(doctor);

            return "redirect:/doctor";
        }

    }

    @GetMapping("/search-doctor")
    public String searchDoctorByPesel(@ModelAttribute Doctor doctor) {

        return "search-doctor";
    }

    @PostMapping("/search-doctor")
    public String searchDoctorByPesel(@RequestParam(value = "id", required = false) long id, Model model) {

        Optional <DoctorDto> doctorById = doctorService.findById(id);

        if (doctorService.findById(id).isPresent()) {

            model.addAttribute("searchForId", doctorById);
        } else {
            model.addAttribute("error", "Nie znaleziono lekarza o podanym numerze ID: " + id);
        }
        return "search-doctor";
    }

    @GetMapping("/update-doctor")
    public String fullDetailDoctor(@RequestParam(value = "id") long id, Model model) {

        model.addAttribute("doctor", doctorService.findById(id).get());

        return "update-doctor";
    }

    @PostMapping("/update-doctor")
    public String editDoctor(@ModelAttribute("doctor") DoctorDto doctor, Model model,
                             Address address, RedirectAttributes redirectAttributes) {

        doctorService.updateDoctor(doctor, address);
        model.addAttribute("doctor", doctor);
        model.addAttribute("address", address);
        redirectAttributes.addFlashAttribute("success", "Zaktualizowano dane lekarza.");

        return "redirect:doctors";
    }

    @GetMapping("/delete-doctor")
    public String showDeleteDoctorForm(@RequestParam("id") long id, Model model) {

        Optional<DoctorDto> doctorById = doctorService.findById(id);
        model.addAttribute("doctor", doctorById);

        return "delete-doctor";
    }

    @PostMapping("/delete-doctor")
    public String deleteDoctor(@RequestParam("id") long id, RedirectAttributes redirectAttributes) {

        Optional<DoctorDto> doctorById = doctorService.findById(id);

        if (doctorById.isPresent()) {

            doctorService.deleteDoctor(doctorById.get().getId());
            redirectAttributes.addFlashAttribute("success", "Usunięto dane lekarza.");
        }
        return "redirect:/doctors";
    }


}
