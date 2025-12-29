package com.cardiy.admin.controller.system;

import com.cardiy.admin.domain.SysPost;
import com.cardiy.admin.domain.vo.CommonSelector;
import com.cardiy.admin.service.ISysPostService;
import com.cardiy.admin.util.MongoQueryUtil;
import com.cardiy.common.api.Result;
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
 * 岗位信息
 */
@RestController
@RequestMapping("/api/admin/system/post")
public class SysPostController {
    
    @Autowired
    private ISysPostService postService;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取岗位列表
     */
    @GetMapping("/list")
    public Result<Page<SysPost>> list(
            @RequestParam(value = "postName", required = false) String postName,
            @RequestParam(value = "postCode", required = false) String postCode,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(current - 1, size, Sort.by(Sort.Direction.ASC, "postSort"));
        
        // 构建查询条件
        List<Criteria> criteriaList = new ArrayList<>();
        if (postName != null && !postName.isEmpty()) {
            Criteria postNameCriteria = MongoQueryUtil.buildLikeCriteria("postName", postName);
            if (postNameCriteria != null) {
                criteriaList.add(postNameCriteria);
            }
        }
        if (postCode != null && !postCode.isEmpty()) {
            Criteria postCodeCriteria = MongoQueryUtil.buildLikeCriteria("postCode", postCode);
            if (postCodeCriteria != null) {
                criteriaList.add(postCodeCriteria);
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
        Page<SysPost> page = MongoQueryUtil.queryWithPage(mongoTemplate, query, SysPost.class, pageable);
        
        return Result.success(page);
    }

    /**
     * 获取岗位列表
     */
    @GetMapping("/selector")
    public Result<List<CommonSelector>> selector(
            @RequestParam(value = "postName", required = false) String postName,
            @RequestParam(value = "postCode", required = false) String postCode) {
        return Result.success(postService.findAllSelector(postName,postCode));
    }
    /**
     * 根据岗位编号获取详细信息
     */
    @GetMapping(value = "/{postId}")
    public Result<SysPost> getInfo(@PathVariable("postId") String postId) {
        return postService.findById(postId)
            .map(Result::success)
            .orElse(Result.error("岗位不存在"));
    }

    /**
     * 新增岗位
     */
    @PostMapping
    public Result<Void> add(@RequestBody SysPost post) {
        postService.save(post);
        return Result.success();
    }

    /**
     * 修改岗位
     */
    @PutMapping
    public Result<Void> edit(@RequestBody SysPost post) {
        postService.save(post);
        return Result.success();
    }

    /**
     * 删除岗位
     */
    @DeleteMapping("/{postIds}")
    public Result<Void> remove(@PathVariable String[] postIds) {
        postService.deleteAllById(List.of(postIds));
        return Result.success();
    }
}

