package zzc.recruitment.controller;

import org.springframework.web.bind.annotation.*;

import zzc.recruitment.service.UserService;
import zzc.recruitment.bean.Businessinfo;
import zzc.recruitment.service.BusinessinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class BusinessController {

    @Autowired
    UserService userService;
    @Autowired
    BusinessinfoService businessinfoService;

    @RequestMapping("/business/info")
    public String businessinfo(HttpServletRequest request,Model model){
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);                // 强制转换成 String
        Businessinfo businessinfo=businessinfoService.getById(id);
        if(businessinfo==null){
            businessinfo= new Businessinfo();
            businessinfo.setId(id);
            businessinfoService.addBusinessinfo(businessinfo);
        }
        model.addAttribute("businessinfo",businessinfo);
        return "/business/binfo/businessinfo";
    }

    @GetMapping("/business/info/edit")
    public String editbusinessinfo(HttpServletRequest request,Model model){
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);                // 强制转换成 String
        Businessinfo businessinfo=businessinfoService.getById(id);
        if(businessinfo==null){
            businessinfo= new Businessinfo();
            businessinfo.setId(id);
            businessinfoService.addBusinessinfo(businessinfo);
        }
        model.addAttribute("businessinfo",businessinfo);
        return "/business/binfo/editbusinessinfo";
    }
}
