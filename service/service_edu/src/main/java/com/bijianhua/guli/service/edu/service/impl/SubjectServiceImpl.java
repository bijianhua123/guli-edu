package com.bijianhua.guli.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.bijianhua.guli.service.edu.entity.Subject;
import com.bijianhua.guli.service.edu.entity.excel.ExcelSubjectData;
import com.bijianhua.guli.service.edu.entity.vo.SubjectVo;
import com.bijianhua.guli.service.edu.listener.ExcelSubjectDataListener;
import com.bijianhua.guli.service.edu.mapper.SubjectMapper;
import com.bijianhua.guli.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-08
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {


    @Override
    public void batchImport(InputStream inputStream) {
        //通过阿里巴巴的easyexcel对文件进行读取并写入数据库
        EasyExcel.read(inputStream, ExcelSubjectData.class, new ExcelSubjectDataListener(baseMapper))
                .excelType(ExcelTypeEnum.XLS).sheet().doRead();
    }


    @Override
    public List<SubjectVo> nestedList() {

        return baseMapper.selectNestedListByParentId("0");
    }
}
