package zzc.recruitment.controller;


import org.springframework.web.bind.annotation.*;

import zzc.recruitment.bean.User;
import zzc.recruitment.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/user/info")
    public String userinfo(){
        return "/user/userinfo";
    }
}
