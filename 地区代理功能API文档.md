# 地区代理功能 API 文档

## 概述

地区代理功能为商城系统提供了完整的地区代理管理体系，包括代理申请、审核、佣金管理、提现等功能。系统分为管理后台和用户端两套API，满足不同角色的使用需求。

## API 访问地址

- **Swagger UI**: `http://localhost:48080/swagger-ui`
- **OpenAPI JSON**: `http://localhost:48080/v3/api-docs`

## 功能模块

### 1. 管理后台 API (`/admin-api/trade/regional-agent/`)

#### 1.1 地区代理管理

##### 获取地区代理分页列表
- **接口**: `GET /admin-api/trade/regional-agent/page`
- **描述**: 分页查询地区代理列表
- **请求参数**: `RegionalAgentPageReqVO`
  - `userId` (Long): 用户编号
  - `provinceId` (Integer): 省份编号
  - `cityId` (Integer): 城市编号
  - `areaId` (Integer): 区县编号
  - `agentLevel` (Integer): 代理级别 (1-省级, 2-市级, 3-区县级)
  - `status` (Integer): 状态 (0-待审核, 1-审核通过, 2-审核拒绝, 3-已禁用)
  - `applyTime` (LocalDateTime[]): 申请时间范围
  - `auditTime` (LocalDateTime[]): 审核时间范围
- **响应**: `PageResult<RegionalAgentRespVO>`

##### 获取地区代理详情
- **接口**: `GET /admin-api/trade/regional-agent/get`
- **描述**: 根据编号获取地区代理详情
- **请求参数**: `id` (Long) - 代理编号
- **响应**: `RegionalAgentRespVO`

##### 审核地区代理
- **接口**: `PUT /admin-api/trade/regional-agent/audit`
- **描述**: 审核地区代理申请
- **请求参数**: `RegionalAgentAuditReqVO`
  - `id` (Long): 代理编号
  - `auditStatus` (Integer): 审核状态 (1-审核通过, 2-审核拒绝)
  - `auditReason` (String): 审核原因
- **响应**: `CommonResult<Boolean>`

##### 更新地区代理状态
- **接口**: `PUT /admin-api/trade/regional-agent/update-status`
- **描述**: 更新地区代理状态（启用/禁用）
- **请求参数**: `RegionalAgentUpdateStatusReqVO`
  - `id` (Long): 代理编号
  - `status` (Integer): 状态 (1-启用, 3-禁用)
- **响应**: `CommonResult<Boolean>`

#### 1.2 佣金记录管理

##### 获取佣金记录分页列表
- **接口**: `GET /admin-api/trade/regional-agent/record/page`
- **描述**: 分页查询地区代理佣金记录
- **请求参数**: `RegionalAgentRecordPageReqVO`
  - `agentId` (Long): 代理编号
  - `userId` (Long): 用户编号
  - `bizType` (Integer): 业务类型
  - `status` (Integer): 状态
  - `agentLevel` (Integer): 代理级别
  - `provinceId` (Integer): 省份编号
  - `cityId` (Integer): 城市编号
  - `areaId` (Integer): 区县编号
  - `createTime` (LocalDateTime[]): 创建时间范围
- **响应**: `PageResult<RegionalAgentRecordRespVO>`

##### 获取佣金记录详情
- **接口**: `GET /admin-api/trade/regional-agent/record/get`
- **描述**: 根据编号获取佣金记录详情
- **请求参数**: `id` (Long) - 记录编号
- **响应**: `RegionalAgentRecordRespVO`

#### 1.3 提现管理

##### 获取提现申请分页列表
- **接口**: `GET /admin-api/trade/regional-agent/withdraw/page`
- **描述**: 分页查询地区代理提现申请
- **请求参数**: `RegionalAgentWithdrawPageReqVO`
  - `agentId` (Long): 代理编号
  - `userId` (Long): 用户编号
  - `type` (Integer): 提现类型
  - `name` (String): 真实姓名
  - `accountNo` (String): 账号
  - `bankName` (String): 银行名称
  - `status` (Integer): 状态
  - `createTime` (LocalDateTime[]): 创建时间范围
- **响应**: `PageResult<RegionalAgentWithdrawRespVO>`

##### 获取提现申请详情
- **接口**: `GET /admin-api/trade/regional-agent/withdraw/get`
- **描述**: 根据编号获取提现申请详情
- **请求参数**: `id` (Long) - 提现编号
- **响应**: `RegionalAgentWithdrawRespVO`

##### 审核提现申请
- **接口**: `PUT /admin-api/trade/regional-agent/withdraw/audit`
- **描述**: 审核地区代理提现申请
- **请求参数**: `RegionalAgentWithdrawAuditReqVO`
  - `id` (Long): 提现编号
  - `auditStatus` (Integer): 审核状态 (20-审核通过, 30-审核拒绝)
  - `auditReason` (String): 审核原因
- **响应**: `CommonResult<Boolean>`

### 2. 用户端 API (`/app-api/trade/regional-agent/`)

#### 2.1 地区代理功能

