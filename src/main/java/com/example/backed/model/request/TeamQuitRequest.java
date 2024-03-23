package com.example.backed.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户退出队伍请求体
 */
@Data
public class TeamQuitRequest implements Serializable {

    private static final long serialVersionUID = -2206348844031853638L;
    /**
     * id
     */
    private Long teamId;

}
