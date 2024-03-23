package com.example.backed.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 队伍加入请求体
 */
@Data
public class TeamJoinRequest implements Serializable {

    private static final long serialVersionUID = -8021545786503633448L;
    /**
     * id
     */
    private Long teamId;
    /**
     * 密码
     */
    private String password;

}
