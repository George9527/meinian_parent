package com.atguigu.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.pojo.Permission;
import com.atguigu.pojo.Role;
import com.atguigu.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller // 创建组件
public class SpringSecurityUserService implements UserDetailsService {

    @Reference //注意：此处要通过dubbo远程调用用户服务
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据用户名称来查询t_user数据表
        com.atguigu.pojo.User user = userService.findUserByUsername(username);

        // 判断该用户是否存在
        if (user == null) {
            return null;
        }

        // 获取这个用户的所有角色
        // private Set<Role> roles = new HashSet<Role>(0);//对应角色集合
        // Set<Role> roles = new HashSet<Role>(0);
        // 获取对应角色集合
        Set<Role> roles = user.getRoles();
        // 新建一个List集合存放获取的权限关键字
        List<GrantedAuthority> lists = new ArrayList<>();
            // Set集合需要遍历
        for (Role role : roles) {
            // 获取集合中的角色的权限
            Set<Permission> permissions = role.getPermissions();
                // 权限关键字
            for (Permission permission : permissions) {
                lists.add(new SimpleGrantedAuthority(permission.getKeyword())) ;
            }
        }

        return new User(username,user.getPassword(),lists);
    }
}
