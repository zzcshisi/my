package zzc.recruitment.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import zzc.recruitment.bean.*;
import zzc.recruitment.bean.Record;
import zzc.recruitment.ex.*;
import zzc.recruitment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import zzc.recruitment.util.JsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class BusinessController {

    @Autowired
    UserService userService;
    @Autowired
    UserinfoService userinfoService;
    @Autowired
    BusinessinfoService businessinfoService;
    @Autowired
    PositionService positionService;
    @Autowired
    InviteService inviteService;
    @Autowired
    RecordService recordService;

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
        int a,b,c,d,e;
        b=recordService.getStatusNum(id,"待筛选");
        c=recordService.getStatusNum(id,"笔试中");
        d=recordService.getStatusNum(id,"面试中");
        e=recordService.getStatusNum(id,"审核中");
        a=b+c+d+e;
        model.addAttribute("a",a);
        model.addAttribute("b",b);
        model.addAttribute("c",c);
        model.addAttribute("d",d);
        model.addAttribute("e",e);
        model.addAttribute("businessinfo",businessinfo);
        return "business/binfo/businessinfo";
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
        User user = userService.getUserById(id);
        if(businessinfo==null){
            businessinfo= new Businessinfo();
            businessinfo.setId(id);
            businessinfoService.addBusinessinfo(businessinfo);
        }
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("businessinfo",businessinfo);
        return "business/binfo/editbusinessinfo";
    }

    //    信息编辑响应
    @PostMapping("/business/info/edit")
    public String EditBusinessinfo(Businessinfo businessinfo,
                                   @RequestParam("userName") String userName,
                                   Model model) {
        User user = userService.getUserById(businessinfo.getId());
        user.setUserName(userName);

        if(businessinfo.getFounding().replace(" ","").equals("")) {
            businessinfo.setFounding("1-1-1");
        }
        businessinfoService.updateBusiness(businessinfo);
        userService.modifyUser(user);
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("msg", "修改成功");
        model.addAttribute("businessinfo", businessinfo);
        return "business/binfo/editbusinessinfo";
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
    @PostMapping("/business/info/editavatar")
    @ResponseBody
    public JsonResult<String> EditBavatar(@RequestParam("avatar_name") MultipartFile file,
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
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            // nothing to do
        }
        if (path == null || !path.exists()) {
            path = new File("");
        }
        String pathStr = path.getAbsolutePath();
        // 如果是jar部署到服务器，则默认和jar包同级
        String file_path = pathStr+"/static/img/avatar";
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
        return "manager/minfo/edituserinfo"; */
    }

    @RequestMapping("/business/position")
    public String businessposition(HttpServletRequest request,
                                   Model model,
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
        try {
            // 获取HttpSession对象
            HttpSession session = request.getSession();
            // 获取我们登录后存在session中的用户信息
            Object obj = session.getAttribute("username");
            String loginname = (String) obj;
            int id = Integer.parseInt(loginname);                // 强制转换成 String
            List<Position> positions=positionService.getByBid(id);
            PageInfo<Position> pageInfo = new PageInfo<Position>(positions, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        return "business/bposition/businessposition";
    }
    //  添加岗位页面
    @GetMapping("business/position/add")
    public String toAddpositionpage(HttpServletRequest request,
                                    Model model) {
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;// 强制转换成 String
        int id = Integer.parseInt(loginname);
        model.addAttribute("bid",id);
        model.addAttribute("bname",businessinfoService.getById(id).getBusinessname());
        return "business/bposition/addposition";
    }

    //    岗位添加响应
    @PostMapping("/business/position/add")
    public String Adduser(Position position,Model model) {
        try{
            int res=positionService.addPosition(position);
            if(res==0){
                model.addAttribute("msg","添加失败");
            }
            else{
                model.addAttribute("msg","添加成功");
            }
        }finally {
            PageHelper.clearPage();
        }
        return "redirect:/business/position";
    }

    //    岗位删除请求
    @GetMapping("/business/position/delete/{id}")
    public String toDelete(@PathVariable("id") Integer id,Model model,HttpServletRequest request) {
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int uid = Integer.parseInt(loginname);                // 强制转换成 String
        if(uid==positionService.getByPid(id).getBid()){
            positionService.deleteByPid(id);
            return "redirect:/business/position";
        }
        else{
            model.addAttribute("msg","您无权操作非自己发布的岗位，请重新登录");
            return "login";
        }
    }

    //    岗位编辑页面
    @GetMapping("/business/position/edit/{id}")
    public String toEditPosition(@PathVariable("id") Integer id, Model model,HttpServletRequest request) {
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int uid = Integer.parseInt(loginname);                // 强制转换成 String
        if(uid==positionService.getByPid(id).getBid()){
            Position position=positionService.getByPid(id);
            if (position!=null){
                model.addAttribute("position", position);
                return "business/bposition/editposition";
            }
            else{
                model.addAttribute("msg","该岗位不存在,请添加！");
                model.addAttribute("bid",id);
                model.addAttribute("bname",businessinfoService.getById(id).getBusinessname());
                return "business/bposition/addposition";
            }
        }
        else{
            model.addAttribute("msg","您无权操作非自己发布的岗位，请重新登录");
            return "login";
        }
    }

    //    岗位编辑响应
    @PostMapping("/business/position/edit")
    public String Editposition(Position position,Model model,HttpServletRequest request) {
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int uid = Integer.parseInt(loginname);                // 强制转换成 String
        if(uid==position.getBid()){
            if(position.getEndtime().replace(" ","").equals("")) {
               position.setEndtime("2023-12-31");
            }
            positionService.updatePosition(position);
            model.addAttribute("msg", "修改成功");
            model.addAttribute("position", position);
            return "business/bposition/editposition";
        }
        else{
            model.addAttribute("msg","您无权操作非自己发布的岗位，请重新登录");
            return "login";
        }
    }

    @RequestMapping("/business/position/search")
    public String SearchId(Model model,
                           HttpServletRequest request,
                           @RequestParam("searchid")String name,
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
        PageHelper.startPage(pageNum, pageSize);//定位显示页
        try {
            // 获取HttpSession对象
            HttpSession session = request.getSession();
            // 获取我们登录后存在session中的用户信息
            Object obj = session.getAttribute("username");
            String loginname = (String) obj;
            int id = Integer.parseInt(loginname);                // 强制转换成 String
            List<Position> positions=positionService.getByPname(name,id);
            PageInfo<Position> pageInfo = new PageInfo<Position>(positions, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        model.addAttribute("last_search",name);
        return "business/bposition/searchposition";
    }
    //邀请人才功能
    @GetMapping("/business/invite")
    String Invite(Model model,
                  HttpServletRequest request,
                  @RequestParam(defaultValue = "-1",value="xueli") int xueli,
                  @RequestParam(defaultValue = "",value="zhuanye") String zhuanye,
                  @RequestParam(defaultValue = "",value="status") String status,
                  @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "请选择岗位进行推荐") String msg,
                  @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){

        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);                // 强制转换成 String
        List<Position> positions=positionService.getByBid(id);
        model.addAttribute("positions",positions);
        List<Userinfo> userinfos=userinfoService.SelectUser(xueli,zhuanye,status);
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
        PageHelper.startPage(pageNum, pageSize);//定位显示页
        PageInfo<Userinfo> pageInfo = new PageInfo<Userinfo>(userinfos, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("xueli", xueli);
        model.addAttribute("zhuanye", zhuanye);
        model.addAttribute("status", status);
        model.addAttribute("msg",msg);
        return "business/invite/invite";
    }

    @PostMapping("/business/invite")
    String Inviteselect(Model model,
                        HttpServletRequest request,
                        @RequestParam(defaultValue = "-1",value="xueli") int xueli,
                        @RequestParam(defaultValue = "",value="zhuanye") String zhuanye,
                        @RequestParam(defaultValue = "",value="status") String status,
                        @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                        @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){

        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);                // 强制转换成 String
        List<Position> positions=positionService.getByBid(id);
        model.addAttribute("positions",positions);
        List<Userinfo> userinfos=userinfoService.SelectUser(xueli,zhuanye,status);
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
        PageInfo<Userinfo> pageInfo = new PageInfo<Userinfo>(userinfos, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("xueli", xueli);
        model.addAttribute("zhuanye", zhuanye);
        model.addAttribute("status", status);
        return "business/invite/invite";
    }

    @PostMapping("/business/invite/invite")
    String Inviteuser(Model model,
                      @RequestParam(defaultValue = "",value="uid")String uid,
                      @RequestParam(defaultValue = "-1",value="pid")int pid,
                      HttpServletRequest request){
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int id = Integer.parseInt(loginname);                // 强制转换成 String
        String[] uids = uid.split(",");
        if(pid!=-1){
            String pname=positionService.getByPid(pid).getPname();
            String bname=businessinfoService.getById(id).getBusinessname();
            Invite invite=new Invite();
            invite.setBname(bname);
            invite.setPname(pname);
            invite.setPid(pid);
            for (int i = 0; i < uids.length; i++) {
                invite.setUid(Integer.parseInt(uids[i]));
                inviteService.addInvite(invite);
            }
            return "redirect:/business/invite?msg=success";
        }
        return "redirect:/business/invite";
    }
    //岗位投递管理
    @RequestMapping("/business/record")
    public String ToRecord(HttpServletRequest request,
                           Model model,
                           @RequestParam(defaultValue = "-1",value="pid")int pid,
                           @RequestParam(defaultValue = "", value="status")String status,
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
        try {
            // 获取HttpSession对象
            HttpSession session = request.getSession();
            // 获取我们登录后存在session中的用户信息
            Object obj = session.getAttribute("username");
            String loginname = (String) obj;
            int id = Integer.parseInt(loginname);                // 强制转换成 String
            List<Record> records;
            if(pid==-1){
                records=recordService.getRecordByBid(id,status);
            }
            else{
                records=recordService.getRecordByPid(pid,status);
            }
            for(int i=0;i<records.size();i++){
                Record record=records.get(i);
                Position post=positionService.getByPid(record.getPid());
                Resume resume=recordService.getResumeById(record.getId());
                record.setBname(resume.getMyname());
                record.setPname(post.getPname());
                records.set(i,record);
            }
            PageInfo<Record> pageInfo = new PageInfo<Record>(records, pageSize);
            List<Position> positions=positionService.getByBid(id);
            model.addAttribute("pid",pid);
            model.addAttribute("status",status);
            model.addAttribute("positions",positions);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }

        return "business/record/record";
    }
    @PostMapping("/business/record/update")
    @ResponseBody
    public String updateRecord(@RequestParam("status")String status,
                               @RequestParam("id") int id,
                               HttpServletRequest request
                               ) {
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int bid = Integer.parseInt(loginname);                // 强制转换成 String
        if(bid!=positionService.getByPid(recordService.getRecordById(id).getPid()).getBid()){
            return"您无权操作该记录";
        }

        if(status.equals("待筛选")){
            recordService.updateStatus(id,"笔试中");
            return "该申请者进入笔试阶段";
        }
        else if(status.equals("笔试中")){
            recordService.updateStatus(id,"面试中");
            return "该申请者进入面试阶段";
        }
        else if(status.equals("面试中")){
            recordService.updateStatus(id,"审核中");
            return "该申请者进入审核阶段";
        }
        else if(status.equals("审核中")){
            recordService.updateStatus(id,"已通过");
            return "该申请者通过审核！";
        }
        else if(status.equals("已通过")){
            return "该申请者已通过！";
        }
        else if(status.equals("已拒绝")){
            recordService.updateStatus(id,"已拒绝");
            return "该申请者已被拒绝，流程结束";
        }
        else{
            recordService.updateStatus(id,"待筛选");
            return "该申请者状态异常，重新进入筛选！";
        }
    }

    @RequestMapping("/business/record/resume")
    public String ToResume(@RequestParam("id")int id,
                           @RequestParam("pid")int pid,
                           HttpServletRequest request,
                           Model model){
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息
        Object obj = session.getAttribute("username");
        String loginname = (String) obj;
        int bid = Integer.parseInt(loginname);                // 强制转换成 String
        if(bid==positionService.getByPid(pid).getBid()){
            Record record=recordService.getRecordById(id);
            Resume resume=recordService.getResumeById(id);
            model.addAttribute("record",record);
            model.addAttribute("resume", resume);
            return "business/record/resume";
        }
        return "redirect:/business/record";
    }

}
