package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;

/**
 * 記事を操作するコントローラクラス.
 * 
 * @author junpei.azuma
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
	@Autowired
	private ArticleRepository repository;
	
	@ModelAttribute
	public ArticleForm setUpForm() {
		return new ArticleForm();
	}
	
	/**
	 * 投稿表示画面を表示する.
	 * 
	 * @param model リクエストスコープに値を渡すためのオブジェクト
	 * @return 投稿表示画面
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList  = repository.findAll();
		model.addAttribute("articleList", articleList);
		return "show-form";
	}
	
	/**
	 * 記事を作成する.
	 * 
	 * @param form 入力値を受け取るためのフォームオブジェクト(フォームクラス名を明示したほうが良いかも)
	 * @return indexメソッドにフォワード
	 */
	@RequestMapping("/insert-article")
	public String insertArticle(ArticleForm form) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		repository.insert(article);
		return "forward:/article";
	}
}
