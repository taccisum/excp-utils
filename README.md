# Exception Utils

## How to

### 发生异常时发送钉钉告警

```java
@Autowired
private SendToDingTalkExceptionHandler sendToDingTalkExceptionHandler;

sendToDingTalkExceptionHandler.handle(new RuntimeException(), "custom msg");
```

```yaml
apsara:
  excp:
    ding-talk:
      alarm:
        enabled: true
        title: My Service
      robot:
        keys: my-service-dev
        access-token: {your_dingtalk_robot_token}
```

建议结合框架（如 Spring MVC、Guava EventBus 等等）的全局异常处理器使用


## References

### Trace ID

trace id 是排查线上错误的时候用来检索日志很好的一种手段，`excp-utils` 默认在各种入口（如 Api 请求、MQ 消息、Job 执行）统一为日志框架（Slf4j）添加了 trace id，开发者可以通过 MDC 直接使用

```java
LogMDCHelper.getTraceId()
```

也可以通过日志框架的 pattern 来使用

```yaml
# application.yml
logging:
  pattern:
    console: '[%X{traceId}] {you pattern...}'  # 注意 traceId 应换成你定义的 key
```

如果某些场景你无需 `excp-utils` 为你添加 trace id，你也可以通过配置来轻松控制
```yaml
# application.yml
apsara:
  excp:
    trace-id:
      enabled:
        servlet: false
        rabbit: true
        scheduled: true
```

