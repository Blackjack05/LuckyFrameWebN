package com.luckyframe.project.system.user.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.post.service.IPostService;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.system.role.service.IRoleService;
import com.luckyframe.project.system.user.domain.User;
import com.luckyframe.project.system.user.service.IUserService;

/**
 * 用户信息
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/data/loading")
public class DataController extends BaseController
{
    private String prefix = "data/loading";

    @Autowired
    private IUserService userService;


    //@RequiresPermissions("data:loading:view")
    @GetMapping()
    public String data()
    {
        return prefix + "/data";
    }

    @RequiresPermissions("data:loading:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(User user)
    {
        startPage();
        List<User> list = userService.selectUserList(user);
        return getDataTable(list);
    }

}