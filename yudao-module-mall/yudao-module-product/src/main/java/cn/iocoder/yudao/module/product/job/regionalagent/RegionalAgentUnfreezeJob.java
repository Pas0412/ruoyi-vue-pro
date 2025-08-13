package cn.iocoder.yudao.module.product.job.regionalagent;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.product.service.regionalagent.RegionalAgentRecordService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 地区代理佣金解冻 Job
 *
 * @author 芋道源码
 */
@Component
public class RegionalAgentUnfreezeJob implements JobHandler {

    private static final Logger log = LoggerFactory.getLogger(RegionalAgentUnfreezeJob.class);

    @Resource
    private RegionalAgentRecordService regionalAgentRecordService;

    @Override
    @TenantJob
    public String execute(String param) throws Exception {
        int count = regionalAgentRecordService.unfreezeRecord();
        log.info("[execute][解冻地区代理佣金数量({})]", count);
        return String.format("解冻地区代理佣金数量(%s)", count);
    }

}