package com.example.demo.pojo;

import lombok.Data;

@Data
public class AgeAggregation {

    private Integer age;
    private Integer max;
    private Integer min;
    private Integer sum;
    private Integer count;
    private Double avg;
    private Double stdDevPop;
    private Double stdDevSamp;
}
