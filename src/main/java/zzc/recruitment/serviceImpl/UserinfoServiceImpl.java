package zzc.recruitment.serviceImpl;

import zzc.recruitment.bean.Userinfo;
import zzc.recruitment.mapper.UserinfoMapper;
import zzc.recruitment.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserinfoServiceImpl implements UserinfoService {

    //    UserinfoMapper
    @Autowired
    private UserinfoMapper userinfoMapper;


    //插入新的用户
    @Override
    public int addUser(Userinfo userinfoBean){return userinfoMapper.addUser(userinfoBean);}

    //删除用户
    @Override
    public int deleteUser(int id){return userinfoMapper.deleteUser(id);}

    //修改用户
    @Override
    public int updateUser(Userinfo userinfoBean){return userinfoMapper.updateUser(userinfoBean);}

    @Override
    public int updateAvatar(String avatar,int id){return userinfoMapper.updateAvatar(avatar,id);}

    //查询所有用户
    @Override
    public List<Userinfo> getAllUser(){return userinfoMapper.getAllUser();}

    @Override
    public Userinfo getById(int id){return userinfoMapper.getById(id);}

    //根据id模糊查找
    @Override
    public List<Userinfo> searchId(String id){return userinfoMapper.searchId(id);}

}
