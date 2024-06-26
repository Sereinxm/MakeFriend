package com.example.backed.model.request;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author cao
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -4032455263587934947L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;


}
