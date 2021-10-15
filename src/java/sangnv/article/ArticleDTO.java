/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.article;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Shang
 */
public class ArticleDTO implements Serializable {
    private String id,
            title,
            description,
            content,
            author;
    private Timestamp postingDate;
    private int status;

    public ArticleDTO() {
    }

    public ArticleDTO(String id, String title, String description, String content, String author, Timestamp postingDate, int status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.author = author;
        this.postingDate = postingDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Timestamp postingDate) {
        this.postingDate = postingDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
