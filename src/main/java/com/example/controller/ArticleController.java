package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
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
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
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
		model.addAttribute("articleList", articleList);
		return "show-form";
	}
	
	/**
	 * 記事を作成する.
	 * 
	 * @param form 入力値を受け取るためのフォームオブジェクト(フォームクラス名を明示したほうが良いかも)
	 * @return indexメソッドにリダイレクト
	 */
	@RequestMapping("/insert-article")
	public String insertArticle(ArticleForm form) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);
		return "redirect:/article";
	}
	
	/**
	 * コメントを作成する.
	 * 
	 * @param form 入力値を受け取るためのフォームオブジェクト
	 * @return indexメソッドにリダイレクト
	 */
	@RequestMapping("/insert-comment")
	public String insertComment(CommentForm form) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form,comment);
		commentRepository.insert(comment);
		return "redirect:/article";
	}
	
	/**
	 * コメントを削除する(記事が削除されたときに同時にコメントも削除される仕様です).
	 * 
	 * @param articleId 削除する記事のid
	 * @return indexメソッドにリダイレクト
	 */
	@RequestMapping("/delete-article")
	public String deleteArticle(String articleId) {
	    Integer deleteArticleId = Integer.parseInt(articleId);
		commentRepository.deleteByArticleId(deleteArticleId);
		articleRepository.deleteById(deleteArticleId);
		return "redirect:/article";
	}
}
