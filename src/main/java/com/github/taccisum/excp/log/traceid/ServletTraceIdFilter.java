package com.github.taccisum.excp.log.traceid;


import com.github.taccisum.excp.config.ExceptionProperties;
import com.github.taccisum.excp.log.LogMDCHelper;
import com.github.taccisum.excp.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author taccisum
 * 日志 trace id 过滤器
 */
@Slf4j
@Component
@ConditionalOnClass(Filter.class)
@ConditionalOnProperty(prefix = ExceptionProperties.PREFIX + ".trace-id.enabled", name = "servlet", havingValue = "true", matchIfMissing = true)
public class ServletTraceIdFilter implements Filter, Ordered {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化 Servlet Filter: {}", this.getClass().getName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        LogMDCHelper.setTraceId(IdUtils.traceId());
        try {
            filterChain.doFilter(request, response);
        } finally {
            LogMDCHelper.removeTraceId();
        }
    }

    @Override
    public void destroy() {
        log.info("销毁 Servlet Filter: {}", this.getClass().getName());
    }

    @Override
    public int getOrder() {
        // 最先执行
        return Integer.MIN_VALUE;
    }
}
