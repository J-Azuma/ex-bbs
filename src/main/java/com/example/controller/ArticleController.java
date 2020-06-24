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
import com.example.repository.CommentRepository;

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
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository  commentRepository;
	
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
		List<Article> articleList  = articleRepository.findAll();
		
		//記事ごとにコメントを取得して、得られたリストをarticleオブジェクトにセット
		for (Article article : articleList) {
			article.setCommentList(commentRepository.findByArticleId(article.getId()));
		}
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
		articleRepository.insert(article);
		return "redirect:/article";
	}
}
