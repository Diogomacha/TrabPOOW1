package br.ufsm.csi.projetodiogopoow.controller;

import br.ufsm.csi.projetodiogopoow.model.Jogador;
import br.ufsm.csi.projetodiogopoow.service.JogadorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jogador")
public class JogadorController {

    private final JogadorService service = new JogadorService();


    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("jogador", new Jogador());
        return "cadastrar-jogador";  // /WEB-INF/jsp/cadastrar-jogador.jsp
    }


    @PostMapping
    public String postJogador(@RequestParam String opcao,
                              @ModelAttribute Jogador jogador,
                              HttpSession session) {

        System.out.println("POST -> opcao: " + opcao);

        switch (opcao) {
            case "cadastrar" -> {
                service.inserir(jogador);
                return "redirect:/jogador/lista";
            }
            case "alterar" -> {
                service.alterar(jogador);
                return "redirect:/jogador/lista";
            }
            case "excluir" -> {
                service.excluir(jogador.getId());
                return "redirect:/jogador/lista";
            }
            default -> {
                return "erro";
            }
        }
    }

    // Lista todos jogadores
    @GetMapping("/lista")
    public String listarJogadores(Model model) {
        model.addAttribute("jogadores", service.getJogadores());
        return "meu-time";
    }


    @GetMapping("/perfil")
    public String perfilJogador(@RequestParam Integer id, Model model) {
        Jogador jogador = service.buscarPorId(id);
        model.addAttribute("jogador", jogador);
        return "perfil-jogador";
    }
}
