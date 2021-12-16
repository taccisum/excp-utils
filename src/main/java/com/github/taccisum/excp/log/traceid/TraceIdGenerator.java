package com.github.taccisum.excp.log.traceid;

import org.springframework.core.Ordered;

/**
 * trace id 生成器
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-12-16
 */
public interface TraceIdGenerator extends Ordered {
    String generate();

    @Override
    default int getOrder() {
        return 0;
    }
}
