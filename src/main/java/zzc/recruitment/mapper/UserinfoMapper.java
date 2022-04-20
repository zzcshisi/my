package zzc.recruitment.mapper;

import zzc.recruitment.bean.Userinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserinfoMapper {

    //插入新的用户
    int addUser(Userinfo userinfoBean);

    //删除用户
    int deleteUser(int id);

    //修改用户
    int updateUser(Userinfo userinfoBean);
    int updateAvatar(String avatar,int id);

    //查询所有用户
    List<Userinfo> getAllUser();

    List<Userinfo> SelectUser(int xueli,String zhuanye,String status);

    Userinfo getById(int id);

    //根据id模糊查找
    List<Userinfo> searchId(String id);

}

