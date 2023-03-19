package com.bijianhua.guli.service.trade.feign;

import com.bijianhua.guli.service.base.dto.MemberDto;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author bijianhua
 * @since 2023/3/19
 */
@FeignClient(value = "service-ucenter")
public interface UcenterMemberService {
    @GetMapping("/api/ucenter/member/inner/get-member-dto/{memberId}")
    MemberDto getMemberDtoByMemberId(@PathVariable(value = "memberId") String memberId);
}
