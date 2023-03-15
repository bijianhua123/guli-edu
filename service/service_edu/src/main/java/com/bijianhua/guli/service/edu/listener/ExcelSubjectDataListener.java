package com.bijianhua.guli.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bijianhua.guli.service.edu.entity.Subject;
import com.bijianhua.guli.service.edu.entity.excel.ExcelSubjectData;
import com.bijianhua.guli.service.edu.mapper.SubjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSubjectDataListener extends AnalysisEventListener<ExcelSubjectData> {


    //课程mapper对象 通过构造方法传入
    private SubjectMapper subjectMapper;


    @Override
    public void invoke(ExcelSubjectData data, AnalysisContext analysisContext) {
        log.info("解析到一条记录: {}", data);
        //1、获取一级类别
        String levelOneTitle = data.getLevelOneTitle();
        //2、获取二级类别
        String levelTwoTitle = data.getLevelTwoTitle();
        /*判断一级分类是否存在*/
        Subject subjectOneByTitle = getSubjectOneByTitle(levelOneTitle);

        //一级分类自己的id
        String oneId = null;
        //如果为null表示不存在
        if (subjectOneByTitle == null) {
            //3、组装一级类别
            Subject subjectOne = new Subject();
            subjectOne.setParentId("0");
            subjectOne.setTitle(levelOneTitle);
            subjectMapper.insert(subjectOne);
            oneId = subjectOne.getId();
        } else {
            oneId = subjectOneByTitle.getId();
        }
        /*判断二级分类是否在同一级目录下存在*/
        Subject subjectTwoByTitle = getSubjectTwoByTitle(levelTwoTitle, oneId);
        if (subjectTwoByTitle == null) {
            Subject subjectTwo = new Subject();
            subjectTwo.setParentId(oneId);
            subjectTwo.setTitle(levelTwoTitle);
            subjectMapper.insert(subjectTwo);
        }


    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("全部数据解析完成");
    }

    /**
     * 根据一级分类的名称判断一级分类
     * 是否存在
     *
     * @param title 一级分类名称
     * @return
     */
    private Subject getSubjectOneByTitle(String title) {
        LambdaQueryWrapper<Subject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Subject::getTitle, title);
        queryWrapper.eq(Subject::getParentId, "0");
        return subjectMapper.selectOne(queryWrapper);
    }

    /**
     * 根据二级分类的名称和二级分类的父id判断二级分类
     * 是否存在
     *
     * @param title 一级分类名称
     * @return
     */
    private Subject getSubjectTwoByTitle(String title, String parentId) {
        LambdaQueryWrapper<Subject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Subject::getTitle, title);
        queryWrapper.eq(Subject::getParentId, parentId);
        return subjectMapper.selectOne(queryWrapper);
    }
}
