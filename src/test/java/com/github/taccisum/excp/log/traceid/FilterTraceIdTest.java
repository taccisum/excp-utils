package com.github.taccisum.excp.log.traceid;

import com.github.taccisum.excp.config.ExceptionProperties;
import com.github.taccisum.excp.log.LogMDCHelper;
import com.github.taccisum.excp.log.traceid.generator.SimpleTraceIdGenerator;
import com.github.taccisum.excp.utils.IdUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-12-16
 */
@RunWith(SpringRunner.class)
@Import({ServletTraceIdFilter.class, SimpleTraceIdGenerator.class})
public class FilterTraceIdTest {
    @Autowired
    private ServletTraceIdFilter filter;
    @Autowired
    private SimpleTraceIdGenerator generator;

    @Before
    public void setUp() throws Exception {
        IdUtils.setTraceIdGenerator(generator);
        LogMDCHelper.setProperties(new ExceptionProperties());
    }

    @Test
    public void index() throws IOException, ServletException {
        assertThat(LogMDCHelper.getTraceId()).isBlank();
        filter.doFilter(mock(ServletRequest.class), mock(ServletResponse.class), (request, response) -> {
            assertThat(LogMDCHelper.getTraceId()).isNotBlank();
        });
        assertThat(LogMDCHelper.getTraceId()).isBlank();
    }
}
