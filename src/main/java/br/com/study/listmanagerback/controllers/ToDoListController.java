package br.com.study.listmanagerback.controllers;

import br.com.study.listmanagerback.entities.ToDoList;
import br.com.study.listmanagerback.services.ToDoListService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todolists")
public class ToDoListController {

    private final ToDoListService service;

    public ToDoListController(ToDoListService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createToDoList(@RequestBody ToDoList toDoList) {
        return service.create(toDoList);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ToDoList findToDoListById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    public List<ToDoList> findAllToDoLists() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateToDoListById(@PathVariable Long id, @RequestBody ToDoList toDoList) {
        service.updateById(id, toDoList);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToDoListById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
