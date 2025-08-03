package cn.iocoder.yudao.module.member.job;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.member.service.user.MemberUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 会员过期清理 Job
 * 定期清理过期的会员，将过期会员的 member_expire_time 设置为 null
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class MemberExpireJob implements JobHandler {

    @Resource
    private MemberUserService memberUserService;

    @Override
    @TenantJob // 支持多租户
    public String execute(String param) {
        int count = memberUserService.cleanExpiredMembers();
        return StrUtil.format("清理过期会员 {} 个", count);
    }
}