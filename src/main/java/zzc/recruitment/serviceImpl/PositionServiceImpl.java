package zzc.recruitment.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzc.recruitment.bean.Position ;
import zzc.recruitment.mapper.PositionMapper;
import zzc.recruitment.service.PositionService;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionMapper positionMapper;

    @Override
    public int addPosition(Position position){
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        position.setReleasetime(formatter.format(date));
        return positionMapper.addPosition(position);
    }

    @Override
    public int deleteByPid(int pid){
        return positionMapper.deleteByPid(pid);
    }

    @Override
    public int deleteByBid(int bid){
        return positionMapper.deleteByBid(bid);
    }

    @Override
    public int updatePosition(Position position){
        if(position.getReleasetime()==null||position.getReleasetime().replace(" ","")==""){
            Date date=new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            position.setReleasetime(formatter.format(date));
        }
        return  positionMapper.updatePosition(position);
    }

    @Override
    public List<Position> getAll(){
        return positionMapper.getAll();
    }

    @Override
    public Position getByPid(int pid){
        return positionMapper.getByPid(pid);
    }

    @Override
    public List<Position> getByBid(int bid){
        return positionMapper.getByBid(bid);
    }

    @Override
    public List<Position> getByPname(String pname,int bid){
        return positionMapper.getByPname(pname,bid);
    }

    @Override
    public List<Position> searchPosition(String searchword,Position position){
        return positionMapper.searchPosition(searchword,position);
    }
}
