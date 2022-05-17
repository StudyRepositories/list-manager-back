package br.com.study.listmanagerback.services;

import br.com.study.listmanagerback.entities.ToDoList;
import br.com.study.listmanagerback.repositories.ToDoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoListService {
    private final ToDoListRepository repository;

    public ToDoListService(ToDoListRepository repository) {
        this.repository = repository;
    }

    public String create(ToDoList toDoList) {
        ToDoList entity = repository.save(toDoList);
        return entity.getName() + " has been created.";
    }

    public ToDoList findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ToDoList> findAll() {
        return repository.findAll();
    }

    public void updateById(Long id, ToDoList toDoList) {
        Optional<ToDoList> tdl = repository.findById(id);
        if(tdl.isPresent()) {
            ToDoList obj = tdl.get();
            obj.setName(toDoList.getName());
            obj.setDescription(toDoList.getDescription());
            repository.save(obj);
        }
    }

    public void deleteById(Long id) {
        Optional<ToDoList> tdl = repository.findById(id);
        if(tdl.isPresent()) {
            repository.deleteById(id);
        }
    }
}