##### 获取个人代理信息
- **接口**: `GET /app-api/trade/regional-agent/get`
- **描述**: 获取当前用户的地区代理信息
- **请求参数**: 无（从登录用户获取）
- **响应**: `AppRegionalAgentRespVO`
  - `id` (Long): 代理编号
  - `isAgent` (Boolean): 是否是地区代理
  - `agentLevel` (Integer): 代理级别
  - `status` (Integer): 代理状态
  - `availablePrice` (Integer): 可用佣金（分）
  - `frozenPrice` (Integer): 冻结佣金（分）
  - `provinceId` (Integer): 省份编号
  - `provinceName` (String): 省份名称
  - `cityId` (Integer): 城市编号
  - `cityName` (String): 城市名称
  - `areaId` (Integer): 区县编号
  - `areaName` (String): 区县名称

##### 申请成为地区代理
- **接口**: `POST /app-api/trade/regional-agent/apply`
- **描述**: 用户申请成为地区代理
- **请求参数**: `AppRegionalAgentApplyReqVO`
  - `provinceId` (Integer): 省份编号（必填）
  - `cityId` (Integer): 城市编号（必填）
  - `areaId` (Integer): 区县编号（必填）
  - `agentLevel` (Integer): 代理级别（必填，1-省级, 2-市级, 3-区县级）
- **响应**: `CommonResult<Boolean>`

#### 2.2 佣金记录功能

##### 获取个人佣金记录
- **接口**: `GET /app-api/trade/regional-agent/record/page`
- **描述**: 分页查询当前用户的佣金记录
- **请求参数**: `AppRegionalAgentRecordPageReqVO`
  - `bizType` (Integer): 业务类型
  - `status` (Integer): 状态
  - `createTime` (LocalDateTime[]): 创建时间范围
- **响应**: `PageResult<AppRegionalAgentRecordRespVO>`
  - `id` (Long): 记录编号
  - `bizType` (Integer): 业务类型
  - `title` (String): 标题
  - `price` (Integer): 金额（分）
  - `totalPrice` (Integer): 当前总佣金（分）
  - `description` (String): 说明
  - `status` (Integer): 状态
  - `unfreezeTime` (LocalDateTime): 解冻时间
  - `createTime` (LocalDateTime): 创建时间

## 三、地区代理配置管理 API

### 1. 管理后台配置 API (`/admin-api/trade/config/`)

#### 1.1 获取交易配置
- **接口**: `GET /admin-api/trade/config/get`
- **描述**: 获取交易中心配置信息，包括地区代理相关配置
- **权限**: `trade:config:query`
- **请求参数**: 无
- **响应**: `TradeConfigRespVO`
  - `id` (Long): 配置编号
  - `regionalAgentEnabled` (Boolean): 是否启用地区代理功能
  - `regionalAgentProvincePercent` (Integer): 省级代理佣金比例（0-100）
  - `regionalAgentCityPercent` (Integer): 市级代理佣金比例（0-100）
  - `regionalAgentDistrictPercent` (Integer): 县级代理佣金比例（0-100）
  - `regionalAgentWithdrawMinPrice` (Integer): 地区代理提现最低金额（分）
  - `regionalAgentWithdrawFeePercent` (Integer): 地区代理提现手续费百分比
  - `regionalAgentFrozenDays` (Integer): 地区代理佣金冻结时间（天）
  - `regionalAgentExcludeSpuIds` (String): 地区代理排除的商品SPU编号列表（逗号分隔）
  - 其他交易配置字段...

#### 1.2 保存交易配置
- **接口**: `PUT /admin-api/trade/config/save`
- **描述**: 保存交易中心配置信息，包括地区代理相关配置
- **权限**: `trade:config:save`
- **请求参数**: `TradeConfigSaveReqVO`
  - `regionalAgentEnabled` (Boolean): 是否启用地区代理功能（必填）
  - `regionalAgentProvincePercent` (Integer): 省级代理佣金比例（必填，0-100）
  - `regionalAgentCityPercent` (Integer): 市级代理佣金比例（必填，0-100）
  - `regionalAgentDistrictPercent` (Integer): 县级代理佣金比例（必填，0-100）
  - `regionalAgentWithdrawMinPrice` (Integer): 地区代理提现最低金额（必填，分）
  - `regionalAgentWithdrawFeePercent` (Integer): 地区代理提现手续费百分比（必填）
  - `regionalAgentFrozenDays` (Integer): 地区代理佣金冻结时间（必填，天）
  - `regionalAgentExcludeSpuIds` (String): 地区代理排除的商品SPU编号列表（可选，逗号分隔）
  - 其他交易配置字段...
- **响应**: `CommonResult<Boolean>`

#### 1.3 配置说明

##### 三级分佣比例配置
- **省级代理佣金比例**: 省级代理从订单中获得的佣金百分比
- **市级代理佣金比例**: 市级代理从订单中获得的佣金百分比  
- **县级代理佣金比例**: 县级代理从订单中获得的佣金百分比
- 比例范围：0-100，表示百分比
- 三级代理可同时获得佣金，互不冲突

