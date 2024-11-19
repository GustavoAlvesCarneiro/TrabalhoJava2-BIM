package com.kanbam.project.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public class KanbanUpdate {
    private String titulo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
}
