package com.bijianhua.guli.service.edu;


import com.bijianhua.guli.service.edu.entity.vo.SubjectVo;
import com.bijianhua.guli.service.edu.mapper.SubjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private SubjectMapper subjectMapper;

    @Test
    public void test1() {
        List<SubjectVo> subjectVos = subjectMapper.selectNestedListByParentId("0");
        System.out.println(subjectVos);
    }

    @Test
    public void test2() {
        System.out.println(System.getProperty("user.dir"));
    }
}
