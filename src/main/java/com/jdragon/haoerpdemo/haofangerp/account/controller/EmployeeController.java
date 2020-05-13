package com.jdragon.haoerpdemo.haofangerp.account.controller;

import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.EmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.LatestEmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.ModifyEmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.service.EmployeeService;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.Constants;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.ResultCode;
import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:34
 * @Description: 用户接口
 */
@RestController
@RequestMapping("/employee")
@Api(tags = "员工接口")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 添加用户、用户自行注册。
     * @param employeeVo
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("注册")
    public Result register(@RequestBody EmployeeVo employeeVo) {
        try {
            return Result.success()
                    .setResult(employeeService.register(employeeVo));
        }catch (Exception e){
            return Result.error().setResult(e.getMessage());
        }
    }

    /**
     * 分页查询员工列表(可用于鉴权,审核模块)
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("/list/{pageNo}/{pageSize}")
    @ApiOperation("分页查询员工列表")
    public Result listEmployees(@ApiParam(name = "pageNo",value = "页码")@PathVariable("pageNo")int pageNo,
                            @ApiParam(name = "pageSize",value = "每页大小")@PathVariable("pageSize")int pageSize,
                            @ApiParam(name = "keyWord",value = "关键词")@RequestParam(value = "keyWord",required = false)String keyWord){
        return Result.success().setResult(employeeService.listEmployees(pageNo, pageSize, keyWord));
    }


    /**
     * 上传用户头像
     * @param avatarFile 头像文件
     * @param request
     * @return
     */
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@ApiParam(name = "avatarFile",value = "用户头像") @RequestParam("avatarFile") MultipartFile avatarFile, HttpServletRequest request){
        try {
            String photoUrl = employeeService.uploadAvatar(avatarFile, request);
            Map<String, String> map = new HashMap<>();
            map.put("photoUrl", photoUrl);
            return Result.success().setResult(map);
        } catch (Exception e) {
            return Result.error(ResultCode.SYSTEM_ERROR).setResult(e.getMessage());
        }
    }

    /**
     * 回显当前登录员工个人信息
     * @return
     */
    @GetMapping("/getLoginEmployeeInfo")
    public Result getLoginEmployeeInfo(){
        try{
            return Result.success().setResult(employeeService.getLoginEmployeeInfo());
        }catch (Exception e){
            return Result.error(ResultCode.SYSTEM_ERROR).setResult(e.getMessage());
        }
    }

    /**
     * 修改员工个人信息
     * @param latestEmployeeVo
     * @return
     */
    @PutMapping("/updateEmployeeInfo")
    public Result updateEmployeeInfo(@ApiParam(name = "latestEmployeeVo",value = "员工修改后的个人信息")@RequestBody @Valid ModifyEmployeeVo modifyEmployeeVo){
        try{
            return Result.success().setResult(employeeService.updateEmployeeInfo(modifyEmployeeVo));
        }catch (Exception e){
            return Result.error(ResultCode.SYSTEM_ERROR).setResult(e.getMessage());
        }
    }

    /**
     * 修改员工密码
     * @param password
     * @return
     */
    @PutMapping("/resetEmployeePassword")
    public Result resetEmployeePassword(@RequestParam("password") String password){
        try{
            if (!password.matches(Constants.PASSWORD_REGEX)) {
                throw new Exception("密码必须为8～16位，且同时包含至少一个字母，一个数字，一个特殊字符(@$!%*#?&_.)");
            }
            return Result.success().setResult(employeeService.resetEmployeePassword(password));
        }catch (Exception e){
            return Result.error(ResultCode.SYSTEM_ERROR).setResult(e.getMessage());
        }
    }

}
