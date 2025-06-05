package Trabalho.Descolar.controller;

import Trabalho.Descolar.model.Usuario;
import Trabalho.Descolar.model.Viagem;
import Trabalho.Descolar.repository.ViagemRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;


@Controller
@SessionAttributes("usuarioLogado")
public class UsuarioController {

    @Autowired
    private ViagemRepository viagemRepository;

    @GetMapping("/")
    public String redirecionarParaCadastro(HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        return (usuarioLogado != null) ? "redirect:/dashboard" : "redirect:/cadastro";
    }

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@ModelAttribute Usuario usuario, Model model) {
        model.addAttribute("usuarioLogado", usuario);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        // Verifica se usuário está logado
        if (!model.containsAttribute("usuarioLogado")) {
            return "redirect:/cadastro";
        }
        
        // Adiciona viagens para o template
        model.addAttribute("viagens", viagemRepository.findAll());
        return "dashboard";
    }

    @GetMapping("/sair")
    public String sair(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/cadastro";
    }
}