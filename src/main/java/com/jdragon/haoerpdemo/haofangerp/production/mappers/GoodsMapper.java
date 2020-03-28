package com.jdragon.haoerpdemo.haofangerp.production.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.haoerpdemo.haofangerp.production.domain.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Auther: kimid
 * @Date: 2020/3/27 09:38
 * @Description:
 */
@Mapper
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
}
