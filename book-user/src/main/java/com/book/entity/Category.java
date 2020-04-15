package com.book.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Category implements Serializable {

    private static final long serialVersionUID = -14828L;

    private Integer id;//父节点
    
    private Integer parentId;

    private String name;
    
    private Integer isParent;

    private Date updated;
    
    private List<Category> twoCategory;//子节点
    
    private List<Category> threeCategory;//孙节点
    
}
