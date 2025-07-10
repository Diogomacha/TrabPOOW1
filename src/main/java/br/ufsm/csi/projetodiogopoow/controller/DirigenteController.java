package br.ufsm.csi.projetodiogopoow.controller;

import br.ufsm.csi.projetodiogopoow.model.Dirigente;
import br.ufsm.csi.projetodiogopoow.service.DirigenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dirigente")
public class DirigenteController {

    private static final DirigenteService service = new DirigenteService();


    @GetMapping("/cadastro")
    public String mostrarCadastro(Model model) {
        model.addAttribute("dirigente", new Dirigente());
        return "cadastro-dirigente";
    }


    @GetMapping("/perfil")
    public String mostrarPerfil(HttpSession session, Model model) {
        Dirigente dirigenteLogado = (Dirigente) session.getAttribute("dirigenteLogado");
        if (dirigenteLogado == null) {
            return "redirect:/index";
        }

        Dirigente dirigente = service.buscarPorEmail(dirigenteLogado.getEmail());
        model.addAttribute("dirigente", dirigente);
        return "perfil-dirigente";
    }


    @PostMapping
    public String handlePost(@RequestParam String opcao,
                             @ModelAttribute Dirigente dirigente,
                             HttpSession session) {

        switch (opcao) {
            case "cadastrar" -> {
                service.inserir(dirigente);
                return "redirect:/index";
            }

            case "alterar" -> {
                service.alterar(dirigente);
                session.setAttribute("dirigenteLogado", dirigente);
                return "redirect:/dirigente/perfil";
            }

            case "excluir" -> {
                service.excluir(dirigente.getId());
                session.invalidate();
                return "redirect:/index";
            }

            default -> {
                return "erro";
            }
        }
    }


    @GetMapping
    public String handleGet(@RequestParam(required = false) String opcao,
                            @RequestParam(required = false) Integer id,
                            HttpSession session) {

        if ("excluir".equals(opcao) && id != null) {
            service.excluir(id);
            session.invalidate();
            return "redirect:/index";
        }

        return "redirect:/dirigente/perfil";
    }
}
