package Trabalho.Descolar.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "viagens")
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String destino;
    private LocalTime embarcacao;
    private Integer duracao; // em minutos

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    
    public LocalTime getEmbarcacao() { return embarcacao; }
    public void setEmbarcacao(LocalTime embarcacao) { this.embarcacao = embarcacao; }
    
    public Integer getDuracao() { return duracao; }
    public void setDuracao(Integer duracao) { this.duracao = duracao; }
    
    // Métodos adicionais para o template
    public double getDuracaoHoras() {
        return duracao != null ? duracao / 60.0 : 0;
    }
    
    public double getPreco() {
        // Exemplo: R$50 por hora de viagem
        return getDuracaoHoras() * 50;
    }
    
    public String getHoraEmbarcacaoFormatada() {
        return embarcacao != null ? embarcacao.format(DateTimeFormatter.ofPattern("HH:mm")) : "";
    }
}