package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;

@Controller
@RequestMapping("/article")
public class ArticleController {
	@Autowired
	private ArticleRepository repository;
	
	@ModelAttribute
	public ArticleForm setUpForm() {
		return new ArticleForm();
	}
	
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList  = repository.findAll();
		model.addAttribute("articleList", articleList);
		return "show-form";
	}
	
//	@RequestMapping("/insert-article")
//	public String insertArticle() {
//		repository.insert(article);
//	}
}
