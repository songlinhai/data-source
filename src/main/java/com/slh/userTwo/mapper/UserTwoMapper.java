package com.slh.userTwo.mapper;


import com.slh.userTwo.dao.UserTwo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by songlinhai on 2018/8/13 0013.
 */
@Mapper
public interface UserTwoMapper {


    @Select("SELECT * from t_user")
    List<UserTwo> findTwo();
}
