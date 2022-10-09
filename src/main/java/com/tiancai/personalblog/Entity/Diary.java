package com.tiancai.personalblog.Entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Diary {
    @TableId(type = IdType.AUTO)
    Integer id;
    @TableField(fill= FieldFill.INSERT)
    Timestamp time;
    //描述
    String description;

    public Diary(String description) {
        this.description = description;
    }

}
