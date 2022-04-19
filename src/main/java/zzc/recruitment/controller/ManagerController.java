package zzc.recruitment.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zzc.recruitment.bean.Notice;
import zzc.recruitment.service.*;
import zzc.recruitment.bean.User;
import zzc.recruitment.bean.Userinfo;
import zzc.recruitment.bean.Businessinfo;
import zzc.recruitment.service.BusinessinfoService;
import zzc.recruitment.service.ResumeService;
import zzc.recruitment.service.PositionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import java.util.*;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import zzc.recruitment.ex.*;
import org.springframework.util.ClassUtils;
import org.apache.commons.io.FileUtils;
import zzc.recruitment.util.JsonResult;
@Controller
public class ManagerController {

    @Autowired
    UserService userService;
    @Autowired
    UserinfoService userinfoService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    BusinessinfoService businessinfoService;
    @Autowired
    PositionService positionService;
    @Autowired
    ResumeService resumeService;

    @RequestMapping("/manager/mindex")
    public String mindex(Model model,HttpServletRequest request){

        model.addAttribute("a", userService.getUserNum());
        model.addAttribute("b", userService.getBusinessNum());
        model.addAttribute("c", userService.getManagerNum());
        return "manager/mindex";

    }

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
        if (user!=null){
            model.addAttribute("user", user);
            return "/manager/muser/edituser";
        }
        else{
            model.addAttribute("msg","该用户不存在,请添加！");
            model.addAttribute("id",id);
            return "/manager/muser/adduser";
        }

    }

    //    用户编辑响应
    @PostMapping("/manager/muser/edit")
    public String Edituser(User user,Model model) {
        userService.modifyUser(user);
        model.addAttribute("msg", "修改成功");
        model.addAttribute("user", user);
        return "/manager/muser/edituser";
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
        model.addAttribute("msg", "修改成功");
        model.addAttribute("notice",notice);
        return "/manager/mnotice/editnotice";
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
            List<Userinfo> userinfos= userinfoService.getAllUser();
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
        userinfoService.deleteUser(id);
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
            List<Userinfo> userinfos = userinfoService.searchId(id);
            PageInfo<Userinfo> pageInfo = new PageInfo<Userinfo>(userinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        model.addAttribute("last_search",id);
        return "manager/minfo/searchuserinfo";
    }

    //    用户信息编辑页面
    @GetMapping("/manager/minfo/edit/{id}")
    public String toEditInfo(@PathVariable("id") Integer id, Model model) {
        Userinfo userinfo = userinfoService.getById(id);
        User user = userService.getUserById(id);
        if (user!=null){
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("userinfo", userinfo);
            return "/manager/minfo/edituserinfo";
        }
        else{
            model.addAttribute("msg","该用户不存在,请添加！");
            model.addAttribute("id",id);
            return "/manager/muser/adduser";
        }

    }

    //    用户信息编辑响应
    @PostMapping("/manager/minfo/edit")
    public String Edituserinfo(Userinfo userinfo,Model model) {
        User user = userService.getUserById(userinfo.getId());
        if (user!=null){
            if(userinfo.getGradeyear().replace(" ","").equals("")) {
                userinfo.setGradeyear("1-1-1");
            }
            userinfoService.updateUser(userinfo);
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("msg", "修改成功");
            model.addAttribute("userinfo", userinfo);
            return "/manager/minfo/edituserinfo";
        }
        else{
            model.addAttribute("msg","该用户不存在,请添加！");
            model.addAttribute("id",userinfo.getId());
            return "/manager/muser/adduser";
        }

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

    @PostMapping("/manager/minfo/editavatar")
    @ResponseBody
    public JsonResult<String> Editavatar(@RequestParam("avatar_name") MultipartFile file,
                                         @RequestParam("id") int id,
                                         Model model,
                                         HttpSession session){
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
    //企业信息管理
    @RequestMapping("/manager/mbusinessinfo")
    public String mbusinessinfo(Model model,
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
            List<Businessinfo> businessinfos= businessinfoService.getAllBusiness();
            PageInfo<Businessinfo> pageInfo = new PageInfo<Businessinfo>(businessinfos, pageSize);
            model.addAttribute("pageInfo",pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        return "manager/mbusinessinfo/mbinfo";
//        model.addAttribute("pageInfo", null);
    }


    //    企业信息删除请求
    @GetMapping("/manager/mbusinessinfo/delete/{id}")
    public String toDeleteBusinessinfo(@PathVariable("id") Integer id,Model model) {
        businessinfoService.deleteBusiness(id);
        return "redirect:/manager/mbusinessinfo";
    }

    //搜索
    @RequestMapping("/manager/mbusinessinfo/search")
    public String SearchBusinessInfo(Model model,
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
            List<Businessinfo> businessinfos= businessinfoService.searchId(id);
            PageInfo<Businessinfo> pageInfo = new PageInfo<Businessinfo>(businessinfos, pageSize);
            model.addAttribute("pageInfo",pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        model.addAttribute("last_search",id);
        return "manager/mbusinessinfo/searchbusinessinfo";
    }

    //    企业信息编辑页面
    @GetMapping("/manager/mbusinessinfo/edit/{id}")
    public String toEditBusinessInfo(@PathVariable("id") Integer id,
                                     Model model) {

        Businessinfo businessinfo = businessinfoService.getById(id);
        User user = userService.getUserById(id);
        if (user!=null){
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("businessinfo", businessinfo);
            return "/manager/mbusinessinfo/editbusinessinfo";
        }
        else{
            model.addAttribute("msg","该用户不存在,请添加！");
            model.addAttribute("id",id);
            return "/manager/muser/adduser";
        }

    }

    //    企业信息编辑响应
    @PostMapping("/manager/mbusinessinfo/edit")
    public String EditBusinessinfo(Businessinfo businessinfo,Model model) {
        User user = userService.getUserById(businessinfo.getId());
        if (user!=null){
            if(businessinfo.getFounding().replace(" ","").equals("")) {
                businessinfo.setFounding("1-1-1");
            }
            businessinfoService.updateBusiness(businessinfo);
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("msg", "修改成功");
            model.addAttribute("businessinfo", businessinfo);
            return "/manager/mbusinessinfo/editbusinessinfo";
        }
        else{
            model.addAttribute("msg","该用户不存在,请添加！");
            model.addAttribute("id",businessinfo.getId());
            return "/manager/muser/adduser";
        }

    }

    @PostMapping("/manager/mbusinessinfo/editavatar")
    @ResponseBody
    public JsonResult<String> EditBusinessavatar(@RequestParam("avatar_name") MultipartFile file,
                             @RequestParam("id") int id,
                             Model model,
                             HttpSession session){
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
        String old_avatar=businessinfoService.getById(id).getAvatar();
        if(old_avatar!=null&&!old_avatar.equals("user.jpg")){
            File old_file=new File(file_path,old_avatar);
            old_file.delete();
        }
        // 头像路径
        String avatar = filename;
        // 将头像写入到数据库中
        businessinfoService.updateAvatar(avatar,id);
        // 返回成功头像路径
        return new JsonResult<String>(200, avatar);
        /* Userinfo userinfo = userinfoService.getById(id);
        User user = userService.getUserById(id);
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("userinfo", userinfo);
        model.addAttribute("msg", "上传成功");
        return "/manager/minfo/edituserinfo"; */
    }


}


