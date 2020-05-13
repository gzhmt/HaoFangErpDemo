package com.jdragon.haoerpdemo.haofangerp.commons.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.13 21:27
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathProperty {
    /**
     * windows系统下的基础路径
     */
    private String windowsPath;
    /**
     * linux系统下的基础路径
     */
    private String linuxPath;
    /**
     * 头像路径
     */
    private String avatarUrl;
    /**
     * 软件logo地址
     */
    private String logoUrl;
}