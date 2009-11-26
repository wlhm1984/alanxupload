/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.itext.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author alan.xiao
 */
@Entity
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String MANAGER_SYSTEM = "system";
    private static final String MANAGER_AMDIN = "admin";
    private static final String MANAGER_VIP = "vip";
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
    private Integer id;
    private String name;
    private String discription;
    private Integer status;

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
        if (!(object instanceof Manager)) {
            return false;
        }
        Manager other = (Manager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.com.itext.entity.Manager[id=" + id + "]";
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
