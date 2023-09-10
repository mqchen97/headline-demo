package com.atguigu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Type;
import com.atguigu.service.TypeService;
import com.atguigu.mapper.TypeMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2023-09-09 23:53:04
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

}




