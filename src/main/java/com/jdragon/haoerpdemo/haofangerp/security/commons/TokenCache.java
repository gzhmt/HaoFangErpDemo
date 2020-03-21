package com.jdragon.haoerpdemo.haofangerp.security.commons;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 23:24
 * @Description: 缓存管理token
 */
public class TokenCache {
    private static final String TOKEN_KEY = "token_";
    private static Cache<String,String> cache = CacheBuilder.newBuilder().build();

    /**
     * 保存
     * @param token
     */
    public static void setToken(String username,String token) {

        cache.put(TOKEN_KEY+username,token);
    }

    /**
     * 取
     * @return
     */
    public static String getTokenFromCache(String username){
        return cache.getIfPresent(TOKEN_KEY+username);
    }

}
