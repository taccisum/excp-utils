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

建议结合 Spring MVC 全局异常处理器使用
