package zzc.recruitment.controller;


import org.springframework.web.bind.annotation.*;
import zzc.recruitment.bean.Notice;
import zzc.recruitment.service.NoticeService;
import zzc.recruitment.bean.User;
import zzc.recruitment.service.UserService;
import zzc.recruitment.bean.Userinfo;
//import zzc.recruitment.service.UserinfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
public class ManagerController {

    @Autowired
    UserService userService;
    @Autowired
    NoticeService noticeService;

   //用户管理
    @RequestMapping("/manager/muser")
    public String globalfresh(Model model,
                              HttpServletRequest request,
                              @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                              @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
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
        try {
            List<User> userinfos = userService.getAllUser();
            PageInfo<User> pageInfo = new PageInfo<User>(userinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        return "manager/muser/muser";
//        model.addAttribute("pageInfo", null);
    }
    //  添加用户页面
    @GetMapping("/manager/muser/add")
    public String toAddpage(HttpServletRequest request) {
        return "/manager/muser/adduser";
    }

    //    用户添加响应
    @PostMapping("/manager/muser/add")
    public String Adduser(User user,Model model) {
        int i=user.getId();
        if(userService.getUserById(i)!=null){
            model.addAttribute("msg","该账号已被注册！");
            return "/manager/muser/adduser";
        }
        userService.addUser(user);
        return "redirect:/manager/muser";
    }

    //    用户删除请求
    @GetMapping("/manager/muser/delete/{id}")
    public String toDelete(@PathVariable("id") Integer id,Model model) {
        userService.dropUser(id);
        return "redirect:/manager/muser";
    }

    //搜索
    @RequestMapping("/manager/muser/search")
    public String SearchId(Model model,
                              @RequestParam("searchid")String id,
                              @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                              @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
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
        try {
            List<User> userinfos = userService.searchId(id);
            PageInfo<User> pageInfo = new PageInfo<User>(userinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        model.addAttribute("last_search",id);
        return "manager/muser/searchuser";
    }

    //    用户编辑页面
    @GetMapping("/manager/muser/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "/manager/muser/edituser";
    }

    //    用户编辑响应
    @PostMapping("/manager/muser/edit")
    public String Edituser(User user,Model model) {
        userService.modifyUser(user);
        return "redirect:/manager/muser";
    }



    //公告管理
    @RequestMapping("/manager/mnotice")
    public String mnotice(Model model,
                              @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                              @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
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
        try {
            List<Notice>noticeinfos = noticeService.getAll();
            PageInfo<Notice> pageInfo = new PageInfo<Notice>(noticeinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        return "manager/mnotice/mnotice";
//        model.addAttribute("pageInfo", null);
    }

    //  添加公告页面
    @GetMapping("/manager/mnotice/add")
    public String toAddnotice() {
        return "/manager/mnotice/addnotice";
    }

    //    公告添加响应
    @PostMapping("/manager/mnotice/add")
    public String Addnotice(Notice notice,Model model) {
        noticeService.addNotice(notice);
        return "redirect:/manager/mnotice";
    }

    //    公告删除请求
    @GetMapping("/manager/mnotice/delete/{id}")
    public String toDeletenotice(@PathVariable("id") Integer id,Model model) {
        noticeService.deleteNotice(id);
        return "redirect:/manager/mnotice";
    }

    //搜索公告
    @RequestMapping("/manager/mnotice/search")
    public String Searchnotice(Model model,
                           @RequestParam("title")String title,
                           @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                           @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
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
        try {
            List<Notice> noticeinfos = noticeService.searchTitle(title);
            PageInfo<Notice> pageInfo = new PageInfo<Notice>(noticeinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        model.addAttribute("last_search",title);
        return "manager/mnotice/searchnotice";
    }

    //    公告编辑页面
    @GetMapping("/manager/mnotice/edit/{id}")
    public String toEditnotice(@PathVariable("id") Integer id, Model model) {
        Notice notice = noticeService.searchNotice(id);
        model.addAttribute("notice", notice);
        return "/manager/mnotice/editnotice";
    }

    //    公告编辑响应
    @PostMapping("/manager/mnotice/edit")
    public String Editnotice(Notice notice,Model model) {
        //String con=notice.getContent();
        //notice.setContent(con);
        noticeService.updateNotice(notice);
        return "redirect:/manager/mnotice";
    }

    //用户信息管理
    @RequestMapping("/manager/minfo")
    public String minfo(Model model,
                              HttpServletRequest request,
                              @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                              @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
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
        try {
            List<Userinfo> userinfos= new ArrayList<>();
            PageInfo<Userinfo> pageInfo = new PageInfo<Userinfo>(userinfos, pageSize);
            model.addAttribute("pageInfo",pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        return "manager/minfo/minfo";
//        model.addAttribute("pageInfo", null);
    }


    //    用户信息删除请求
    @GetMapping("/manager/minfo/delete/{id}")
    public String toDeleteinfo(@PathVariable("id") Integer id,Model model) {
        userService.dropUser(id);
        return "redirect:/manager/minfo";
    }

    //搜索
    @RequestMapping("/manager/minfo/search")
    public String SearchInfo(Model model,
                           @RequestParam("searchid")String id,
                           @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                           @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
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
        try {
            List<User> userinfos = userService.searchId(id);
            PageInfo<User> pageInfo = new PageInfo<User>(userinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        model.addAttribute("last_search",id);
        return "manager/muser/searchuser";
    }

    //    用户信息编辑页面
    @GetMapping("/manager/minfo/edit/{id}")
    public String toEditInfo(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "/manager/minfo/edituser";
    }

    //    用户编辑响应
    @PostMapping("/manager/minfo/edit")
    public String Edituserinfo(User user,Model model) {
        userService.modifyUser(user);
        return "redirect:/manager/muser";
    }

}


