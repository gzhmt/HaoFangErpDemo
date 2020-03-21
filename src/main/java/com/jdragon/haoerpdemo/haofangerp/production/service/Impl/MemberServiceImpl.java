package com.jdragon.haoerpdemo.haofangerp.production.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdragon.haoerpdemo.haofangerp.commons.tools.Bean2Utils;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.MemberVo;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Member;
import com.jdragon.haoerpdemo.haofangerp.production.mappers.MemberMapper;
import com.jdragon.haoerpdemo.haofangerp.production.service.MemberService;
import com.jdragon.haoerpdemo.haofangerp.security.commons.BCryptPasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 11:26
 * @Description: 用户服务接口实现类
 */
@Service
public class  MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    @Override
    public Member getMemberByEmployeeNo(String employeeNo) {
        LambdaQueryWrapper<Member> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //查询条件：全匹配账号名，和状态为1的账号
        lambdaQueryWrapper.eq(Member::getEmployeeNo,employeeNo);
        Member member = getOne(lambdaQueryWrapper);
        return member;
    }

    @Override
    public boolean checkLogin(String employeeNo, String rawPassword) throws Exception {
        Member member = this.getMemberByEmployeeNo(employeeNo);
        if(member==null){
            throw  new Exception("账号不存在，请重新尝试！");
        }else{
            //加密的密码
            String encodedPassword = member.getPassword();
            //和加密后的密码进行比配
            if(!bCryptPasswordEncoderUtil.matches(rawPassword,encodedPassword)) {
                //System.out.println("checkLogin--------->密码不正确！");
                //设置友好提示
                throw new Exception("密码不正确！");
            }else{
                return  true;
            }
        }
    }

    @Override
    public Member register(MemberVo memberVo) throws Exception {
        if(memberVo !=null) {
            Member member = this.getMemberByEmployeeNo(memberVo.getEmployeeNo());
            if(member != null) {
                throw new Exception("这个用户已经存在，不能重复。");
            }
            member = (Member)Bean2Utils.copyProperties(memberVo,Member.class);
            member.setPassword(bCryptPasswordEncoderUtil.encode(memberVo.getPassword()));
            if(member.insert()){
                return member;
            }else{
                throw new Exception("注册失败");
            }
        }else{
            throw new Exception("错误消息：用户对象为空！");
        }
    }
}
