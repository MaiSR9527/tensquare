# 十次方社交项目

## 项目介绍

采用目前主流的微服务系统架构SpringBoot+SpringCloud+SpringData。

系统整体分为三部分：微服务、网站前台、网站后台。

功能模块包括：文章、问答、招聘、活动、吐槽、交友、用户中心、搜索中心等。最后所有服务都采用Docker勇气话部署。

## 模块介绍

| 模块名称                  | 模块中文名称   |
| ------------------------- | -------------- |
| tensquare_common          | 公共模块       |
| tensquare_article         | 文章微服务     |
| tensquare_base            | 基础微服务     |
| tensquare_friend          | 交友微服务     |
| tensquare_gathering       | 活动微服务     |
| tensquare_qa              | 问答微服务     |
| tensquare_recruit         | 招聘微服务     |
| tensquare_user            | 用户微服务     |
| tensquare_spit            | 吐槽微服务     |
| tensquare_search          | 搜索微服务     |
| tensquare_web             | 前台微服务网关 |
| tensquare_manager         | 后台微服务网关 |
| tensquare_eureka          | 注册中心       |
| tensquare_config          | 配置中心       |
| tensquare_consumer        | 消费者微服务   |
| tensquare_article_crawler | 文章爬虫微服务 |
| tensquare_user_crawler    | 用户爬虫微服务 |
| tensquare_ai              | 人工智能微服务 |
| dist                      | 补充：静态原型 |
| api-doc                   | 补充：接口文档 |

## 开发环境

1. JDK1.8
2. mysql5.7
3. idea 2019.3.1
4. maven 3.6.1
5. docker最新版本
6. 服务器：centos7
7. 测试工具：Postman

## 运用技术

后端技术：springBoot、SpringCloud、SpringData（JPA、MongDB、Elasticsearch）、SpringMVC、RabbitMQ、WebMagic、DeepLeaning4J、IK分词器、JWT

前端技术：Node.js、Vue.js、ElementUI、NUXT

容器技术：Docker、Rancher（容器编排管理）、Grafana+cAdvisor+influxDB(容器实时监控)

## 模块功能介绍

### 招聘微服务：tensquare_recruit

招聘服务主要分为两块：企业信息和招聘信息

如：招聘企业首页、热门企业列表、推荐职位、最新职位列表。

待补充功能：简历投递

### 问答微服务：tensquare_qa

主要功能：用户可以在提出的问题下进行回答。有最新回答列表、热门回答列表、等待回答列表。

### 文章微服务：tensquare_article

主要：保存文章、审核文章、点赞文章、使用Redis进行文章缓存、增加和删除文章品论、根据文章id查询评论列表

### 活动微服务：tensquare_gathering

活动的增删查改、活动报名。使用SpringCache进行活动缓存

### 吐槽微服务：tensquare_spit

基本增删改查、根据上级id查找吐槽列表、发布吐槽、吐槽点赞、浏览量

### 搜索微服务：tensquare_search 

使用Elasticsearch完成文章搜索，使用Logstash将MySQL数据导入Elasticsearch

### 用户微服务：tensquare_user

用户注册：手机注册---使用阿里云短信通过RabbitMQ发送给用户，邮箱注册---使用RabbitMQ发送邮件给用户

用户登录：签发JWT，使用JWT进行认证和鉴权

粉丝数变更：交友微服务传入

### 消费者微服务：tensquare_consumer

主要消费RabbitMQ中的消息

### 交友微服务：tensquare_friend

添加好友，同时更新用户粉丝数、添加非好友、删除好友

### 配置中心：tensquare_config

将配置文件提交到码云集中管理，通过消息总线SpringCloudBus

### 前台网关：tensquare_web

路由转发、路由过滤

### 后台网关：tensquare_manager

路由转发、比前台更加严格的路由过滤

### 文章爬虫微服务：tensquare_article_crawler

爬取CSDN上的文章

### 用户爬虫微服务：tensquare_user_crawler

爬取每篇文章的用户信息

### 人工智能微服务：tensquare_ai

利用IK分词器对文章进行分词，再使用Deeplearning4j构建词向量，构建卷积神经网络模型进行训练

### 基础微服务：tensquare_base

主要标签操作功能，如Java、C++等标签。



