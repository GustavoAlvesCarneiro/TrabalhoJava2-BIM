package com.kanbam.project.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;



import java.util.Date;

public class KanbanCreat {

    private String titulo;


    private String descricao;

    @Enumerated(EnumType.STRING)

    private Prioridade prioridade;


    private Date dateLimit;

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public Date getDateLimit() {
        return dateLimit;
    }
}
