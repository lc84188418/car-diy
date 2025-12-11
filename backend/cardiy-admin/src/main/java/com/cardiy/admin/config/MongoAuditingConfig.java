package com.cardiy.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * MongoDB 审计配置
 * 用于自动填充 @CreatedBy 和 @LastModifiedBy 字段
 * 
 * 注意：@EnableMongoAuditing 已在主类 CardiyAdminApplication 中启用
 * 这里只需要提供 AuditorAware bean
 */
@Configuration
public class MongoAuditingConfig {

    /**
     * 审计提供者
     * 返回当前操作用户，可以从 SecurityContext 或其他地方获取
     * 这里暂时返回 "system"，实际项目中应该从 SecurityContext 获取当前登录用户
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                // TODO: 从 SecurityContext 获取当前登录用户
                // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                // if (authentication != null && authentication.isAuthenticated()) {
                //     return Optional.of(authentication.getName());
                // }
                // 暂时返回 "system"
                return Optional.of("system");
            }
        };
    }
}

