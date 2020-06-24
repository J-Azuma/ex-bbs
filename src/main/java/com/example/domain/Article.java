package com.example.domain;

/**
 * 記事情報を保持するドメインクラス.
 * 
 * @author junpei.azuma
 *
 */
public class Article {
	private Integer id;
	private String name;
	private String content;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", name=" + name + ", content=" + content + "]";
	}
	
}
