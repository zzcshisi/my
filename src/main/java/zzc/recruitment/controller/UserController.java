package zzc.recruitment.controller;


import org.apache.commons.io.FileUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import zzc.recruitment.bean.*;
import zzc.recruitment.ex.*;
import zzc.recruitment.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import zzc.recruitment.util.JsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserinfoService userinfoService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    ResumeService resumeService;
    @Autowired
    InviteService inviteService;
    @Autowired
    PositionService positionService;

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
        List<Notice> notices = noticeService.getShow();
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
            Userinfo userinfo=userinfoService.getById(loginId);
            if(userinfo==null){
                userinfo= new Userinfo();
                userinfo.setId(loginId);
                userinfoService.addUser(userinfo);
            }
            model.addAttribute("username", user.getUserName());
            model.addAttribute("avatar", userinfo.getAvatar());
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
    public String userinfo(HttpServletRequest request,Model model){
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);                // 强制转换成 String
        Userinfo userinfo=userinfoService.getById(id);
        if(userinfo==null){
            userinfo= new Userinfo();
            userinfo.setId(id);
            userinfoService.addUser(userinfo);
        }
        model.addAttribute("userinfo",userinfo);
        return "/user/info/userinfo";
    }

    @GetMapping("/user/info/edit")
    public String editbusinessinfo(HttpServletRequest request,Model model){
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);                // 强制转换成 String
        Userinfo userinfo=userinfoService.getById(id);
        User user = userService.getUserById(id);
        if(userinfo==null){
            userinfo= new Userinfo();
            userinfo.setId(id);
            userinfoService.addUser(userinfo);
        }
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("userinfo",userinfo);
        return "/user/info/edituserinfo";
    }

    //    信息编辑响应
    @PostMapping("/user/info/edit")
    public String EditBusinessinfo(Userinfo userinfo,
                                   @RequestParam("userName") String userName,
                                   HttpServletRequest request,
                                   Model model) {
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);                // 强制转换成 String

        User user = userService.getUserById(userinfo.getId());
        if(id==userinfo.getId()){
            user.setUserName(userName);
            if(userinfo.getGradeyear().replace(" ","").equals("")) {
                userinfo.setGradeyear("1-1-1");
            }
            userinfoService.updateUser(userinfo);
            userService.modifyUser(user);
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("msg", "修改成功");
            model.addAttribute("userinfo", userinfo);
        }
        else{
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("msg", "您无法操作他人的信息");
            model.addAttribute("userinfo", userinfo);
        }
        return "/user/info/edituserinfo";
    }

    //头像编辑响应
    /** 头像文件大小的上限值(10MB) */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /** 允许上传的头像的文件类型 */
    public static final List<String> AVATAR_TYPES = new ArrayList<String>();

    /** 初始化允许上传的头像的文件类型 */
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/jpg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }
    @PostMapping("/user/info/editavatar")
    @ResponseBody
    public JsonResult<String> Editavatar(@RequestParam("avatar_name") MultipartFile file,
                                         HttpServletRequest request){
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);
        // 判断上传的文件是否为空
        if (file.isEmpty()) {
            // 是：抛出异常
            throw new FileEmptyException("上传的头像文件不允许为空");
        }

        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE) { // getSize()：返回文件的大小，以字节为单位
            // 是：抛出异常
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的头像文件");
        }

        // 判断上传的文件类型是否超出限制
        String contentType = file.getContentType();
        // public boolean list.contains(Object o)：当前列表若包含某元素，返回结果为true；若不包含该元素，返回结果为false。
        if (!AVATAR_TYPES.contains(contentType)) {
            // 是：抛出异常
            throw new FileTypeException("不支持使用该类型的文件作为头像，允许的文件类型：\n" + AVATAR_TYPES);
        }

        // 获取当前项目的绝对磁盘路径
        String file_path = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/img/avatar";
        // 保存头像文件的文件夹
        File dir = new File(file_path);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.print(file_path);
        }

        // 保存的头像文件的文件名
        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        int beginIndex = originalFilename.lastIndexOf(".");
        if (beginIndex > 0) {
            suffix = originalFilename.substring(beginIndex);
        }
        String filename = UUID.randomUUID().toString() + suffix;
        String path = file_path+filename;
        // 执行保存头像文件
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(file_path, filename));
        } catch (IllegalStateException e) {
            // 抛出异常
            throw new FileStateException("文件状态异常，可能文件已被移动或删除");
        } catch (IOException e) {
            // 抛出异常
            throw new FileUploadIOException("上传文件时读写错误，请稍后重尝试");
        }
        //删除旧头像
        String old_avatar=userinfoService.getById(id).getAvatar();
        if(old_avatar!=null&&!old_avatar.equals("user.jpg")){
            File old_file=new File(file_path,old_avatar);
            old_file.delete();
        }
        // 头像路径
        String avatar = filename;
        // 将头像写入到数据库中
        userinfoService.updateAvatar(avatar,id);
        // 返回成功头像路径
        return new JsonResult<String>(200, avatar);
        /* Userinfo userinfo = userinfoService.getById(id);
        User user = userService.getUserById(id);
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("userinfo", userinfo);
        model.addAttribute("msg", "上传成功");
        return "/manager/minfo/edituserinfo"; */
    }

    @RequestMapping("/user/search")
    public String usersearch(HttpServletRequest request,
                             Model model,
                             @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                             @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){
        List<Position> posts=positionService.getAll();
        PageInfo<Position> pageInfo = new PageInfo<Position>(posts, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "/user/search/search";
    }


    @RequestMapping("/user/resume")
    public String ToResume(HttpServletRequest request,Model model){
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);                // 强制转换成 String
        Resume resume=resumeService.getById(id);
        if(resume==null){
            resumeService.add(id);
        }
        model.addAttribute("resume", resume);
        return "/user/resume/resume";
    }
    @GetMapping("/user/resume/edit")
    public String ToResumeEdit(HttpServletRequest request,Model model){
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);                // 强制转换成 String
        Resume resume=resumeService.getById(id);
        if(resume==null){
            resumeService.add(id);
        }
        model.addAttribute("resume", resume);
        return "/user/resume/editresume";
    }

    //    信息编辑响应
    @PostMapping("/user/resume/edit")
    public String EditResume(Resume resume,
                                   HttpServletRequest request,
                                   Model model) {
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);                // 强制转换成 String

        User user = userService.getUserById(resume.getId());

        if(id==resume.getId()){
            Resume tmp=resumeService.getById(id);
            if(tmp==null){
                resumeService.add(id);
            }
            resumeService.update(resume);
            model.addAttribute("msg", "修改成功");
            model.addAttribute("resume", resume);
        }
        else{
            model.addAttribute("msg", "您无法操作他人的信息");
            model.addAttribute("resume", resume);
        }
        return "/user/resume/editresume";
    }
    @PostMapping("/user/resume/editavatar")
    @ResponseBody
    public JsonResult<String> Editresumeavatar(@RequestParam("avatar_name") MultipartFile file,
                                         HttpServletRequest request){
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);
        // 判断上传的文件是否为空
        if (file.isEmpty()) {
            // 是：抛出异常
            throw new FileEmptyException("上传的头像文件不允许为空");
        }

        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE) { // getSize()：返回文件的大小，以字节为单位
            // 是：抛出异常
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的头像文件");
        }

        // 判断上传的文件类型是否超出限制
        String contentType = file.getContentType();
        // public boolean list.contains(Object o)：当前列表若包含某元素，返回结果为true；若不包含该元素，返回结果为false。
        if (!AVATAR_TYPES.contains(contentType)) {
            // 是：抛出异常
            throw new FileTypeException("不支持使用该类型的文件作为头像，允许的文件类型：\n" + AVATAR_TYPES);
        }

        // 获取当前项目的绝对磁盘路径
        String file_path = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/img/avatar";
        // 保存头像文件的文件夹
        File dir = new File(file_path);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.print(file_path);
        }

        // 保存的头像文件的文件名
        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        int beginIndex = originalFilename.lastIndexOf(".");
        if (beginIndex > 0) {
            suffix = originalFilename.substring(beginIndex);
        }
        String filename = UUID.randomUUID().toString() + suffix;
        String path = file_path+filename;
        // 执行保存头像文件
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(file_path, filename));
        } catch (IllegalStateException e) {
            // 抛出异常
            throw new FileStateException("文件状态异常，可能文件已被移动或删除");
        } catch (IOException e) {
            // 抛出异常
            throw new FileUploadIOException("上传文件时读写错误，请稍后重尝试");
        }
        //删除旧头像
        String old_avatar=resumeService.getById(id).getAvatar();
        if(old_avatar!=null&&!old_avatar.equals("user.jpg")){
            File old_file=new File(file_path,old_avatar);
            old_file.delete();
        }
        // 头像路径
        String avatar = filename;
        // 将头像写入到数据库中
        resumeService.updateAvatar(avatar,id);
        // 返回成功头像路径
        return new JsonResult<String>(200, avatar);
    }
    @RequestMapping("/user/invite")
    public String ToInvite(HttpServletRequest request,
                           Model model,
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
            // 获取HttpSession对象
            HttpSession session = request.getSession();
            // 获取我们登录后存在session中的用户信息
            Object obj = session.getAttribute("username");
            String loginname = (String) obj;
            int id = Integer.parseInt(loginname);                // 强制转换成 String
            List<Invite> invites=inviteService.getByUid(id);
            PageInfo<Invite> pageInfo = new PageInfo<Invite>(invites, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }

        return "/user/invite/invite";
    }
    @RequestMapping("/user/invite/refuse")
    public String RefuseInvite(@RequestParam("id") int id) {
        inviteService.deleteById(id);

        return "redirect:/user/invite";
    }
}
