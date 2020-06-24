package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

/**
 * 記事の操作を行うリポジトリクラス.
 * 
 * @author junpei.azuma
 *
 */
@Repository
public class ArticleRepository {
	@Autowired 
	private NamedParameterJdbcTemplate template;
	
	private RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};
	
	/**
	 * 記事の全件検索を行う.
	 * 
	 * @return 全ての記事のリスト
	 */
	public  List<Article> findAll() {
		String sql = "select id, name, content from articles order by id desc;";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		return articleList;
	}
	
	/**
	 * 記事の作成を行う.
	 * 
	 * @param article 作成する記事
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "insert into articles(name, content) values(:name, :content);";
		template.update(sql, param);
	}
	
	/**
	 * 記事の削除を行う.
	 * 
	 * @param id 削除対象の記事id
	 */
	public void deleteById(int id) {
		String sql = "delete from articles where id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
