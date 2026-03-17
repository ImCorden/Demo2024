# 🚀 Demo2024: 基于 Spring Cloud Alibaba 的微服务实战项目

![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.0.2-brightgreen?logo=springboot)
![Spring Cloud Alibaba](https://img.shields.io/badge/SC_Alibaba-2022.0.0.0-blue?logo=alibabacloud)
![JDK 17](https://img.shields.io/badge/JDK-17-orange?logo=java)

**Demo2024** 是一套基于 **Spring Boot 3** 与 **JDK 17** 构建的全栈微服务解决方案。本项目深度集成 **Spring Cloud Alibaba** 生态体系，模拟了“在线教育 + 电子商务”融合的业务场景，旨在演示高并发、分布式事务、统一鉴权及海量数据检索的企业级落地实践。

---

## 📖 项目概述

本项目采用云原生架构理念，致力于展示在复杂分布式环境下，如何保障系统的高可用性、一致性与高性能。

### 核心技术亮点
* **高并发秒杀**：Redis Lua 脚本原子操作 + RocketMQ 异步削峰。
* **分布式事务**：Seata AT (零侵入) 与 TCC (高性能) 模式双方案演示。
* **统一鉴权**：Gateway + Sa-Token 架构，实现轻量级 RBAC 权限管控。
* **全文搜索**：Elasticsearch 7.17.x 支撑毫秒级高亮检索与聚合分析。

---

## 🛠 技术栈概览

| 类别 | 技术组件                     | 版本 (参考) | 核心描述 |
| :--- |:-------------------------| :--- | :--- |
| **基础框架** | **Spring Boot**          | 3.0.2 | 核心应用容器与生态基石 |
| **微服务治理** | **Spring Cloud Alibaba** | 2022.0.0.0 | 服务发现、配置中心及流量治理 |
| **注册/配置** | **Nacos**                | 2.x | 动态服务发现与配置集中管理 |
| **流量治理** | **Sentinel**             | 1.8.x | 熔断、降级与系统自适应限流 |
| **事务管理** | **Seata**                | 1.6+/2.0 | AT 模式与 TCC 模式分布式事务 |
| **异步驱动** | **RocketMQ**             | 4.9.x | 业务解耦、异步下单与流量削峰 |
| **权限安全** | **Sa-Token**             | Latest | 极简、高性能的权限认证框架 |
| **API 网关** | **Gateway**              | 4.x | 统一流量入口、动态路由与鉴权 |
| **数据持久化** | **MyBatis-Plus**         | 3.5.x | 高效的对象关系映射 (ORM) |
| **缓存/锁** | **Redis/Redisson**       | 7.x | 多级缓存加速与分布式锁实现 |
| **搜索引擎** | **Elasticsearch**        | 7.17.x | 课程海量数据检索与统计 |
| **任务调度** | **XXL-JOB**              | 2.4.x | 分布式定时任务调度平台 |
| **文档交互** | **Knife4j**              | 4.x | 增强型 Swagger 接口交互文档 |

---

## 🧩 模块架构

```bash
Demo2024
├── auth-server      # [9002] 认证中心：负责 Token 签发、登录及 RBAC 管理
├── gateway-server   # [9000] 统一网关：负责路由转发、全局过滤器鉴权
├── student-server   # [9001] 学生服务：课程管理、学习计划、ES 检索
├── shopping-server  # [9003] 交易服务：购物车、订单、支付、高并发秒杀
├── common-tools     # 公共组件：统一异常处理、标准化响应、Feign Client 封装
├── dbscript         # 基础设施：SQL 脚本、Nacos 配置文件、API 调试配置
└── pom.xml          # 父工程：全局依赖版本管理
```
 
# 🚀 Demo2024: 基于 Spring Cloud Alibaba 的微服务架构实战

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0.2-brightgreen?logo=springboot)
![JDK](https://img.shields.io/badge/JDK-17-orange?logo=openjdk)
![Spring Cloud Alibaba](https://img.shields.io/badge/SC_Alibaba-2022.0.0.0-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

**Demo2024** 是一套高性能、全栈式的微服务解决方案。项目基于现代化的云原生架构设计，模拟了在线教育与电子商务融合的复杂业务场景，深度集成 Spring Cloud Alibaba 生态，致力于演示分布式环境下的企业级技术落地。

---

## 🛠 核心功能与技术实现

### 1. 高并发秒杀系统 (SecKill)
针对“瞬时高并发、读多写少”场景，系统采用 **多级缓存 + 异步削峰** 的架构设计。



* **网关前置校验**：利用 **Gateway** 集成 **Sentinel** 进行热点参数限流，拦截异常流量。
* **库存预热**：通过 `SecKillRedisTools` 组件，提前将商品库存加载至 **Redis**。
* **原子扣减**：执行 **Redis Lua 脚本** 进行库存预扣减，从根本上杜绝超卖。
* **异步解耦**：预扣成功后，通过 **RocketMQ (Stream)** 发送订单创建消息。
* **削峰填谷**：`Shopping-Server` 消费者异步将订单持久化至 **MySQL**，缓解瞬时写入压力。

### 2. 分布式事务解决方案 (Seata)
项目演示了保障微服务链路数据强一致性的两种方案：



* **AT 模式 (`bothSave`)**：通过 Seata 代理数据源自动管理回滚日志 (Undo Log)，对业务代码**零侵入**，适用于标准 CRUD 场景。
* **TCC 模式 (`ttcUpdate`)**：手动实现 `Prepare` (Try)、`Commit` (Confirm)、`Rollback` (Cancel) 三阶段逻辑，适用于**极高性能要求**或非 ACID 资源的复杂场景。

### 3. 网关统一鉴权体系 (Gateway + Sa-Token)
替代传统的 Spring Security，采用轻量级高效方案：
* **全局拦截**：在 Gateway 层实现 `LoginCheckGlobalFilter` 统一拦截请求。
* **权限验证**：集成 Redis，通过 `StpInterFaceImp` 动态加载角色权限。
* **优化方案**：支持 Redis `HMGet` 命令或多级缓存（本地缓存 + Redis），大幅降低鉴权 I/O 开销。

### 4. Elasticsearch 搜索增强
在 `Student-Server` 中实现复杂检索逻辑：
* **检索功能**：支持关键字高亮、多条件布尔查询 (`Bool Query`) 及价格区间筛选。
* **数据聚合**：支持对搜索结果进行多维度的分类聚合统计分析。

---

## ⚡ 部署与启动指南 (Deployment Guide)

### 1. 基础设施准备
推荐使用 **Docker** 或 **OrbStack** 部署以下中间件：
* **Nacos Server (2.x)** / **MySQL (8.0)** / **Redis (7.x)**
* **RocketMQ (NameServer + Broker)** / **Seata Server**
* **Elasticsearch + Kibana** (可选) / **XXL-JOB Admin** (可选)

### 2. 初始化配置
1.  **数据库**：创建业务库（如 `seata`, `demo_student`, `demo_shopping`）并导入 SQL 初始化脚本。
2.  **配置中心**：导入 `dbscript/nacos` 目录下的配置文件至 Nacos（public 命名空间）。
3.  **关键注意**：修改 Nacos 配置文件内的 IP 地址，确保指向你环境的实际地址（本地开发建议使用 `localhost` 或局域网 IP）。

### 3. 服务启动序列
请遵循以下顺序启动，以避免依赖报错：
1.  `GatewayServerApplication` (网关)
2.  `AuthApplication` (认证中心)
3.  `StudentApplication` (学生业务)
4.  `ShoppingApplication` (交易业务)

**Author:** Bob  
**Copyright:** © 2024 Demo Project