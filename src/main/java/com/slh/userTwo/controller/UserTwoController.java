package com.slh.userTwo.controller;

import com.slh.userTwo.mapper.UserTwoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by songlinhai on 2018/8/13 0013.
 */
@RestController
@RequestMapping("/userTwo")
public class UserTwoController {



    @Autowired
    private UserTwoMapper twoMapper;




    @RequestMapping("/two")
    public List findTwo(HttpServletRequest request, HttpServletResponse response){
        return twoMapper.findTwo();
    }

}
