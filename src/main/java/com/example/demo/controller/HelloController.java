package com.example.demo.controller;

import com.example.demo.pojo.AgeAggregation;
import com.example.demo.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/get")
    public List<Map> get(){
        Criteria criteria=Criteria.where("age").gte(18);
        GroupOperation groupOperation1=Aggregation.group("age")
                .max("age").as("max")
                .min("age").as("min")
                .sum("age").as("sum")
                .count().as("count")
                .avg("age").as("avg")
                .stdDevPop("age").as("stdDevPop")
                .stdDevSamp("age").as("stdDevSamp");

        ProjectionOperation projectionOperation=new ProjectionOperation()
                .andExclude("_id")
                .and("_id").as("age")
                .andInclude("max","min","sum","count","avg","stdDevPop","stdDevSamp");

        Aggregation aggregation=Aggregation.newAggregation(Aggregation.match(criteria),groupOperation1,projectionOperation);
        AggregationResults<Map> results=mongoTemplate.aggregate(aggregation, "person",Map.class);

        return results.getMappedResults();
    }

    @RequestMapping("/get2")
    public List<Map> get2(){
        Criteria criteria=Criteria.where("age").gte(18);
        ProjectionOperation projectionOperation=new ProjectionOperation().andInclude("age","name").and("age").as("age2");

        Aggregation aggregation=Aggregation.newAggregation(Aggregation.match(criteria),projectionOperation);
        AggregationResults<Map> results=mongoTemplate.aggregate(aggregation, "person",Map.class);

        return results.getMappedResults();
    }

    @RequestMapping("/get3")
    public List<AgeAggregation> get3(){
        Criteria criteria=Criteria.where("age").gte(18);
        GroupOperation groupOperation1=Aggregation.group("age")
                .max("age").as("max")
                .min("age").as("min")
                .sum("age").as("sum")
                .count().as("count")
                .avg("age").as("avg")
                .stdDevPop("age").as("stdDevPop")
                .stdDevSamp("age").as("stdDevSamp");

        ProjectionOperation projectionOperation=new ProjectionOperation()
                .andExclude("_id")
                .and("_id").as("age")
                .andInclude("max","min","sum","count","avg","stdDevPop","stdDevSamp");

        Aggregation aggregation=Aggregation.newAggregation(Aggregation.match(criteria),groupOperation1,projectionOperation);
        AggregationResults<AgeAggregation> results=mongoTemplate.aggregate(aggregation, "person",AgeAggregation.class);

        return results.getMappedResults();
    }

    @RequestMapping("/getAll")
    public List<Person> getAll(){
        return mongoTemplate.findAll(Person.class);
    }
}
