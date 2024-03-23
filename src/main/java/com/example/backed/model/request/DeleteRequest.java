package com.example.backed.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {
    private static final long serialVersionUID = -2893529461801931116L;
    private long id;
}

