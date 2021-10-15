/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.article;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import sangnv.utils.DBHelper;
import sangnv.utils.MyConstants;

/**
 *
 * @author Shang
 */
public class ArticleDAO implements Serializable {

    List<ArticleDTO> list;
    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;

    public void closeDB() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public List<ArticleDTO> getList() {
        return list;
    }

    public ArticleDAO() {
    }

    public int getTotalArticlePageSearchContent(String search, int numPerPage) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        int pages = 0;
        try {
            if (conn != null) {
                String sql = "SELECT COUNT(id)"
                        + " FROM Article"
                        + " WHERE [content] LIKE ?"
                        + " AND status = ?";
                stm = conn.prepareStatement(sql);
                stm.setNString(1, "%" + search + "%");
                stm.setInt(2, MyConstants.ARTICLE_STATUS_ACTIVE);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int totalArticle = rs.getInt(1);
                    if (totalArticle % numPerPage == 0) {
                        pages = totalArticle / numPerPage;
                    } else {
                        pages = totalArticle / numPerPage + 1;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return pages;
    }

    public int getTotalArticlePageSearchContentByAdmin(String search, int status, int numPerPage) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        int pages = 0;
        try {
            if (conn != null) {
                String sql = "SELECT COUNT(id)"
                        + " FROM Article"
                        + " WHERE [content] LIKE ?"
                        + " AND status = ?";
                stm = conn.prepareStatement(sql);
                stm.setNString(1, "%" + search + "%");
                stm.setInt(2, status);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int totalArticle = rs.getInt(1);
                    if (totalArticle % numPerPage == 0) {
                        pages = totalArticle / numPerPage;
                    } else {
                        pages = totalArticle / numPerPage + 1;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return pages;
    }

    public int getTotalArticlePageSearchNameByAdmin(String search, int status, int numPerPage) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        int pages = 0;
        try {
            if (conn != null) {
                String sql = "SELECT COUNT(id)"
                        + " FROM Article"
                        + " WHERE title LIKE ?"
                        + " AND status = ?";
                stm = conn.prepareStatement(sql);
                stm.setNString(1, "%" + search + "%");
                stm.setInt(2, status);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int totalArticle = rs.getInt(1);
                    if (totalArticle % numPerPage == 0) {
                        pages = totalArticle / numPerPage;
                    } else {
                        pages = totalArticle / numPerPage + 1;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return pages;
    }

    public void getArticlesOnContent(int start, int totalEachPage, String search) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        try {
            if (conn != null) {
                String sql = "SELECT ar.id, ar.title, ar.description, ar.author, ar.postingDate, ac.name"
                        + " FROM Article ar"
                        + " INNER JOIN Account ac ON ar.author = ac.email"
                        + " WHERE ar.[content] LIKE ?"
                        + " AND ar.status = ?"
                        + " ORDER BY ar.postingDate DESC"
                        + " OFFSET ? ROWS"
                        + " FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setNString(1, "%" + search + "%");
                stm.setInt(2, MyConstants.ARTICLE_STATUS_ACTIVE);
                stm.setInt(3, start);
                stm.setInt(4, totalEachPage);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ArticleDTO dto = new ArticleDTO();
                    dto.setId(rs.getString("id"));
                    dto.setTitle(rs.getString("title"));
                    dto.setDescription(rs.getString("description"));
                    dto.setAuthor(rs.getString("name") + " (" + rs.getString("author") + ")");
                    dto.setPostingDate(rs.getTimestamp("postingDate"));
                    list.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getArticlesOnContentByAdmin(int start, int totalEachPage, String search, int status) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        try {
            if (conn != null) {
                String sql = "SELECT ar.id, ar.title, ar.description, ar.author, ar.postingDate, ac.name, ar.status"
                        + " FROM Article ar"
                        + " INNER JOIN Account ac ON ar.author = ac.email"
                        + " WHERE ar.[content] LIKE ?"
                        + " AND ar.status = ?"
                        + " ORDER BY ar.postingDate DESC"
                        + " OFFSET ? ROWS"
                        + " FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setNString(1, "%" + search + "%");
                stm.setInt(2, status);
                stm.setInt(3, start);
                stm.setInt(4, totalEachPage);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ArticleDTO dto = new ArticleDTO();
                    dto.setId(rs.getString("id"));
                    dto.setTitle(rs.getString("title"));
                    dto.setDescription(rs.getString("description"));
                    dto.setAuthor(rs.getString("name") + " (" + rs.getString("author") + ")");
                    dto.setPostingDate(rs.getTimestamp("postingDate"));
                    dto.setStatus(rs.getInt("status"));
                    list.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getArticlesOnNameByAdmin(int start, int totalEachPage, String search, int status) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        try {
            if (conn != null) {
                String sql = "SELECT ar.id, ar.title, ar.description, ar.author, ar.postingDate, ac.name, ar.status"
                        + " FROM Article ar"
                        + " INNER JOIN Account ac ON ar.author = ac.email"
                        + " WHERE ar.title LIKE ?"
                        + " AND ar.status = ?"
                        + " ORDER BY ar.postingDate DESC"
                        + " OFFSET ? ROWS"
                        + " FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setNString(1, "%" + search + "%");
                stm.setInt(2, status);
                stm.setInt(3, start);
                stm.setInt(4, totalEachPage);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ArticleDTO dto = new ArticleDTO();
                    dto.setId(rs.getString("id"));
                    dto.setTitle(rs.getString("title"));
                    dto.setDescription(rs.getString("description"));
                    dto.setAuthor(rs.getString("name") + " (" + rs.getString("author") + ")");
                    dto.setPostingDate(rs.getTimestamp("postingDate"));
                    dto.setStatus(rs.getInt("status"));
                    list.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public ArticleDTO findArticleOnId(String articleId) throws NamingException, SQLException {
        ArticleDTO dto = null;
        conn = DBHelper.getConnect();
        try {
            String sql = "SELECT ar.id, ar.title, ar.description, ar.[content], ar.author, ar.postingDate, ac.name"
                    + " FROM Article ar"
                    + " INNER JOIN Account ac ON ar.author = ac.email"
                    + " WHERE ar.id = ?"
                    + " AND ar.status = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, articleId);
            stm.setInt(2, MyConstants.ARTICLE_STATUS_ACTIVE);
            rs = stm.executeQuery();
            if (rs.next()) {
                dto = new ArticleDTO();
                dto.setId(rs.getString("id"));
                dto.setTitle(rs.getString("title"));
                dto.setDescription(rs.getString("description"));
                dto.setContent(rs.getString("content"));
                dto.setAuthor(rs.getString("name") + " (" + rs.getString("author") + ")");
                dto.setPostingDate(rs.getTimestamp("postingDate"));
            }
        } finally {
            closeDB();
        }
        return dto;
    }

    public ArticleDTO findArticleOnIdByAdmin(String articleId) throws NamingException, SQLException {
        ArticleDTO dto = null;
        conn = DBHelper.getConnect();
        try {
            String sql = "SELECT ar.id, ar.title, ar.description, ar.[content], ar.author, ar.postingDate, ac.name, ar.status"
                    + " FROM Article ar"
                    + " INNER JOIN Account ac ON ar.author = ac.email"
                    + " WHERE ar.id = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, articleId);
            rs = stm.executeQuery();
            if (rs.next()) {
                dto = new ArticleDTO();
                dto.setId(rs.getString("id"));
                dto.setTitle(rs.getString("title"));
                dto.setDescription(rs.getString("description"));
                dto.setContent(rs.getString("content"));
                dto.setAuthor(rs.getString("name") + " (" + rs.getString("author") + ")");
                dto.setPostingDate(rs.getTimestamp("postingDate"));
                dto.setStatus(rs.getInt("status"));
            }
        } finally {
            closeDB();
        }
        return dto;
    }

    public boolean postArticle(ArticleDTO dto) throws NamingException, SQLException {
        boolean result = false;
        conn = DBHelper.getConnect();
        try {
            String sql = "INSERT INTO Article (id, title, description, content, postingDate, author, status)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            stm = conn.prepareStatement(sql);
            long currentTimeMillis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(currentTimeMillis);
            stm.setString(1, dto.getId());
            stm.setString(2, dto.getTitle());
            stm.setString(3, dto.getDescription());
            stm.setString(4, dto.getContent());
            stm.setTimestamp(5, timestamp);
            stm.setString(6, dto.getAuthor());
            stm.setInt(7, MyConstants.ARTICLE_STATUS_NEW);
            int insertCount = stm.executeUpdate();
            if (insertCount > 0) {
                result = true;
            }
        } finally {
            closeDB();
        }
        return result;
    }

    public boolean approveArticle(String articleId) throws NamingException, SQLException {
        boolean result = false;
        conn = DBHelper.getConnect();
        try {
            String sql = "UPDATE Article"
                    + " SET status = ?"
                    + " WHERE id = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, MyConstants.ARTICLE_STATUS_ACTIVE);
            stm.setString(2, articleId);
            int updateCount = stm.executeUpdate();
            if (updateCount > 0) {
                result = true;
            }
        } finally {
            closeDB();

        }
        return result;
    }
    
    public boolean deleteArticle(String articleId) throws NamingException, SQLException {
        boolean result = false;
        conn = DBHelper.getConnect();
        try {
            String sql = "UPDATE Article"
                    + " SET status = ?"
                    + " WHERE id = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, MyConstants.ARTICLE_STATUS_DELETED);
            stm.setString(2, articleId);
            int updateCount = stm.executeUpdate();
            if (updateCount > 0) {
                result = true;
            }
        } finally {
            closeDB();

        }
        return result;
    }

}
