package zzc.recruitment.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzc.recruitment.bean.Resume;
import zzc.recruitment.mapper.ResumeMapper;
import zzc.recruitment.service.ResumeService;


@Service
public class ResumeServiceImpl implements ResumeService{
    @Autowired
    ResumeMapper resumeMapper;
    //插入
    @Override
    public int add(int id)
    {
        if(resumeMapper.getById(id)==null){
            return resumeMapper.add(id);
        }
        else{
            return 0;
        }
    }

    //删除
    @Override
    public int delete(int id){
        return resumeMapper.delete(id);
    }

    //修改
    @Override
    public int update(Resume resume){
        return resumeMapper.update(resume);
    }
    @Override
    public int updateAvatar(String avatar,int id){
        return resumeMapper.updateAvatar(avatar,id);
    }

    //查询
    @Override
    public Resume getById(int id){
        return resumeMapper.getById(id);
    }
}
