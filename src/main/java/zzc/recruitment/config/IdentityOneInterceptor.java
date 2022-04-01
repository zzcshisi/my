package zzc.recruitment.config;

import zzc.recruitment.bean.User;
import zzc.recruitment.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IdentityOneInterceptor implements HandlerInterceptor{
    UserService userService;
    public IdentityOneInterceptor(UserService userService){
        super();
        this.userService = userService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取HttpSession对象
        HttpSession session = request.getSession();

        // 获取我们登录后存在session中的用户信息
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
            request.setAttribute("msg","无权限，请登录");
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }
        return true;
    }
}
