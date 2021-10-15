/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.articlecomment;

import java.sql.Timestamp;

/**
 *
 * @author Shang
 */
public class ArticleCommentDTO {
    private int id,
            status;
    private String articleId,
            commenter,
            content;
    private Timestamp postingDate;

    public ArticleCommentDTO() {
    }

    public ArticleCommentDTO(int id, int status, String articleId, String commenter, String content, Timestamp postingDate) {
        this.id = id;
        this.status = status;
        this.articleId = articleId;
        this.commenter = commenter;
        this.content = content;
        this.postingDate = postingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Timestamp postingDate) {
        this.postingDate = postingDate;
    }
    
    
}
