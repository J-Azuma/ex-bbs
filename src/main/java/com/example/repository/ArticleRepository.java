package com.example.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import com.example.domain.Article;
import com.example.domain.Comment;

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
	/**
	 * 記事の全件検索を行う.
	 * 
	 * @return 全ての記事のリスト
	 */
	public List<Article> findAll() {
		// SQLは合ってる？→articleの中身は取得できている。しかし、同じ記事が複数回取得されてしまう。
		String sql = "select a.id, a.name, a.content, c.id as com_id, c.name as com_name, c.content as com_content, article_id"
				+    " from articles as a left outer join comments as c on a.id = c.article_id"
				+    " order by a.id desc, com_id desc;";
		ResultSetExtractor<List<Article>> articleResultSetExtractor = new ResultSetExtractor<List<Article>>() {
			public List<Article> extractData(ResultSet rs) throws SQLException,
            DataAccessException{
				List<Article> articleList = new ArrayList<>();
				List<Comment> commentList = null;
				int tmp = 0;
				while (rs.next()) {
					if (rs.getInt("id") != tmp) {
						tmp = rs.getInt("id");
						Article article = new Article();
						article.setId(rs.getInt("id"));
						article.setName(rs.getString("name"));
						article.setContent(rs.getString("content"));
						
						commentList = new ArrayList<>();
						article.setCommentList(commentList);
						articleList.add(article);	
						
					}
					if (rs.getInt("com_id") != 0) {
						Comment comment = new Comment();
						comment.setId(rs.getInt("com_id"));
						comment.setName(rs.getString("com_name"));
						comment.setContent(rs.getString("com_content"));
						comment.setArticleId(rs.getInt("article_id"));
						commentList.add(comment);
					}
				}
				return articleList;
			}
		};
		List<Article> articleList = template.query(sql, articleResultSetExtractor);
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
