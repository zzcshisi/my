package zzc.recruitment.mapper;
import zzc.recruitment.bean.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResumeMapper {
    //插入新的用户
    int add(int id);

    //删除用户
    int delete(int id);

    //修改用户
    int update(Resume resume);
    int updateAvatar(String avatar,int id);

    //查询
    Resume getById(int id);
}
