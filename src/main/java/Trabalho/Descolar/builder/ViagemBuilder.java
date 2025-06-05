package Trabalho.Descolar.builder;

import Trabalho.Descolar.model.Viagem;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class ViagemBuilder {
    private String destino;
    private LocalTime embarcacao;
    private Integer duracao;

    public ViagemBuilder comDestino(String destino) {
        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("Destino não pode ser vazio");
        }
        this.destino = destino;
        return this;
    }

    public ViagemBuilder comEmbarcacao(String horarioMilitar) {
        try {
            this.embarcacao = LocalTime.parse(horarioMilitar);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de horário inválido. Use HH:MM (ex: 14:30)");
        }
        return this;
    }

    public ViagemBuilder comDuracaoMinutos(int duracao) {
        if (duracao <= 0) {
            throw new IllegalArgumentException("Duração deve ser maior que zero");
        }
        this.duracao = duracao;
        return this;
    }

    public Viagem build() {
        if (this.destino == null) {
            throw new IllegalStateException("Destino é obrigatório");
        }
        if (this.embarcacao == null) {
            throw new IllegalStateException("Horário de embarcação é obrigatório");
        }
        if (this.duracao == null) {
            throw new IllegalStateException("Duração é obrigatória");
        }

        Viagem viagem = new Viagem();
        viagem.setDestino(this.destino);
        viagem.setEmbarcacao(this.embarcacao);
        viagem.setDuracao(this.duracao);
        return viagem;
    }
}