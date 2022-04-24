package zzc.recruitment.mapper;
import zzc.recruitment.bean.Resume;
import zzc.recruitment.bean.Record;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RecordMapper {
    //插入
    int addRecord(int uid,int pid);
    int addRecord_resume(Resume resume);

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
