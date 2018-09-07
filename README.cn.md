
# sps-boot-groovy
sps (SPS) :  数据库存储过程微服务化

快速, 低成本,移植数据库存储过程为微服务的方法, 基于 Spring Boot, Groovy。
背景 , 在评估一大型传统数据库迁移到 TiDB 时, 挑战是大量核心的,复杂的存储过程,而不是数据的规模。
该方案, 可快速迁移存储过程,原有数据库仅执行数据CRUD,不承担应用服务的功能, 为下一步的数据移植做好准备。

在 TiDB 中， 模拟临时表
1. 主要使用 JSON 数据类型, 及函数 JSON_OBJECT / JSON_EXTRACT。

2. 两个实体表, temp_odometer 注册一个临时表, temp_table 存储临时表的数据。

3. 参考文件(temp_table.sql), 可运行于 MySQL(5.7+) 或 TiDB。



