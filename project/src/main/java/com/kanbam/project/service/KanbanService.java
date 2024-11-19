package com.kanbam.project.service;

import com.kanbam.project.model.*;
import com.kanbam.project.repository.KanbanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KanbanService {

    @Autowired
    private KanbanRepository kanbanRepository;

    public Map<Status, List<KanbanModel>> listarTarefas() throws Exception{
        List<KanbanModel> tarefas = kanbanRepository.findAll();

        if (tarefas.isEmpty()) {
            throw new Exception("Nenhuma tarefa encontrada!");
        }

        Map<Status, List<KanbanModel>> tarefasPorStatus = tarefas.stream()
                .collect(Collectors.groupingBy(KanbanModel::getStatus));

        for (Map.Entry<Status, List<KanbanModel>> entry : tarefasPorStatus.entrySet()) {
            List<KanbanModel> tarefasOrdenadas = entry.getValue().stream()
                    .sorted(Comparator.comparing(KanbanModel::getPrioridade))
                    .toList();
            entry.setValue(tarefasOrdenadas);
        }
        return tarefasPorStatus;
    }

    public String criarTarefa(KanbanCreat kanbanCreat) {
        KanbanModel tarefa = new KanbanModel();

        tarefa.setTitulo(kanbanCreat.getTitulo());
        tarefa.setDescricao(kanbanCreat.getDescricao());
        tarefa.setStatus(Status.AFAZER);
        tarefa.setPrioridade(kanbanCreat.getPrioridade());
        tarefa.setDateLimit(kanbanCreat.getDateLimit());

        kanbanRepository.save(tarefa);
        return "Tarefa criada com sucesso";
    }

    public String atualizarTarefa(long id, KanbanUpdate kanbanUpdate) throws Exception {
        KanbanModel tarefa = tarefa(id);

        boolean updated = false;

        if (kanbanUpdate.getTitulo() != null && !kanbanUpdate.getTitulo().trim().isEmpty()) {
            tarefa.setTitulo(kanbanUpdate.getTitulo());
            updated = true;
        }
        if (kanbanUpdate.getDescricao() != null && !kanbanUpdate.getDescricao().trim().isEmpty()) {
            tarefa.setDescricao(kanbanUpdate.getDescricao());
            updated = true;
        }
        if (kanbanUpdate.getPrioridade() != null) {
            tarefa.setPrioridade(kanbanUpdate.getPrioridade());
            updated = true;
        }

        if (updated) {
            kanbanRepository.save(tarefa);
            return "Tarefa " + id + " atualizada com sucesso!";
        } else {
            return "Nenhum campo foi alterado. Nenhuma atualização realizada.";
        }
    }

    public String moverTarefa(long id) throws Exception {
        KanbanModel tarefa = tarefa(id);

        if(tarefa.getStatus() == Status.AFAZER){
            tarefa.setStatus(Status.EMPROGRESSO) ;
        }else if(tarefa.getStatus() == Status.EMPROGRESSO){
            tarefa.setStatus(Status.CONCLUIDA);
        }else{
            throw new Exception("A tarefa já foi concluida");
        }
        kanbanRepository.save(tarefa);
        return "Tarefa movida para " + tarefa.getStatus() + " com sucesso";
    }

    public KanbanModel tarefa(long id) throws Exception {
        Optional<KanbanModel> optionalTarefa = kanbanRepository.findById(id);
        if (!optionalTarefa.isPresent()) {
            throw new Exception("Tarefa não encontrada!");
        }
        return optionalTarefa.get();
    }

    public String deletarTarefa(long id) throws Exception {
        KanbanModel deletar = tarefa(id);
        kanbanRepository.delete(deletar);
        return "Tarefa " + id +" deletada com sucesso!!";
    }

    public List<KanbanModel> filtrarTarefas(Prioridade prioridade, Date dateLimit) {
        List<KanbanModel> tarefas = kanbanRepository.findAll();
        List<KanbanModel> tarefasFiltradas = new ArrayList<>();

        for (KanbanModel tarefa : tarefas) {
            boolean prioridadeMatch = (prioridade == null || tarefa.getPrioridade().equals(prioridade));
            boolean dataMatch = (dateLimit == null || tarefa.getDateLimit().equals(dateLimit));
            if (prioridadeMatch && dataMatch) {
                tarefasFiltradas.add(tarefa);
            }
        }

        return tarefasFiltradas;
    }

    public List<KanbanModel> relatorioTarefas() throws Exception {
        Map<Status, List<KanbanModel>> tarefas = listarTarefas();
        List<KanbanModel> tarefasAtrasadas = new ArrayList<>();
        Date hoje = new Date();

        for (Map.Entry<Status, List<KanbanModel>> entry : tarefas.entrySet()) {
            if (!entry.getKey().equals(Status.CONCLUIDA)) {
                tarefasAtrasadas.addAll(
                        entry.getValue().stream()
                                .filter(tarefa -> tarefa.getDateLimit() != null && tarefa.getDateLimit().before(hoje))
                                .collect(Collectors.toList())
                );
            }
        }
        tarefasAtrasadas.sort(Comparator.comparing(KanbanModel::getDateLimit));

        return tarefasAtrasadas;
    }
}
