package Trabalho.Descolar.controller;

import Trabalho.Descolar.builder.ViagemBuilder;
import Trabalho.Descolar.model.Viagem;
import Trabalho.Descolar.model.Usuario;
import Trabalho.Descolar.repository.ViagemRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ViagemRepository viagemRepository;

    public AdminController(ViagemRepository viagemRepository) {
        this.viagemRepository = viagemRepository;
    }

    // Exibe o formulário de criação de viagem
    @GetMapping("/viagens/nova")
    public String mostrarFormCriacao(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }
        model.addAttribute("viagemBuilder", new ViagemBuilder());
        return "admin/nova-viagem";
    }

    // Processa a criação de nova viagem
    @PostMapping("/viagens/criar")
    public String criarViagem(
            @RequestParam String destino,
            @RequestParam String embarcacao,
            @RequestParam int duracao,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {
            Viagem viagem = new ViagemBuilder()
                    .comDestino(destino)
                    .comEmbarcacao(embarcacao)
                    .comDuracaoMinutos(duracao)
                    .build();

            viagemRepository.save(viagem);
            redirectAttributes.addFlashAttribute("successMessage", "Viagem criada com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/viagens/nova";
        }

        return "redirect:/dashboard";
    }

    // Exibe o dashboard com todas as viagens
    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", usuario);
        model.addAttribute("viagens", viagemRepository.findAll());
        return "dashboard";
    }

    // Exibe o formulário de edição de viagem
    @GetMapping("/viagens/editar/{id}")
    public String mostrarFormEdicao(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        Viagem viagem = viagemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de viagem inválido"));

        model.addAttribute("viagem", viagem);
        model.addAttribute("idViagem", id);
        return "admin/editar-viagem";
    }

    // Processa a edição da viagem
    @PostMapping("/viagens/editar/{id}")
    public String editarViagem(
            @PathVariable Long id,
            @RequestParam String destino,
            @RequestParam String embarcacao,
            @RequestParam int duracao,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {
            Viagem viagemExistente = viagemRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ID de viagem inválido"));

            Viagem viagemAtualizada = new ViagemBuilder()
                    .comDestino(destino)
                    .comEmbarcacao(embarcacao)
                    .comDuracaoMinutos(duracao)
                    .build();
            viagemAtualizada.setId(id);

            viagemRepository.save(viagemAtualizada);
            redirectAttributes.addFlashAttribute("successMessage", "Viagem atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/viagens/editar/" + id;
        }

        return "redirect:/dashboard";
    }

    // Processa a exclusão da viagem
    @GetMapping("/viagens/excluir/{id}")
    public String excluirViagem(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/dashboard";
        }

        try {
            Viagem viagem = viagemRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ID de viagem inválido"));

            viagemRepository.delete(viagem);
            redirectAttributes.addFlashAttribute("successMessage", "Viagem excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir viagem: " + e.getMessage());
        }

        return "redirect:/dashboard";
    }

    // Verifica se o usuário é administrador
    private boolean isAdmin(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        return usuario != null && "ADMIN".equals(usuario.getTipo());
    }
}
