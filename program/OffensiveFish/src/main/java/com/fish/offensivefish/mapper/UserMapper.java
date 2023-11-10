package com.fish.offensivefish.mapper;

import com.fish.offensivefish.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public void insertUser(User user);
    public void updateUserPasswd(String userName,String passwd);
    public User selectUser(String userName);
    public void updateStatus(int status,String userName);
}
