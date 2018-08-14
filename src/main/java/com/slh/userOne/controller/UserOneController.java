package com.slh.userOne.controller;


import com.slh.userOne.mapper.UserOneMapper;
import com.slh.userTwo.mapper.UserTwoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by songlinhai on 2018/8/13 0013.
 */
@RestController
@RequestMapping("/userOne")
public class UserOneController {

    @Autowired
    private UserOneMapper oneMapper;

    @Autowired
    private UserTwoMapper twoMapper;


    @RequestMapping("/one")
    public List findTwo(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("two=============="+twoMapper.findTwo());
        System.out.println("one=============="+oneMapper.findOne());
        List list = new ArrayList<>();
        list.add(twoMapper.findTwo());
        list.add(oneMapper.findOne());
        return list;
    }

}
