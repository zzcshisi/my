package zzc.recruitment.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzc.recruitment.bean.Position ;
import zzc.recruitment.bean.Resume;
import zzc.recruitment.mapper.PositionMapper;
import zzc.recruitment.mapper.ResumeMapper;
import zzc.recruitment.mapper.UserinfoMapper;
import zzc.recruitment.service.PositionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import cn.hutool.core.util.StrUtil;


@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    UserinfoMapper userinfoMapper;
    @Autowired
    ResumeMapper resumeMapper;

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
    public List<Position> searchPosition(String searchword,String city,String industry,String nature,String bscale,String kind,String cate, int xueli, int exp, int pleft,int pright){
        List<Position> positions=positionMapper.searchPosition(searchword,city,industry,nature,bscale,kind,cate,xueli,exp,pleft,pright);
        List<Position> tmp=positionMapper.searchPosition("",city,industry,nature,bscale,kind,cate,xueli,exp,pleft,pright);
        for(Position post:tmp){
            if(!positions.contains(post)){
                if(StrUtil.similar(searchword.toLowerCase(), post.getPname().toLowerCase())>=0.50){
                    positions.add(post);
                }
            }
        }
        return positions;
    }

    @Override
    public List<Position> getRecommend(int id,String status,String hposition,String hplace,int hleft,int hright,int xueli){
        String field[] = {"软件开发","通信/硬件","机械/制造","产品/项目/运营","金融","市场营销","咨询/管理","人力/财务/行政","教育/科研","供应链/物流","视觉/交互/设计","房地产/建筑","生物医疗"};
        String post[][]={{"后端开发","前端开发","客户端开发","测试","数据","运维/技术支持","人工智能/算法","研发工程师","技术支持"},
                {"硬件工程师","电子/半导体","通信"},
                {"机械","汽车制造","化工"},
                {"产品","运营","游戏策划","项目","用户研究"},
                {"风控", "证券类", "投融资", "银行"},
                {"市场/营销", "销售/商务", "公关媒介", "客服"},
                {"管理", "管理培训生"},
                {"财务审计", "人力资源", "行政管理", "法务"},
                {"教育产品研发", "教育", "翻译相关", "科研"},
                {"供应链", "物流"},
                {"交互/设计", "动画/特效", "工业类设计"},
                {"房地产", "设计装修与市政建设"},
                {"生物制药"}
        };
        boolean flag=false;
        String name=hposition==null?"":hposition.toLowerCase();
        for(int i=0;i<13;i++){
            if(flag){
                break;
            }
            String[] tmp=post[i];
            for(String s : tmp){
                if(s.equals(hposition)){
                    hposition=field[i];
                    flag=true;
                    break;
                }
            }
        }
        List<Position> positions=positionMapper.getRecommend(status,hposition,hplace,hleft,hright,xueli);
        Resume resume=resumeMapper.getById(id);
        if(resume==null){
            resumeMapper.add(id);
            resume=resumeMapper.getById(id);
        }
        String zhuanye= resume.getZhuanye()==null?"": resume.getZhuanye().toLowerCase();
        String[] skills={};
        if(resume.getSkills()!=null){
            skills=resume.getSkills().toLowerCase().replace("。"," ").split(",|，|/|、| ");
        }
        String selfeval=resume.getSelfeval()==null?"": resume.getSelfeval().toLowerCase();
        for(int i=0;i<positions.size();i++){
            double count=0.0;
            Position position=positions.get(i);
            String pname=position.getPname().toLowerCase();
            String intro=position.getIntro()==null?"":position.getIntro().toLowerCase();
            String tokens=position.getToken1()+position.getToken2()+position.getToken3();
            tokens=tokens==null?"":tokens.toLowerCase();
            if(name!=null&&pname!=null){
                count+=StrUtil.similar(name, pname)*20;
            }
            if(intro!=null&&selfeval!=null){
                count+=StrUtil.similar(selfeval, intro)*20;
            }
            if(intro.contains(zhuanye)||tokens.contains(zhuanye)){
                count+=20;
            }
            for(int j=0;j< skills.length;j++){
                if(intro.contains(skills[j])||tokens.contains(skills[j])){
                    count+=20/ skills.length;
                }
            }
            position.setCompareCount(count);
            positions.set(i,position);
        }
        Collections.sort(positions, new Comparator<Position>(){
            @Override
            public int compare(Position u1, Position u2) {
                double diff = u1.getCompareCount() - u2.getCompareCount();
                if (diff > 0) {
                    return -1;
                }
                else if (diff < 0) {
                    return 1;
                }
                return 0; //相等为0
            }
        });
        return positions;
    }
}
