package br.com.study.listmanagerback.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ToDo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private Boolean completed;

	public ToDo() {
	}

	public ToDo(String title, Boolean completed) {
		this.title = title;
		this.completed = completed;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Boolean getCompleted() {
		return completed;
	}

}
