package zzc.recruitment.service;

import zzc.recruitment.bean.User;

import java.util.List;

public interface UserService {
    User getUserById(int id);
    String getPwdById(int id);

    //登录时信息核对
    User loginIn(String userName,String pwd);

    //根据ID查询用户信息
    User queryUserById(String id);

    //根据UserName查询用户身份
    String getIdentityByUsername(String userName);

    //插入新的用户
    int addUser(User user);

    //删除用户
    int dropUser(int id);

    //修改用户
    int modifyUser(User user);

    //查询所有用户
    List<User> getAllUser();

    //根据id模糊查找
    List<User> searchId(String id);

    int getUserNum();

    int getManagerNum();

    int getBusinessNum();
}
