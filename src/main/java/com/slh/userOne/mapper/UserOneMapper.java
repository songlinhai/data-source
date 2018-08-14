package com.slh.userOne.mapper;

import com.slh.userOne.dao.UserOne;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by songlinhai on 2018/8/13 0013.
 */
@Mapper
public interface UserOneMapper {

    @Select("SELECT * from t_user")
    List<UserOne> findOne();




}
