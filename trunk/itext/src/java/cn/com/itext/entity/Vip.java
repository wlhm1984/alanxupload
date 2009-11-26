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
import javax.persistence.OneToMany;

/**
 *
 * @author alan.xiao
 */
@Entity
public class Vip implements Serializable {
   

    private static final long serialVersionUID = 1L;

    /**
     * 新建
     */
    private static final Integer STATUS_DRAFT = 0;

    /**
     * 激活
     */
    private static final Integer STATUS_PUBLISHED = 1;

    /**
     * 已删除
     */
    private static final Integer STATUS_DELETED = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String userName;
    private String gender;
    private String email;
    private String qq;
    private String msn;
    private String mobile;
    private String telephone;
    private Integer status;
    private Integer vipLevel;
    private String createBy;
    private Date createDate;
    private String modifyBy;
    private Date modifyDate;
 @OneToMany(mappedBy = "publisher")
    private List<ArticleChapter> articleChapters;
    @OneToMany(mappedBy = "publisher")
    private List<Article> articles;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        if (!(object instanceof Vip)) {
            return false;
        }
        Vip other = (Vip) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.com.itext.entity.Member[id=" + id + "]";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public List<ArticleChapter> getArticleChapters() {
        return articleChapters;
    }

    public void setArticleChapters(List<ArticleChapter> articleChapters) {
        this.articleChapters = articleChapters;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
