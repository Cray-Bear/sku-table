package com.example.skutable.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class FlowPageResponse<T> {

    private List<T> data;
    private PageInfo page;

}
