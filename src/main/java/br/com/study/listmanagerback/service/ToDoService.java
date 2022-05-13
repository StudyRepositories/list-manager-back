package br.com.study.listmanagerback.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.study.listmanagerback.model.ToDo;
import br.com.study.listmanagerback.repository.ToDoRepository;

@Service
public class ToDoService {

	@Autowired
	private ToDoRepository toDoRepository;

	public List<ToDo> findAll() {
		return toDoRepository.findAll();
	}

	public ToDo findById(String id) {
		return toDoRepository.findById(Long.parseLong(id)).orElseThrow(EntityNotFoundException::new);
	}

	public ToDo save(ToDo toDo) {
		return toDoRepository.save(toDo);
	}

	public void deleteById(String id) {
		toDoRepository.deleteById(Long.parseLong(id));
	}
}

