package com.cardiy.admin.util;

import com.cardiy.admin.domain.SysUserPost;
import com.cardiy.admin.domain.SysUserRole;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuchun
 * @version v2.4.0
 * @className RelationTabUtil
 * @desc TODO
 * @createTime 2025/12/29 16:58
 */
public class RelationTabUtil {
    
    public static List<SysUserPost> getUserPostList(List<String> postIds, String userId) {
        List<SysUserPost> list = new ArrayList<>(postIds.size());
        for (String id : postIds) {
            SysUserPost obj = new SysUserPost();
            obj.setUserId(userId);
            obj.setPostId(id);
            list.add(obj);
        }
        return list;
    }
    public static List<SysUserRole> getUserRoleList(List<String> roleIds, String userId) {
        List<SysUserRole> list = new ArrayList<>(roleIds.size());
        for (String id : roleIds) {
            SysUserRole obj = new SysUserRole();
            obj.setUserId(userId);
            obj.setRoleId(id);
            list.add(obj);
        }
        return list;
    }
}
