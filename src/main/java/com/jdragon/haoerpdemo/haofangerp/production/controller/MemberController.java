package com.jdragon.haoerpdemo.haofangerp.production.controller;

import com.jdragon.haoerpdemo.haofangerp.commons.response.Result;
import com.jdragon.haoerpdemo.haofangerp.production.domain.vo.MemberVo;
import com.jdragon.haoerpdemo.haofangerp.production.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 16:48
 * @Description: 用户接口
 */
@Slf4j
@RestController
@RequestMapping("/member")
@Api(tags = "用户注册")
public class MemberController {

    @Autowired
    MemberService memberService;

    /**
     * 添加用户、用户自行注册。
     * @param memberVo
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("注册")
    public Result register(@RequestBody MemberVo memberVo) {
        try {
            log.debug("registerVo = " + memberVo);
            return Result.success("注册成功")
                    .setResult(memberService.register(memberVo));
        }catch (Exception e){
            return Result.success(e.getMessage());
        }
    }
}
