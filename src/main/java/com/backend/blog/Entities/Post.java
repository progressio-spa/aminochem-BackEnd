package com.backend.blog.Entities;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Date;

@Entity
public class Post{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;
	private String body;
	private Date dateCreated;

	@ManyToOne
	private User creator;

	public Post(){
	}

	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getTitle(){
		return this.title;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getBody(){
		return this.body;
	}

	public void setBody(String body){
		this.body = body;
	}

	public Date getDateCreated(){
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated){
		this.dateCreated = dateCreated;
	}

	public User getCreator(){
		return this.creator;
	}

	public void setCreator(User creator){
		this.creator = creator;
	}
}