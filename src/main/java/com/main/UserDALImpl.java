package com.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDALImpl implements UserDAL{
    @Autowired
    private MongoTemplate mt;
    @Override
    public List<User> getAllUsers() {
        return mt.findAll(User.class);
    }

    @Override
    public void addNewUser(User user) {
        mt.save(user);
    }

    @Override
    public void deleteAll() {
        mt.getDb().drop();
    }

    @Override
    public List<User> getSpecificUsers(Integer occupation, Integer age, String gender) {
        Query query = new Query();
        if(occupation!=-1)
            query.addCriteria(Criteria.where("occupation").is(occupation));
        if(!gender.equals(""))
            query.addCriteria(Criteria.where("gender").is(gender));
        if(age!=-1)
            query.addCriteria(Criteria.where("age").is(age));
        return mt.find(query,User.class);
    }
}
