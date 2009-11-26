/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.itext.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author alan.xiao
 */
@Entity
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 草稿
     */
    private static final Integer STATUS_DRAFT = 0;
    /**
     * 以发布
     */
    private static final Integer STATUS_PUBLISHED = 1;
    /**
     * 已删除
     */
    private static final Integer STATUS_DELETED = -1;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * 文章所属类别
     */
    @ManyToMany(mappedBy = "articles")
    private List<ArticleCategory> categorys;
    /**
     * 文章章节
     */
    @OneToMany(mappedBy = "article")
    private List<ArticleChapter> chapters;
    private String author;
    private String summary;
    /**
     * 发布者
     */
    @ManyToOne
    private Vip publisher;
    private Date publishDate;
    /**
     * status = 0：草稿 1：发布 -1：删除
     */
    private Integer status;
    private String createBy;
    private Date createDate;
    private String modifyBy;
    private Date modifyDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.com.itext.entity.Text[id=" + id + "]";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<ArticleCategory> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<ArticleCategory> categorys) {
        this.categorys = categorys;
    }

    public List<ArticleChapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<ArticleChapter> chapters) {
        this.chapters = chapters;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Vip getPublisher() {
        return publisher;
    }

    public void setPublisher(Vip publisher) {
        this.publisher = publisher;
    }
}
