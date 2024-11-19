package com.kanbam.project.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
public class KanbanModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String titulo;

    private String descricao;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date creatAt;

    @Enumerated(EnumType.STRING)
    private  Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.DATE)
    private Date dateLimit;

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getCreatAt() {
        return creatAt;
    }

    public Status getStatus() {
        return status;
    }

    public Date getDateLimit() {
        return dateLimit;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public void setDateLimit(Date dateLimit) {
        this.dateLimit = dateLimit;
    }
}
