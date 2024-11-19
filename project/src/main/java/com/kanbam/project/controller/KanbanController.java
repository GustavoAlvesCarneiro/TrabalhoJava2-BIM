package com.kanbam.project.controller;

import com.kanbam.project.model.*;
import com.kanbam.project.service.KanbanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class KanbanController {

    @Autowired()
    private KanbanService kanbanService;


    @GetMapping()
    public Map<Status, List<KanbanModel>> getAllTask() throws Exception{
        return kanbanService.listarTarefas();
    }


    @PutMapping("{id}")
    public String getOneTask(@PathVariable long id, @RequestBody KanbanUpdate kanbanUpdate) throws Exception{
        return kanbanService.atualizarTarefa(id, kanbanUpdate);
    }


    @PostMapping()
    public String postTask(@RequestBody KanbanCreat kanbanCreat) {
        return kanbanService.criarTarefa(kanbanCreat);
    }


    @PutMapping("{id}/move")
    public String putTask(@PathVariable long id) throws Exception {
        return kanbanService.moverTarefa(id);
    }

    @DeleteMapping("{id}")
    public String deleteTask(@PathVariable long id) throws Exception {
        return kanbanService.deletarTarefa(id);
    }


    @GetMapping("/filter")
    public List<KanbanModel> filtrarTarefas(
            @RequestParam(required = false) Prioridade prioridade,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateLimit) {
        return kanbanService.filtrarTarefas(prioridade, dateLimit);
    }


    @GetMapping("/report")
    public List<KanbanModel> ralatorioTarefas() throws Exception{
        return kanbanService.relatorioTarefas();
    }
}
