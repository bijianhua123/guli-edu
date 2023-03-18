package com.bijianhua.guli.service.cms.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.cms.entity.Ad;
import com.bijianhua.guli.service.cms.entity.vo.AdVo;
import com.bijianhua.guli.service.cms.feign.OssFileService;
import com.bijianhua.guli.service.cms.mapper.AdMapper;
import com.bijianhua.guli.service.cms.service.AdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 广告推荐 服务实现类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-16
 */
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements AdService {


    @Autowired
    private OssFileService ossFileService;

    @Override
    public IPage<AdVo> selectPage(Long page, Long limit) {
        QueryWrapper<AdVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("a.type_id", "a.sort");

        Page<AdVo> adVoPage = new Page<>(page, limit);
        List<AdVo> adVoList = baseMapper.selectPageByAsc(adVoPage, queryWrapper);
        adVoPage.setRecords(adVoList);
        return adVoPage;
    }

    @Override
    public boolean removeAdImageById(String id) {
        Ad ad = baseMapper.selectById(id);
        if (ad != null) {
            String imagesUrl = ad.getImageUrl();
            if (!StringUtils.isEmpty(imagesUrl)) {
                //删除图片
                R r = ossFileService.removeAvatarById(imagesUrl);
                return r.getSuccess();
            }
        }
        return false;
    }

    @Override
    public List<Ad> selectByAdTypeId(String adTypeId) {
        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort", adTypeId);
        queryWrapper.eq("type_id", adTypeId);
        return baseMapper.selectList(queryWrapper);
    }
}
