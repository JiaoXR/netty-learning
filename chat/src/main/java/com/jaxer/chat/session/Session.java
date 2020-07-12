package com.jaxer.chat.session;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 记录会话信息
 *
 * @author jaxer
 * @date 2020/6/30 10:37 AM
 */
@Data
@AllArgsConstructor
public class Session {
    private String userId;

    private String username;
}
