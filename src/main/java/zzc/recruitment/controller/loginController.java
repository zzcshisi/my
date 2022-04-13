package zzc.recruitment.controller;


import org.springframework.web.bind.annotation.ResponseBody;
import zzc.recruitment.bean.User;
import zzc.recruitment.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
public class loginController {

    @Autowired
    UserService userService;
    @PostMapping("/user/login")
    public String login(@RequestParam("username")String username, @RequestParam("password")String password, @RequestParam("pass")String pass,Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserById(Integer.parseInt(username));
        if(user!=null&&!StringUtils.isEmpty(username)&&user.getPwd().equals(password)) {
            //System.out.println(pass);
            if (pass != null && !pass.equals("nopass")) {

                Cookie cookie_username = new Cookie("cookie_username", username);
                // 设置cookie的持久化时间，30天
                cookie_username.setMaxAge(30 * 24 * 60 * 60);
                // 设置为当前项目下都携带这个cookie
                cookie_username.setPath("/");
                // 向客户端发送cookie
                response.addCookie(cookie_username);
                System.out.println("成功设置cookie");
            }
            session.setAttribute("username", username);
            session.setAttribute("identity", user.getIdentity());
//            Coolie
            if (user.getIdentity().equals("0")) {
                System.out.print(user.getIdentity());
                return "redirect:/index";
            } else if (user.getIdentity().equals("1")) {
                return "redirect:/business/info";
            } else {
                return "redirect:/manager/mindex";
            }
        }
        else{
            model.addAttribute("msg","用户名或密码错误！");
            return "login";
        }
    }
    @RequestMapping("/user/register")
    public String register(@RequestParam("id")String id,@RequestParam("username")String username, @RequestParam("password")String password,@RequestParam("password1")String password1,@RequestParam("identity")String identity,
                             Model model) {
        boolean is_matcher = id != null && id.matches("[0-9]+");
        if(!is_matcher){
            model.addAttribute("msg","请输入数字作为账号！");
            return "register";
        }
        is_matcher = password != null && password.matches("[A-Za-z0-9]+");
        if(!is_matcher){
            model.addAttribute("msg","请输入数字或字母作为密码！");
            return "register";
        }
        if (!password.equals(password1)){
            model.addAttribute("msg","两次输入的密码不一致！");
            return "register";
        }
        int i=Integer.parseInt(id);
        if(userService.getUserById(i)!=null){
            model.addAttribute("msg","该账号已被注册！");
            return "register";
        }
        User user=new User();
        user.setId(i);
        user.setUserName(username);
        user.setPwd(password);
        user.setIdentity(identity);
        userService.addUser(user);
        model.addAttribute("msg","注册成功，请登录！");
        model.addAttribute("username",i);
        return "login";
    }
    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session,HttpServletResponse response) {
        // 删除session里面的用户信息
        session.removeAttribute("username");
        session.removeAttribute("identity");
        // 保存cookie，实现自动登录
        Cookie cookie_username = new Cookie("cookie_username", "");
        // 设置cookie的持久化时间，0
        cookie_username.setMaxAge(0);
        // 设置为当前项目下都携带这个cookie
        cookie_username.setPath("/");
        // 向客户端发送cookie
        response.addCookie(cookie_username);
        return "redirect:login";
    }

    @RequestMapping("/user/passwd")
    public String passwd( Model model,HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();       // 获取登录信息
            Object obj = session.getAttribute("username");
            if (obj == null) {     // 登录信息为 null，表示没有登录
                model.addAttribute("id", null);
                return "editpasswd";
            }
            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            User user=userService.getUserById(loginId);
            String name=user.getUserName();
            model.addAttribute("id", loginId);
            return "editpasswd";
        } finally {
            PageHelper.clearPage();
        }
    }
    @PostMapping("/user/passwd/edit")
    public String editpasswd(@RequestParam("id") int id,
                          @RequestParam("pwd") String pwd,
                          @RequestParam("npwd")String npwd,
                          Model model) {
        int flag = 0;
        User user=userService.getUserById(id);
        if(user.getPwd().equals(pwd)){
            flag=1;
            user.setPwd(npwd);
        }
        userService.modifyUser(user);
        if(flag == 1){
            model.addAttribute("msg","修改密码成功");
        }else {
            model.addAttribute("msg","修改密码失败");
        }
        model.addAttribute("id", id);
        return "editpasswd";
    }

    @RequestMapping("/contact")
    public String contact(){
        return "contact";
    }
}

