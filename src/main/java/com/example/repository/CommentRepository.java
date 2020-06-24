package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * コメントを操作するリポジトリクラス.
 * 
 * @author junpei.azuma
 *
 */
@Repository
public class CommentRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER= (rs, i) -> {
		Comment comment= new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("id"));
		return comment;
	};
	
	/**
	 * 記事IDごとのコメントをすべて取得する.
	 * 
	 * @param articleId 記事id
	 * @return 記事ごとのコメントリスト
	 */
	public List<Comment> findByArticleId(int articleId) {
		String sql = "select name, content from comments where article_id = :articleId order by id desc;";
		return template.query(sql, COMMENT_ROW_MAPPER);
	}
	
	/**
	 * コメントを作成する.
	 * 
	 * @param comment 作成するコメント
	 */
	public void insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		
		String sql = "insert into comments(name, content, article_id) values(:name, :content, :articleId);";
		template.update(sql, param);	
	}
	
	/**
	 * 記事が削除されたときにその記事と紐づけられているコメントを全て削除する.
	 * 
	 * @param articleId 削除される記事id
	 */
	public void deleteByArticleId(Integer articleId) {
		String sql = "delete from comments where article_id = :articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		template.update(sql, param);
	}
}
