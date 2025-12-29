package com.cardiy.admin.controller.system;

import com.cardiy.admin.domain.SysUser;
import com.cardiy.admin.domain.dto.SysUserDto;
import com.cardiy.admin.domain.vo.SysUserVo;
import com.cardiy.admin.mapper.SysUserPostMapper;
import com.cardiy.admin.mapper.SysUserRoleMapper;
import com.cardiy.admin.service.ISysUserService;
import com.cardiy.admin.util.MongoQueryUtil;
import com.cardiy.admin.util.RelationTabUtil;
import com.cardiy.common.api.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息
 */
@RestController
@RequestMapping("/api/admin/system/user")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SysUserPostMapper userPostMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public Result<Page<SysUser>> list(
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "nickName", required = false) String nickName,
            @RequestParam(value = "tel", required = false) String tel,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(current - 1, size, Sort.by(Sort.Direction.DESC, "createTime"));

        // 构建查询条件
        List<Criteria> criteriaList = new ArrayList<>();
        if (userName != null && !userName.isEmpty()) {
            Criteria userNameCriteria = MongoQueryUtil.buildLikeCriteria("userName", userName);
            if (userNameCriteria != null) {
                criteriaList.add(userNameCriteria);
            }
        }
        if (nickName != null && !nickName.isEmpty()) {
            Criteria nickNameCriteria = MongoQueryUtil.buildLikeCriteria("nickName", nickName);
            if (nickNameCriteria != null) {
                criteriaList.add(nickNameCriteria);
            }
        }
        if (tel != null && !tel.isEmpty()) {
            Criteria telCriteria = MongoQueryUtil.buildLikeCriteria("tel", tel);
            if (telCriteria != null) {
                criteriaList.add(telCriteria);
            }
        }
        if (status != null && !status.isEmpty()) {
            Criteria statusCriteria = MongoQueryUtil.buildEqualCriteria("status", status);
            if (statusCriteria != null) {
                criteriaList.add(statusCriteria);
            }
        }

        Criteria criteria = MongoQueryUtil.combineCriteria(criteriaList);
        Query query = criteriaList.isEmpty() ? new Query() : new Query(criteria);
        Page<SysUser> page = MongoQueryUtil.queryWithPage(mongoTemplate, query, SysUser.class, pageable);

        return Result.success(page);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @GetMapping(value = "/{userId}")
    public Result<SysUserVo> getInfo(@PathVariable("userId") String userId) {
        return userService.findById(userId);
    }
//    public Result<SysUser> getInfo(@PathVariable("userId") String userId) {
//        return userService.findById(userId)
//                .map(Result::success)
//                .orElse(Result.error("用户不存在"));
//    }

    /**
     * 新增用户
     */
    @PostMapping
    public Result<Void> add(@RequestBody SysUser user) {
        userService.save(user);
        return Result.success();
    }

    /**
     * 修改用户
     */
    @PutMapping
    public Result<Void> edit(@RequestBody SysUserDto dto) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(dto, user);
        userService.save(user);
        //编辑岗位信息
        userPostMapper.deleteAllByUserId(user.getUserId());
        userPostMapper.saveAll(RelationTabUtil.getUserPostList(dto.getPostIds(), user.getUserId()));
        //编辑角色信息
        userRoleMapper.deleteAllByUserId(user.getUserId());
        userRoleMapper.saveAll(RelationTabUtil.getUserRoleList(dto.getRoleIds(), user.getUserId()));
        return Result.success();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userIds}")
    public Result<Void> remove(@PathVariable String[] userIds) {
        userService.deleteAllById(List.of(userIds));
        return Result.success();
    }
}