##### 特殊商品排除功能
- **排除商品SPU编号列表**: 不参与地区代理分佣的商品SPU编号
- 格式：逗号分隔的数字字符串，如 "643,644,645"
- 配置后，这些商品的订单将不会产生地区代理佣金
- 示例：商品ID为643的特殊商品不参与代理分佣

##### 提现相关配置
- **提现最低金额**: 地区代理申请提现的最低金额限制（单位：分）
- **提现手续费百分比**: 提现时收取的手续费比例
- **佣金冻结时间**: 新产生的佣金需要冻结的天数，到期后可提现

#### 2.3 提现功能

##### 申请提现
- **接口**: `POST /app-api/trade/regional-agent/withdraw/create`
- **描述**: 创建地区代理提现申请
- **请求参数**: `AppRegionalAgentWithdrawCreateReqVO`
  - `price` (Integer): 提现金额（分，必填）
  - `type` (Integer): 提现类型（必填）
  - `name` (String): 真实姓名（必填）
  - `accountNo` (String): 收款账号（必填）
  - `bankName` (String): 银行名称
  - `bankAddress` (String): 开户地址
  - `accountQrCodeUrl` (String): 收款码
  - `remark` (String): 备注
- **响应**: `CommonResult<Long>` - 返回提现申请编号

##### 获取提现记录
- **接口**: `GET /app-api/trade/regional-agent/withdraw/page`
- **描述**: 分页查询当前用户的提现记录
- **请求参数**: `AppRegionalAgentWithdrawPageReqVO`
  - `type` (Integer): 提现类型
  - `status` (Integer): 状态
  - `createTime` (LocalDateTime[]): 创建时间范围
- **响应**: `PageResult<AppRegionalAgentWithdrawRespVO>`
  - `id` (Long): 提现编号
  - `price` (Integer): 提现金额（分）
  - `feePrice` (Integer): 手续费（分）
  - `totalPrice` (Integer): 当前总佣金（分）
  - `type` (Integer): 提现类型
  - `name` (String): 真实姓名
  - `accountNo` (String): 收款账号
  - `bankName` (String): 银行名称
  - `bankAddress` (String): 开户地址
  - `accountQrCodeUrl` (String): 收款码
  - `status` (Integer): 状态
  - `auditReason` (String): 审核原因
  - `auditTime` (LocalDateTime): 审核时间
  - `remark` (String): 备注
  - `createTime` (LocalDateTime): 创建时间

## 数据字典

### 代理级别 (agentLevel)
- `1`: 省级代理
- `2`: 市级代理
- `3`: 区县级代理

### 代理状态 (status)
- `0`: 待审核
- `1`: 审核通过
- `2`: 审核拒绝
- `3`: 已禁用

### 佣金记录状态
- `0`: 待结算
- `1`: 已结算
- `2`: 已取消

### 提现状态
- `0`: 审核中
- `10`: 审核通过
- `20`: 提现成功
- `30`: 审核拒绝
- `40`: 提现失败

### 提现类型
- `1`: 微信
- `2`: 支付宝
- `3`: 银行卡

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 1-010-001-000 | 地区代理不存在 |
| 1-010-001-001 | 用户已是地区代理 |
| 1-010-001-002 | 该地区已有代理 |
| 1-010-001-003 | 代理状态不允许此操作 |
| 1-010-001-004 | 佣金余额不足 |
| 1-010-001-005 | 提现金额超过限制 |

## 注意事项

1. **权限控制**: 管理后台API需要管理员权限，用户端API需要用户登录
2. **金额单位**: 所有金额字段均以分为单位
3. **地区限制**: 每个地区（省/市/区县）只能有一个对应级别的代理
4. **状态流转**: 代理状态按照 待审核 → 审核通过/拒绝 → 启用/禁用 的流程
5. **佣金冻结**: 新产生的佣金会有冻结期，到期后自动解冻
6. **提现限制**: 只能提现已解冻的佣金，需要通过审核才能到账

## 示例代码

### 申请成为地区代理
```javascript
// 前端调用示例
const applyAgent = async () => {
  const response = await fetch('/app-api/trade/regional-agent/apply', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    },
    body: JSON.stringify({
      provinceId: 110000,
      cityId: 110100,
      areaId: 110101,
      agentLevel: 3
    })
  });
  const result = await response.json();
  return result;
};
```

### 查询佣金记录
```javascript
// 前端调用示例
const getRecords = async (page = 1, size = 10) => {
  const response = await fetch(`/app-api/trade/regional-agent/record/page?pageNo=${page}&pageSize=${size}`, {
    headers: {
      'Authorization': 'Bearer ' + token
    }
  });
  const result = await response.json();
  return result;
};
```

### 申请提现
```javascript
// 前端调用示例
const createWithdraw = async (withdrawData) => {
  const response = await fetch('/app-api/trade/regional-agent/withdraw/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    },
    body: JSON.stringify({
      price: 10000, // 100元，以分为单位
      type: 1, // 微信提现
      name: '张三',
      accountNo: 'wx123456789',
      remark: '提现申请'
    })
  });
  const result = await response.json();
  return result;
};
```

---

**文档版本**: v1.0  
**最后更新**: 2024年  
**维护人员**: 开发团队