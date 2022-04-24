package zzc.recruitment.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzc.recruitment.bean.Record;
import zzc.recruitment.bean.Resume;
import zzc.recruitment.mapper.RecordMapper;
import zzc.recruitment.mapper.ResumeMapper;
import zzc.recruitment.service.RecordService;
import zzc.recruitment.service.ResumeService;

import java.util.List;


@Service
public class RecordServiceImpl implements RecordService{
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    ResumeMapper resumeMapper;

    //插入
    @Override
    public int addRecord(int uid,int pid){
        int i=recordMapper.addRecord(uid,pid);
        Resume resume=resumeMapper.getById(uid);
        int id=recordMapper.getRecordByUidPid(uid,pid).getId();
        resume.setId(id);
        recordMapper.addRecord_resume(resume);
        return i;
    }

    //删除
    @Override
    public int delete(int id){
        return recordMapper.delete(id);
    }

    //修改
    @Override
    public int updateStatus(int id,String status){
        return recordMapper.updateStatus(id,status);
    }

    //查询
    @Override
    public int getStatusNum(int bid,String status){
        return recordMapper.getStatusNum(bid,status);
    }
    @Override
    public List<Record> getRecordByUid(int uid){
        return recordMapper.getRecordByUid(uid);
    }
    @Override
    public List<Record> getRecordByBid(int bid,String status){
        return recordMapper.getRecordByBid(bid,status);
    }
    @Override
    public List<Record> getRecordByPid(int pid,String status){
        return recordMapper.getRecordByPid(pid,status);
    }
    @Override
    public Record getRecordById(int id){
        return recordMapper.getRecordById(id);
    }
    @Override
    public Record getRecordByUidPid(int uid,int pid){
        return recordMapper.getRecordByUidPid(uid,pid);
    }
    @Override
    public Resume getResumeById(int id){
        return recordMapper.getResumeById(id);
    }
}
