package br.ufsm.csi.projetodiogopoow.controller;

import br.ufsm.csi.projetodiogopoow.dao.DirigenteDAO;
import br.ufsm.csi.projetodiogopoow.model.Dirigente;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/")
    public String redirectRoot() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "index"; // JSP de login: /WEB-INF/jsp/index.jsp
    }

    @GetMapping("/index")
    public String mostrarIndex() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session,
                        Model model) {

        if (email == null || senha == null || email.isBlank() || senha.isBlank()) {
            model.addAttribute("msg", "Preencha todos os campos.");
            return "index";
        }

        Dirigente dirigente = DirigenteDAO.buscarPorEmail(email);

        if (dirigente != null && dirigente.getSenha().equals(senha)) {
            session.setAttribute("dirigenteLogado", dirigente);
            return "redirect:/login/home";
        } else {
            model.addAttribute("msg", "Email ou senha incorretos.");
            return "index";
        }
    }

    @GetMapping("/login/home")
    public String home(HttpSession session) {
        if (session.getAttribute("dirigenteLogado") == null) {
            return "redirect:/login";
        }
        return "home";
    }


}
