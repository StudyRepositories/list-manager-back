package br.com.study.listmanagerback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.study.listmanagerback.model.ToDo;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

}