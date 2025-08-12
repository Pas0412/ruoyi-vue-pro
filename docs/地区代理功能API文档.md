# 地区代理功能 API 文档

## 概述

地区代理功能是一个完整的多级分销系统，支持省、市、县三级地区代理的申请、审核、佣金管理和提现功能。

## 用户端接口 (APP)

### 1. 地区代理管理接口

#### 1.1 申请成为地区代理

- **接口路径**: `POST /product/regional-agent/create`
- **接口描述**: 用户申请成为指定地区的代理
- **权限要求**: 需要用户登录认证
- **请求参数**: 
  ```json
  {
    "areaId": 110100,        // 地区编号
    "areaType": 3,           // 地区类型（2-省份，3-城市，4-地区）
    "areaName": "北京市"      // 地区名称
  }
  ```
- **响应结果**: 返回申请记录ID
  ```json
  {
    "code": 0,
    "data": 1024,
    "msg": "操作成功"
  }
  ```

#### 1.2 获取地区代理信息

- **接口路径**: `GET /product/regional-agent/get`
- **接口描述**: 根据ID获取地区代理详细信息
- **权限要求**: 需要用户登录认证
- **请求参数**: 
  - `id`: 地区代理编号（必填）
- **响应结果**: 
  ```json
  {
    "code": 0,
    "data": {
      "id": 1024,
      "userId": 1001,
      "areaId": 110100,
      "areaType": 3,
      "areaName": "北京市",
      "status": 2,
      "applyTime": "2024-01-01 10:00:00",
      "auditTime": "2024-01-02 14:30:00",
      "brokeragePrice": 50000,
      "frozenBrokeragePrice": 10000,
      "agentTime": "2024-01-02 14:30:00",
      "createTime": "2024-01-01 10:00:00"
    },
    "msg": "操作成功"
  }
  ```

#### 1.3 分页查询地区代理

- **接口路径**: `GET /product/regional-agent/page`
- **接口描述**: 分页获取地区代理列表
- **权限要求**: 需要用户登录认证
- **请求参数**: 
  - `pageNo`: 页码（默认1）
  - `pageSize`: 每页数量（默认10）
  - `areaId`: 地区编号（可选）
  - `areaType`: 地区类型（可选）
  - `areaName`: 地区名称（可选）
  - `status`: 代理状态（可选）
  - `applyTime`: 申请时间范围（可选）
  - `auditTime`: 审核时间范围（可选）
- **响应结果**: 分页的地区代理列表

#### 1.4 获取当前用户的地区代理

- **接口路径**: `GET /product/regional-agent/get-by-user`
- **接口描述**: 获取当前登录用户的已通过的地区代理信息
- **权限要求**: 需要用户登录认证
- **请求参数**: 无（从登录用户获取）
- **响应结果**: 用户的地区代理信息

### 2. 地区代理提现接口

#### 2.1 申请提现

- **接口路径**: `POST /product/regional-agent-withdraw/create`
- **接口描述**: 地区代理申请提现佣金
- **权限要求**: 需要用户登录认证
- **请求参数**: 
  ```json
  {
    "price": 10000,                    // 提现金额（分）
    "type": 2,                         // 提现类型（1-钱包，2-银行卡，3-微信，4-支付宝）
    "realName": "张三",                // 真实姓名
    "accountNo": "6222021234567890",   // 账号
    "qrCodeUrl": "",                   // 收款码（微信/支付宝）
    "bankName": "中国银行",             // 银行名称
    "bankAddress": "北京分行",          // 开户地址
    "remark": "提现申请"                // 备注
  }
  ```
- **响应结果**: 返回提现申请ID

#### 2.2 获取提现记录

- **接口路径**: `GET /product/regional-agent-withdraw/get`
- **接口描述**: 根据ID获取提现记录详情
- **权限要求**: 需要用户登录认证
- **请求参数**: 
  - `id`: 提现记录编号（必填）
- **响应结果**: 提现记录详细信息

#### 2.3 分页查询提现记录

- **接口路径**: `GET /product/regional-agent-withdraw/page`
- **接口描述**: 分页获取当前用户的提现记录
- **权限要求**: 需要用户登录认证
- **请求参数**: 
  - `pageNo`: 页码（默认1）
  - `pageSize`: 每页数量（默认10）
  - `type`: 提现类型（可选）
  - `status`: 状态（可选）
  - `createTime`: 创建时间范围（可选）
