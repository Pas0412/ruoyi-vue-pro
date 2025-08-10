package cn.iocoder.yudao.module.trade.job.regionalagent;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.trade.service.regionalagent.RegionalAgentRecordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 地区代理佣金解冻 Job
 *
 * @author 芋道源码
 */
@Component
public class RegionalAgentRecordUnfreezeJob implements JobHandler {

    @Resource
    private RegionalAgentRecordService regionalAgentRecordService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = regionalAgentRecordService.unfreezeRecord();
        return StrUtil.format("解冻地区代理佣金 {} 个", count);
    }

}