package com.bob.core.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.bob.commontools.pojo.enums.YesOrNo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 类名：MyMetaObjectHandler
 * <p>
 * 描述：
 * 功能：新增/更新某些字段自动添加
 *
 * @author TC
 * @version Ver 1.0
 * @date 2021/9/3 10:24
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("--------MybatisPlus Auto Insert Default Value");
        this.setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("revision", 0, metaObject);
        this.setFieldValByName("delFlag", YesOrNo.NO.type, metaObject);
        // this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("--------MybatisPlus Auto Update Default Value");
        this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
    }
}