- **响应结果**: 分页的提现记录列表

### 3. 地区代理佣金记录接口

#### 3.1 获取佣金记录

- **接口路径**: `GET /product/regional-agent-record/get`
- **接口描述**: 根据ID获取佣金记录详情
- **权限要求**: 需要用户登录认证
- **请求参数**: 
  - `id`: 记录编号（必填）
- **响应结果**: 佣金记录详细信息

#### 3.2 分页查询佣金记录

- **接口路径**: `GET /product/regional-agent-record/page`
- **接口描述**: 分页获取当前用户的佣金记录
- **权限要求**: 需要用户登录认证
- **请求参数**: 
  - `pageNo`: 页码（默认1）
  - `pageSize`: 每页数量（默认10）
  - `bizId`: 业务编号（可选）
  - `bizType`: 业务类型（可选）
  - `status`: 状态（可选）
  - `createTime`: 创建时间范围（可选）
- **响应结果**: 分页的佣金记录列表

## 管理后台接口 (Admin)

### 1. 地区代理管理接口

#### 1.1 创建地区代理

- **接口路径**: `POST /product/regional-agent/create`
- **接口描述**: 管理员手动创建地区代理
- **权限要求**: `product:regional-agent:create`
- **请求参数**: 
  ```json
  {
    "userId": 1001,          // 用户编号
    "areaId": 110100,        // 地区编号
    "areaType": 3,           // 地区类型
    "areaName": "北京市",     // 地区名称
    "auditRemark": "管理员创建" // 审核备注
  }
  ```

#### 1.2 更新地区代理

- **接口路径**: `PUT /product/regional-agent/update`
- **接口描述**: 更新地区代理信息
- **权限要求**: `product:regional-agent:update`
- **请求参数**: 
  ```json
  {
    "id": 1024,             // 编号
    "areaId": 110100,       // 地区编号
    "areaType": 3,          // 地区类型
    "areaName": "北京市",    // 地区名称
    "auditRemark": "更新信息" // 审核备注
  }
  ```

#### 1.3 删除地区代理

- **接口路径**: `DELETE /product/regional-agent/delete`
- **接口描述**: 删除地区代理
- **权限要求**: `product:regional-agent:delete`
- **请求参数**: 
  - `id`: 代理编号（必填）

#### 1.4 审核地区代理申请

- **接口路径**: `PUT /product/regional-agent/approve`
- **接口描述**: 审核地区代理申请（通过/拒绝）
- **权限要求**: `product:regional-agent:approve`
- **请求参数**: 
  ```json
  {
    "id": 1024,                    // 代理编号
    "status": 1,                   // 审核状态（1-通过，2-拒绝）
    "auditRemark": "审核通过"       // 审核备注
  }
  ```

#### 1.5 获取地区代理

- **接口路径**: `GET /product/regional-agent/get`
- **接口描述**: 根据ID获取地区代理详情
- **权限要求**: `product:regional-agent:query`
- **请求参数**: 
  - `id`: 代理编号（必填）

#### 1.6 分页查询地区代理

- **接口路径**: `GET /product/regional-agent/page`
- **接口描述**: 分页查询地区代理列表
- **权限要求**: `product:regional-agent:query`
- **请求参数**: 支持多种筛选条件

#### 1.7 导出地区代理

- **接口路径**: `GET /product/regional-agent/export`
- **接口描述**: 导出地区代理数据
- **权限要求**: `product:regional-agent:export`

### 2. 地区代理提现管理接口

#### 2.1 审核提现申请

- **接口路径**: `PUT /product/regional-agent-withdraw/approve`
- **接口描述**: 审核地区代理提现申请
- **权限要求**: `product:regional-agent-withdraw:approve`
- **请求参数**: 
  ```json
  {
    "id": 1024,                    // 提现编号
    "status": 2,                   // 状态（2-审核通过，3-审核不通过）
    "auditReason": "审核通过"       // 审核原因
  }
  ```

#### 2.2 分页查询提现记录

- **接口路径**: `GET /product/regional-agent-withdraw/page`
- **接口描述**: 管理员分页查询所有提现记录
- **权限要求**: `product:regional-agent-withdraw:query`

### 3. 地区代理佣金记录管理接口

#### 3.1 查询佣金记录

- **接口路径**: `GET /product/regional-agent-record/get`
- **接口描述**: 根据ID获取佣金记录
- **权限要求**: `product:regional-agent-record:query`

