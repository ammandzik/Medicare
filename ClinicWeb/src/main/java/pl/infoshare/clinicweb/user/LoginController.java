package pl.infoshare.clinicweb.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String getIndex() {

        return "home/index";
    }

    @GetMapping("/login")
    public String login() {

        User user = getPrincipal();

        if (user != null) {
            return "redirect:/index";
        }

        return "user/login";
    }

    @GetMapping("/logout")
    public String logout() {


        return "home/index";

    }

    private User getPrincipal() {
        User user = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }


}
