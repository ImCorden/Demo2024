package com.bob.seataDemo.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>
 * Seata表用来实现分布式事务
 * </p>
 *
 * @author Bob
 * @since 2025-01-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("seata_demo")
@Schema(name = "SeataDemo", description = "Seata表用来实现分布式事务")
public class SeataDemo implements Serializable {

    @Serial
    private static final long serialVersionUID = 7170278733422158746L;

    @Schema(description = "主键")
    @TableId("id")
    private Long id;

    @Schema(description = "名字")
    @TableField("name")
    private String name;

    @Schema(description = "年龄")
    @TableField("age")
    private Integer age;

    @Schema(description = "锁定标识(0未锁定，1锁定)")
    @TableField("lock_flag")
    private String lockFlag;

    @Schema(description = "乐观锁")
    @TableField(value = "revision", fill = FieldFill.INSERT_UPDATE)
    @Version
    private Integer revision;

    @Schema(description = "创建人")
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;

    @Schema(description = "创建时间")
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime createdTime;

    @Schema(description = "更新人")
    @TableField(value = "updated_by", fill = FieldFill.UPDATE)
    private Long updatedBy;

    @Schema(description = "更新时间")
    @TableField(value = "updated_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime updatedTime;

    @Schema(description = "删除标识(0未删除，1删除)")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;
}
