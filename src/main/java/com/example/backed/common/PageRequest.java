package com.example.backed.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用分页请求参数
 *
 * @author caocao
 */
@Data
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 3222199621276439441L;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 当前是第几页
     */
    private int pageNum;
}
