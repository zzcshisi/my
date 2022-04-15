package zzc.recruitment.controller;


import org.springframework.web.bind.annotation.*;

import zzc.recruitment.bean.Notice;
import zzc.recruitment.service.NoticeService;
import zzc.recruitment.bean.User;
import zzc.recruitment.service.UserService;
import zzc.recruitment.bean.Userinfo;
import zzc.recruitment.service.UserinfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserinfoService userinfoService;
    @Autowired
    NoticeService noticeService;

    @RequestMapping("/index")
    public String userindex(Model model,
                            HttpServletRequest request,
                            @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                            @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){
        if (pageNum == null) {
            pageNum = 1;   //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;    //设置默认每页显示的数据数
        }
        PageHelper.startPage(pageNum, pageSize);//定位显示页
        List<Notice> notices = noticeService.getAll();
        PageInfo<Notice> pageInfo = new PageInfo<Notice>(notices, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            model.addAttribute("username", "未登录");
            model.addAttribute("avatar", "user.jpg");
        }
        else{
            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            User user=userService.getUserById(loginId);
            model.addAttribute("username", user.getUserName());
            model.addAttribute("avatar", userinfoService.getById(loginId).getAvatar());
        }

        return "/index";
    }

    @RequestMapping("/notice")
    public String notice( @RequestParam("id") int id,
                          Model model){
        model.addAttribute("notice", noticeService.searchNotice(id));
        return "/notice";
    }

    @RequestMapping("/user/info")
    public String userinfo(){
        return "/user/userinfo";
    }

    @RequestMapping("/user/search")
    public String usersearch(){
        return "/user/userinfo";
    }
}
