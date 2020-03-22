package com.jdragon.haoerpdemo.haofangerp.security;

import com.jdragon.haoerpdemo.haofangerp.security.commons.BCryptPasswordEncoderUtil;
import com.jdragon.haoerpdemo.haofangerp.security.commons.DynamicPermission;
import com.jdragon.haoerpdemo.haofangerp.security.handler.*;
import com.jdragon.haoerpdemo.haofangerp.security.filter.MyOncePerRequestFilter;
import com.jdragon.haoerpdemo.haofangerp.security.filter.MyUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 23:19
 * @Description:
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("authUserDetailsServiceImpl")
    UserDetailsService authUserDetailsService;

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    //登录成功处理器
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    MyOncePerRequestFilter myOncePerRequestFilter;

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;


    //退出处理器
    @Autowired
    private MyLogoutHandler myLogoutHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    DynamicPermission dynamicPermission;
    /**
     * 从容器中取出 AuthenticationManagerBuilder，执行方法里面的逻辑之后，放回容器
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(authUserDetailsService).passwordEncoder(bCryptPasswordEncoderUtil);
    }

    /**
     * 放行swagger2的配置
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",//swagger api json
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/swagger-ui.html","/webjars/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //第1步：解决跨域问题。cors 预检请求放行,让Spring security 放行所有preflight request（cors 预检请求）
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

        //第2步：让Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().cacheControl();

        //第3步：请求权限配置
        //放行注册API请求，其它任何请求都必须经过身份验证.
        http.authorizeRequests()
                .antMatchers("/member/register").permitAll()

                //ROLE_ADMIN可以操作任何事情
                //.antMatchers("/**").hasRole("ADMIN")
                //同等上一行代码
                //.antMatchers("/**").hasAuthority("ROLE_ADMIN")
                /*
                 由于使用动态资源配置，以上代码在数据库中配置如下：
                 在system_power中添加一条记录
                 api_id=1，
                 api_name = 所有API，
                 api_url=/**,
                 api_method=GET,POST,PUT,DELETE
                 */
                //动态加载资源,基于表达式
                .anyRequest().access("@dynamicPermission.checkPermisstion(request,authentication)");

        //第4步：拦截账号、密码。覆盖 UsernamePasswordAuthenticationFilter过滤器
        http.addFilterAt(myUsernamePasswordAuthenticationFilter() , UsernamePasswordAuthenticationFilter.class);

        //第5步：拦截token，并检测。在 UsernamePasswordAuthenticationFilter 之前添加 JwtAuthenticationTokenFilter
        http.addFilterBefore(myOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);

        //第6步：处理异常情况：认证失败和权限不足
        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint).accessDeniedHandler(myAccessDeniedHandler);

        //第7步：登录,因为使用前端发送JSON方式进行登录，所以登录模式不设置也是可以的。
        http.formLogin();

        //第8步：退出
        http.logout().addLogoutHandler(myLogoutHandler).logoutSuccessHandler(myLogoutSuccessHandler);
    }
    /**
     * 手动注册账号、密码拦截器
     * @return
     * @throws Exception
     */
    @Bean
    MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
        //成功后处理
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        //失败后处理
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
