package com.jdragon.haoerpdemo.haofangerp.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Employee;
import com.jdragon.haoerpdemo.haofangerp.account.domain.entity.Role;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.EmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.LatestEmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.ModifyEmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.domain.vo.QueryEmployeeVo;
import com.jdragon.haoerpdemo.haofangerp.account.mappers.EmployeeMapper;
import com.jdragon.haoerpdemo.haofangerp.account.service.EmployeeService;
import com.jdragon.haoerpdemo.haofangerp.commons.constant.Constants;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Task;
import com.jdragon.haoerpdemo.haofangerp.security.commons.BCryptPasswordEncoderUtil;
import com.jdragon.haoerpdemo.haofangerp.security.commons.SecurityContextHolderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.generics.tree.Tree;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/3/26 下午4:30
 * @Description: 用户服务接口实现类
 */
@Service
//@CacheConfig(cacheNames = "employee")
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    @Autowired
    EmployeeMapper employeeMapper;

//    @Cacheable
    @Override
    public Employee getEmployeeByEmployeeNo(String employeeNo) {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getEmployeeNo,employeeNo);
        Employee employee = getOne(lambdaQueryWrapper);
        return employee;
    }

    @Override
    public boolean checkLogin(String employeeNo, String rawPassword) throws Exception {
        Employee employee = this.getEmployeeByEmployeeNo(employeeNo);
        if(employee == null){
            throw new Exception("账号不存在，请重新尝试！");
        }else{
            //加密的密码
            String encodedPassword = employee.getPassword();
            //和加密后的密码进行比配
            if(!bCryptPasswordEncoderUtil.matches(rawPassword,encodedPassword)) {
                throw new Exception("密码不正确！");
            }else{
                return true;
            }
        }
    }

//    @Cacheable(key = "#employeeVo.employeeNo")
    @Override
    public Employee register(EmployeeVo employeeVo) throws Exception {
        if(employeeVo !=null) {
            Employee employee = this.getEmployeeByEmployeeNo(employeeVo.getEmployeeNo());
            if(employee != null) {
                throw new Exception("这个用户已经存在，不能重复。");
            }
            employee = (Employee) Bean2Utils.copyProperties(employeeVo, Employee.class);
            employee.setPassword(bCryptPasswordEncoderUtil.encode(employeeVo.getPassword()));
            if(employee.insert()){
                return employee;
            }else{
                throw new Exception("注册失败");
            }
        }else{
            throw new Exception("错误消息：用户对象为空！");
        }
    }

    @Override
    public PageInfo<QueryEmployeeVo> listEmployees(int pageNo, int pageSize, String keyWord) {
        PageHelper.startPage(pageNo, pageSize);
        if(Optional.ofNullable(keyWord).isPresent()){
            keyWord = "%" + keyWord.replace("_","\\_").replace("%","\\%") + "%";
        }
        List<QueryEmployeeVo> list = employeeMapper.listEmployees(keyWord);
        PageInfo<QueryEmployeeVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String uploadAvatar(MultipartFile avatarFile, HttpServletRequest request) throws Exception {
        File folder = new File(Constants.AVATAR_DIR);
        if(!folder.isDirectory()){
            folder.mkdirs();
        }
        String oldName = avatarFile.getOriginalFilename();
        String suffix = oldName.substring(oldName.lastIndexOf("."));
        if(!(suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".png"))){
            throw new Exception("上传的头像不合法,请上传jpg，png或者jpeg格式的文件");
        }
        String newName = UUID.randomUUID().toString() + suffix;
        File targetFile = new File(folder, newName);
        String employeeNo = SecurityContextHolderHelper.getEmployeeNo();
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getEmployeeNo, employeeNo);
        try {
            Employee employee = baseMapper.selectOne(lambdaQueryWrapper);
            String oldPhotoName = employee.getPhotoUrl().substring(employee.getPhotoUrl().lastIndexOf("/") + 1);
            new File(Constants.AVATAR_DIR, oldPhotoName).delete();
            avatarFile.transferTo(targetFile);
        } catch (IOException e) {
            throw new Exception("上传头像出错,请重新尝试");
        }
        Employee employee = new Employee();
        String photoUrl = request.getScheme() + "://" + request.getServerName()+":" + request.getServerPort() + Constants.AVATAR_DIR + newName;
        employee.setPhotoUrl(photoUrl);
        if(baseMapper.update(employee,lambdaQueryWrapper) > 0){
            return photoUrl;
        } else {
            throw new Exception("上传头像出错,请重新尝试");
        }
    }

    @Override
    public boolean updateEmployeeInfo(ModifyEmployeeVo modifyEmployeeVo) throws Exception {
        String employeeNo = SecurityContextHolderHelper.getEmployeeNo();
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getEmployeeNo, employeeNo);
        Employee employee = (Employee) Bean2Utils.copyProperties(modifyEmployeeVo, Employee.class);
        if(baseMapper.update(employee,lambdaQueryWrapper) > 0){
            return true;
        } else {
            throw new Exception("修改个人信息失败");
        }
    }

    @Override
    public LatestEmployeeVo getLoginEmployeeInfo() {
        String employeeNo = SecurityContextHolderHelper.getEmployeeNo();
        LatestEmployeeVo loginEmployeeInfo = employeeMapper.getLoginEmployeeInfo(employeeNo);
        return loginEmployeeInfo;
    }

    @Override
    public boolean resetEmployeePassword(String password) throws Exception {
        String employeeNo = SecurityContextHolderHelper.getEmployeeNo();
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getEmployeeNo, employeeNo);
        Employee employee = new Employee();
        employee.setPassword(bCryptPasswordEncoderUtil.encode(password));
        if(baseMapper.update(employee,lambdaQueryWrapper) > 0){
            return true;
        } else {
            throw new Exception("修改密码失败");
        }
    }
}
