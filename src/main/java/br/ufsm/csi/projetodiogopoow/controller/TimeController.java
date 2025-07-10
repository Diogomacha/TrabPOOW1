package br.ufsm.csi.projetodiogopoow.controller;

import br.ufsm.csi.projetodiogopoow.model.Dirigente;
import br.ufsm.csi.projetodiogopoow.model.Time;
import br.ufsm.csi.projetodiogopoow.service.TimeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/time")
public class TimeController {

    private final TimeService service = new TimeService();


    @GetMapping
    public String redirecionarMeuTime() {
        return "redirect:/time/meu-time";
    }


    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("time", new Time());
        return "cadastrar-time";
    }


    @GetMapping("/meu-time")
    public String mostrarMeuTime(HttpSession session, Model model) {
        Dirigente dirigente = (Dirigente) session.getAttribute("dirigenteLogado");
        if (dirigente == null) {
            return "redirect:/login";
        }

        Time time = service.buscarPorDirigente(dirigente.getId());
        if (time == null) {
            time = new Time();
        }
        model.addAttribute("time", time);

        return "meu-time";
    }


    @PostMapping
    public String processarFormulario(
            @RequestParam String opcao,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) Integer dirigenteId,
            RedirectAttributes redirectAttributes
    ) {
        switch (opcao) {
            case "cadastrar" -> {
                if (nome == null || cidade == null || dirigenteId == null) {
                    redirectAttributes.addFlashAttribute("msg", "Preencha todos os campos para cadastro.");
                    return "redirect:/time/cadastrar";
                }
                Time novo = new Time(0, nome, cidade, dirigenteId);
                service.inserir(novo);
                redirectAttributes.addFlashAttribute("msg", "Time cadastrado com sucesso!");
            }
            case "alterar" -> {
                if (id == null || nome == null || cidade == null || dirigenteId == null) {
                    redirectAttributes.addFlashAttribute("msg", "Preencha todos os campos para alteração.");
                    return "redirect:/time/meu-time";
                }
                Time t = new Time(id, nome, cidade, dirigenteId);
                service.alterar(t);
                redirectAttributes.addFlashAttribute("msg", "Time alterado com sucesso!");
            }
            case "excluir" -> {
                if (id != null) {
                    service.excluir(id);
                    redirectAttributes.addFlashAttribute("msg", "Time excluído com sucesso!");
                } else {
                    redirectAttributes.addFlashAttribute("msg", "ID inválido para exclusão.");
                }
            }
            default -> {
                redirectAttributes.addFlashAttribute("msg", "Opção inválida.");
            }
        }

        return "redirect:/time/meu-time";
    }


    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable int id, RedirectAttributes redirectAttributes) {
        service.excluir(id);
        redirectAttributes.addFlashAttribute("msg", "Time excluído com sucesso!");
        return "redirect:/time/meu-time";
    }
}
