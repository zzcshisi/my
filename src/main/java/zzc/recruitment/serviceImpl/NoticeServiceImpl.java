package zzc.recruitment.serviceImpl;

import zzc.recruitment.bean.Notice;
import zzc.recruitment.mapper.NoticeMapper;
import zzc.recruitment.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    //    NoticeMapper
    @Autowired
    private NoticeMapper NoticeMapper;


    @Override
    public int addNotice(Notice noticeBean){
        return NoticeMapper.addNotice(noticeBean);
    }


    @Override
    public int deleteNotice(int id){
        return NoticeMapper.deleteNotice(id);
    }


    @Override
    public Notice searchNotice(int id){
        return NoticeMapper.searchNotice(id);
    }


    //获取所有信息
    @Override
    public List<Notice> getAll(){
        return NoticeMapper.getAll();
    }

    @Override
    public int updateNotice(Notice noticeBean){return NoticeMapper.updateNotice(noticeBean);}

    @Override
    public List<Notice> getCarousel(){return NoticeMapper.getCarousel();}

    @Override
    public List<Notice> searchTitle(String title){
        return NoticeMapper.searchTitle(title);
    }
}
