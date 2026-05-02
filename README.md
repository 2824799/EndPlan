注意：releases更新频率较低，请自行构建


### GT5U

1.高能束流聚焦对撞靶室移除粒子检测

2.高能束流聚焦对撞靶室增加并行

3.量子操纵者概率强制为100%

4.量子操纵者每个催化剂提供2097152并行(最高并行INT.MAX)


### NH-Utilities

1.时间瓶加速效果永久不消失


### enderIO

1.物品导管速度提升为每4096*64 / tick


### PH:可编程仓室

1. 可调用 "/endplan allowopt set <数值>" 使所有已加载区块内的机器设置 "允许样板优化" 属性（例如设置为 1 开启，如果合成保存请重启整合包）
2. 可调用 "/endplan useNewGTPatternCache set <数值>" 使所有已加载区块内的机器设置 "使用新版GT样板缓存" 属性（例如设置为 1 开启）

### 常用指令

- `/endplan check_gt_recipe_amount`: 检查并输出当前的配方数量等信息
- `/endplan allowopt set <数值>`: 批量修改已加载机器的 allowopt 属性
- `/endplan useNewGTPatternCache set <数值>`: 批量修改已加载机器的 useNewGTPatternCache 属性

### 未完成（计划中）：

1.尝试增加ae2输出总线标记格子数量