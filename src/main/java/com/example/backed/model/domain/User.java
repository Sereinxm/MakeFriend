package com.example.backed.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户名
     */
    private String username;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private long id;


    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 标签列表tags
     */
    private String tags;

    /**
     * 状态 0-正常
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;
    /**
     * 用户角色 0-普通用户 1-管理员
     */
    private Integer userRole;
    /**
     * 编号
     */
    private String planetCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}