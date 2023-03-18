package com.bijianhua.guli.service.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bijianhua.guli.service.cms.entity.Ad;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bijianhua.guli.service.cms.entity.vo.AdVo;

import java.util.List;

/**
 * <p>
 * 广告推荐 服务类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-16
 */
public interface AdService extends IService<Ad> {

    /**
     * 广告推荐位分页查询
     *
     * @param page  当前页
     * @param limit 每页显示条数
     * @return
     */
    IPage<AdVo> selectPage(Long page, Long limit);

    /**
     * 根据id删除推荐推片
     *
     * @param id
     */
    boolean removeAdImageById(String id);

    /**
     * 根据推荐位类型id获取推荐位信息
     *
     * @param adTypeId 推荐位id
     * @return
     */
    List<Ad> selectByAdTypeId(String adTypeId);
}
