package com.example.domain;

/**
 * 記事情報を保持するドメインクラス.
 * 
 * @author junpei.azuma
 *
 */
public class Article {
	//記事id
	private Integer id;
	//記事投稿者の名前(記事タイトルではないので注意)
	private String name;
	//記事の内容
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
