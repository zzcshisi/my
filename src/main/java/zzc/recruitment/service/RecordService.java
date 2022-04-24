package zzc.recruitment.service;

import zzc.recruitment.bean.Record;
import zzc.recruitment.bean.Resume;

import java.util.List;

public interface RecordService {
    //插入
    int addRecord(int uid,int pid);

    //删除
    int delete(int id);

    //修改
    int updateStatus(int id,String status);

    //查询
    int getStatusNum(int bid,String status);
    List<Record> getRecordByUid(int uid);
    List<Record> getRecordByBid(int bid,String status);
    List<Record> getRecordByPid(int pid,String status);
    Record getRecordByUidPid(int uid,int pid);
    Record getRecordById(int id);
    Resume getResumeById(int id);
}
