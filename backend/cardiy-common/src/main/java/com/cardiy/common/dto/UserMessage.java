package com.cardiy.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author liuchun
 * @version v2.4.0
 * @className UserMessage
 * @desc TODO
 * @createTime 2026/1/7 17:10
 */
@Data
public class UserMessage implements Serializable {
    private Long userId;
    private String username;
    private String email;
    private String action; // CREATE, UPDATE, DELETE
    private LocalDateTime timestamp;

    // 构造方法
    public static UserMessage ofCreate(Long userId, String username, String email) {
        UserMessage message = new UserMessage();
        message.setUserId(userId);
        message.setUsername(username);
        message.setEmail(email);
        message.setAction("CREATE");
        message.setTimestamp(LocalDateTime.now());
        return message;
    }
}