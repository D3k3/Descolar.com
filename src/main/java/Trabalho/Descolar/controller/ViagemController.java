package Trabalho.Descolar.controller;

import Trabalho.Descolar.model.Usuario;
import Trabalho.Descolar.model.Viagem;
import Trabalho.Descolar.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/viagens")
@SessionAttributes("usuarioLogado")
public class ViagemController {

    private final ViagemRepository viagemRepository;

    @Autowired
    public ViagemController(ViagemRepository viagemRepository) {
        this.viagemRepository = viagemRepository;
    }

    // Listar todas as viagens
    @GetMapping
    public String listarViagens(Model model) {
        model.addAttribute("viagens", viagemRepository.findAll());
        return "viagens/lista";
    }

    // Mostrar formulário de criação
    @GetMapping("/nova")
    public String mostrarFormularioCriacao(Model model) {
        model.addAttribute("viagem", new Viagem());
        return "viagens/formulario";
    }

    // Processar criação de viagem
    @PostMapping("/salvar")
    public String salvarViagem(@ModelAttribute Viagem viagem, RedirectAttributes redirectAttributes) {
        try {
            viagemRepository.save(viagem);
            redirectAttributes.addFlashAttribute("sucesso", "Viagem cadastrada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar viagem: " + e.getMessage());
        }
        return "redirect:/viagens";
    }

    // Mostrar formulário de edição
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Viagem> viagemOptional = viagemRepository.findById(id);
        if (!viagemOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("erro", "Viagem não encontrada!");
            return "redirect:/viagens";
        }
        model.addAttribute("viagem", viagemOptional.get());
        return "viagens/formulario";
    }

    // Processar atualização
    @PostMapping("/atualizar/{id}")
    public String atualizarViagem(@PathVariable Long id, @ModelAttribute Viagem viagem, RedirectAttributes redirectAttributes) {
        try {
            if (!viagemRepository.existsById(id)) {
                redirectAttributes.addFlashAttribute("erro", "Viagem não encontrada!");
                return "redirect:/viagens";
            }
            viagem.setId(id);
            viagemRepository.save(viagem);
            redirectAttributes.addFlashAttribute("sucesso", "Viagem atualizada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar viagem: " + e.getMessage());
        }
        return "redirect:/viagens";
    }

    // Excluir viagem
    @GetMapping("/excluir/{id}")
    public String excluirViagem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (!viagemRepository.existsById(id)) {
                redirectAttributes.addFlashAttribute("erro", "Viagem não encontrada!");
                return "redirect:/viagens";
            }
            viagemRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("sucesso", "Viagem excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir viagem: " + e.getMessage());
        }
        return "redirect:/viagens";
    }

    // Detalhes da viagem
    @GetMapping("/detalhes/{id}")
    public String detalhesViagem(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Viagem> viagemOptional = viagemRepository.findById(id);
        if (!viagemOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("erro", "Viagem não encontrada!");
            return "redirect:/viagens";
        }
        model.addAttribute("viagem", viagemOptional.get());
        return "viagens/detalhes";
    }

    // Dashboard com usuário logado
    @GetMapping("/dashboard")
    public String mostrarDashboard(@ModelAttribute("usuarioLogado") Usuario usuario, Model model) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("viagens", viagemRepository.findAll());
        return "dashboard";
    }
}
