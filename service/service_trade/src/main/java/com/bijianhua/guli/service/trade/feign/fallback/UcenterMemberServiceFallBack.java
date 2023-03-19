package com.bijianhua.guli.service.trade.feign.fallback;

import com.bijianhua.guli.service.base.dto.MemberDto;
import com.bijianhua.guli.service.trade.feign.UcenterMemberService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author bijianhua
 * @since 2023/3/19
 */
@Service
@Slf4j
public class UcenterMemberServiceFallBack implements UcenterMemberService {
    @Override
    public MemberDto getMemberDtoByMemberId(String memberId) {
        log.error("熔断保护");
        return null;
    }
}
