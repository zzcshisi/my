package zzc.recruitment.mapper;
import zzc.recruitment.bean.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResumeMapper {
    //插入
    int add(int id);

    //删除
    int delete(int id);

    //修改
    int update(Resume resume);
    int updateAvatar(String avatar,int id);

    //查询
    Resume getById(int id);
}
