package com.bijianhua.guli.service.cms.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.cms.entity.AdType;
import com.bijianhua.guli.service.cms.service.AdTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 推荐位 前端控制器
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-16
 */
@CrossOrigin
@Slf4j
@Api(tags = "推荐位管理")
@RestController
@RequestMapping("admin/cms/ad-type")
public class AdTypeController {

    @Autowired
    private AdTypeService adTypeService;


    @ApiOperation("所有推荐类列表")
    @GetMapping("list")
    public R listAll() {
        List<AdType> list = adTypeService.list();
        return R.ok().data("items", list);
    }

    @ApiOperation("推荐列表分页展示")
    @GetMapping("list/{page}/{limit}")
    public R listPage(
            @ApiParam(value = "当前页数", required = true)
            @PathVariable Long page,
            @ApiParam(value = "每页显示条数", required = true)
            @PathVariable Long limit) {
        Page<AdType> adTypePage = new Page<>(page, limit);
        IPage<AdType> typePage = adTypeService.page(adTypePage);
        long total = typePage.getTotal();
        List<AdType> adTypeList = typePage.getRecords();
        return R.ok().data("rows", adTypeList).data("total", total);
    }

    @ApiOperation("根据ID删除推荐位")
    @DeleteMapping("remove/{adTypeId}")
    public R removeById(
            @ApiParam(value = "推荐位Id", required = true)
            @PathVariable String adTypeId) {
        boolean result = adTypeService.removeById(adTypeId);
        if (!result) {
            return R.error().message("删除失败");
        }
        return R.ok().message("删除成功");
    }

    @ApiOperation("更新推荐信息")
    @PutMapping("update")
    public R update(
            @ApiParam(value = "推荐对象", required = true)
            @RequestBody AdType adType) {
        boolean result = adTypeService.updateById(adType);
        if (!result) {
            return R.error().message("更新失败");
        }
        return R.ok().message("更新成功");
    }

    @ApiOperation("根据Id获取推荐类别信息")
    @GetMapping("get/{adTypeId}")
    public R getById(
            @ApiParam(value = "推荐类别Id", required = true)
            @PathVariable String adTypeId) {
        AdType adType = adTypeService.getById(adTypeId);
        if (adType == null) {
            return R.error().message("获取信息失败");
        }
        return R.ok().data("item", adType);
    }

    @ApiOperation("新增推荐类别")
    @PostMapping("save")
    public R save(
            @ApiParam(value = "推荐对象", required = true)
            @RequestBody AdType adType) {
        boolean result = adTypeService.save(adType);
        if (!result) {
            return R.error().message("添加失败");
        }
        return R.ok().message("添加成功");

    }

}

