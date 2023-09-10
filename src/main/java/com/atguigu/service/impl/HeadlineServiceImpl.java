package com.atguigu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Headline;
import com.atguigu.service.HeadlineService;
import com.atguigu.mapper.HeadlineMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2023-09-09 23:53:04
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

}




