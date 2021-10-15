/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.articlecomment;

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
public class ArticleCommentDAO implements Serializable {

    List<ArticleCommentDTO> list;
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

    public ArticleCommentDAO() {
    }

    public List<ArticleCommentDTO> getList() {
        return list;
    }

    public int getTotalArticleCommentPageSearchId(String articleId, int numPerPage) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        int pages = 0;
        try {
            if (conn != null) {
                String sql = "SELECT COUNT(id)"
                        + " FROM ArticleComment"
                        + " WHERE articleId LIKE ?"
                        + " AND status = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, articleId);
                stm.setInt(2, MyConstants.COMMENT_STATUS_ACTIVE);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int totalComment = rs.getInt(1);
                    if (totalComment % numPerPage == 0) {
                        pages = totalComment / numPerPage;
                    } else {
                        pages = totalComment / numPerPage + 1;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return pages;
    }

    public void getArticleCommentOnId(int start, int totalEachPage, String articleId) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        try {
            if (conn != null) {
                String sql = "SELECT c.id, c.articleId, c.commenter, c.postingDate, c.[content], a.name"
                        + " FROM ArticleComment c"
                        + " INNER JOIN Account a ON c.commenter = a.email"
                        + " WHERE c.articleId LIKE ?"
                        + " AND c.status = ?"
                        + " ORDER BY c.postingDate DESC"
                        + " OFFSET ? ROWS"
                        + " FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setString(1, articleId);
                stm.setInt(2, MyConstants.COMMENT_STATUS_ACTIVE);
                stm.setInt(3, start);
                stm.setInt(4, totalEachPage);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ArticleCommentDTO dto = new ArticleCommentDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setArticleId(rs.getString("articleId"));
                    dto.setCommenter(rs.getString("name") + " (" + rs.getString("commenter") + ")");
                    dto.setPostingDate(rs.getTimestamp("postingDate"));
                    dto.setContent(rs.getString("content"));
                    list.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public boolean postComment(ArticleCommentDTO dto) throws NamingException, SQLException {
        boolean result = false;
        conn = DBHelper.getConnect();
        try {
            String sql = "INSERT INTO ArticleComment (articleId, commenter, postingDate, status, content)"
                    + " VALUES (?, ?, ?, ?, ?)";
            stm = conn.prepareStatement(sql);
            long currentTimeMillis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(currentTimeMillis);
            stm.setString(1, dto.getArticleId());
            stm.setString(2, dto.getCommenter());
            stm.setTimestamp(3, timestamp);
            stm.setInt(4, MyConstants.COMMENT_STATUS_ACTIVE);
            stm.setNString(5, dto.getContent());
            int insertCount = stm.executeUpdate();
            if (insertCount > 0) {
                result = true;
            }
        } finally {
            closeDB();
        }
        return result;
    }

}
