package com.kanbam.project.repository;

import com.kanbam.project.model.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kanbam.project.model.KanbanModel;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;

public interface KanbanRepositorio extends JpaRepository<KanbanModel, Long> {}
