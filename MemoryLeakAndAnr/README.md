#### 内存泄漏

- PermissionActivity 中 leaks.add(this); 应该注释
- PermissionActivity 中 linearLayout 应在 onDestroy 通过 removeAllViews() 释放
- NetworkActivity 中 handler 在 onDestroy 中应通过removeCallbacksAndMessages(null); 释放
- call.enqueue 内函数 NetworkActivity 改为弱引用



##### 优化的 ANRWatchDog

- 代码在 CustomAnrWatchDog 中

- 如果时间轴为新进程延时1s，UI延时8s，原 ANRWatchDog 无法检测 ANR
- 新 ANRWatchDog 通过每一秒进行检测，若发生连续 5 次无反应则返回 ANR，可对上述情况生效