#### 3.2 分页查询佣金记录

- **接口路径**: `GET /product/regional-agent-record/page`
- **接口描述**: 管理员分页查询所有佣金记录
- **权限要求**: `product:regional-agent-record:query`

#### 3.3 导出佣金记录

- **接口路径**: `GET /product/regional-agent-record/export`
- **接口描述**: 导出佣金记录数据
- **权限要求**: `product:regional-agent-record:export`

## 枚举值说明

### 地区代理状态 (RegionalAgentStatusEnum)

- `0`: 申请中
- `1`: 已通过
- `2`: 已拒绝
- `3`: 已禁用

### 地区类型 (AreaTypeEnum)

- `2`: 省份
- `3`: 城市
- `4`: 地区

### 业务类型 (RegionalAgentRecordBizTypeEnum)

- `1`: 订单
- `2`: 提现
- `3`: 退款
- `4`: 手动调整

### 记录状态 (RegionalAgentRecordStatusEnum)

- `1`: 待结算
- `2`: 已结算
- `3`: 已失效

### 提现类型 (RegionalAgentWithdrawTypeEnum)

- `1`: 钱包
- `2`: 银行卡
- `3`: 微信
- `4`: 支付宝

### 提现状态 (RegionalAgentWithdrawStatusEnum)

- `1`: 审核中
- `2`: 审核通过
- `3`: 审核不通过
- `4`: 提现成功
- `5`: 提现失败

## 核心功能特性

1. **多级分佣**: 支持省、市、县三级地区代理的多级佣金分配
2. **申请审核**: 完整的申请-审核-通过/拒绝流程
3. **佣金管理**: 佣金冻结、解冻、结算机制
4. **提现功能**: 支持多种提现方式（钱包、银行卡、微信、支付宝）
5. **权限控制**: 基于RBAC的细粒度权限控制
6. **数据统计**: 完整的佣金记录和提现记录追踪
7. **定时任务**: 自动解冻佣金的定时任务

## 数据库表结构

### 地区代理表 (product_regional_agent)

主要字段：
- `id`: 编号
- `user_id`: 用户编号
- `area_id`: 地区编号
- `area_type`: 地区类型
- `area_name`: 地区名称
- `status`: 代理状态
- `apply_time`: 申请时间
- `audit_time`: 审核时间
- `audit_user_id`: 审核人
- `audit_remark`: 审核备注
- `brokerage_price`: 可用佣金
- `frozen_brokerage_price`: 冻结佣金
- `agent_time`: 成为代理时间

### 地区代理记录表 (product_regional_agent_record)

主要字段：
- `id`: 编号
- `user_id`: 用户编号
- `biz_id`: 业务编号
- `biz_type`: 业务类型
- `title`: 标题
- `description`: 说明
- `price`: 金额
- `total_price`: 当前总佣金
- `status`: 状态
- `frozen_time`: 冻结时间
- `unfreeze_time`: 解冻时间
- `source_user_level`: 来源用户等级
- `source_user_id`: 来源用户编号

### 地区代理提现表 (product_regional_agent_withdraw)

主要字段：
- `id`: 编号
- `user_id`: 用户编号
- `price`: 提现金额
- `fee_price`: 手续费
- `total_price`: 总佣金
- `type`: 提现类型
- `real_name`: 真实姓名
- `account_no`: 账号
- `qr_code_url`: 收款码
- `bank_name`: 银行名称
- `bank_address`: 开户地址
- `status`: 状态
- `audit_reason`: 审核驳回原因
- `audit_time`: 审核时间
- `audit_user_id`: 审核人
- `remark`: 备注

## 注意事项

1. 所有接口都遵循RESTful设计规范
2. 支持标准的HTTP状态码和统一的响应格式
3. 金额单位统一使用分（避免浮点数精度问题）
4. 时间格式统一使用 `yyyy-MM-dd HH:mm:ss`
5. 分页参数：`pageNo`（页码，从1开始），`pageSize`（每页数量）
6. 所有需要认证的接口都需要在请求头中携带有效的访问令牌
7. 管理后台接口需要相应的权限才能访问

## 错误码说明

- `0`: 操作成功
- `500`: 系统异常
- `401`: 未授权
- `403`: 权限不足
- `400`: 参数错误
- `404`: 资源不存在

具体的业务错误码请参考系统错误码管理模块。