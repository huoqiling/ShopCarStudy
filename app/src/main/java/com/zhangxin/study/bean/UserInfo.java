package com.zhangxin.study.bean;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable(name = "天龙八部")
public class UserInfo {
    
    @SmartColumn(id = 0, name = "姓名", autoMerge = true)
    private String name;
    @SmartColumn(id = 1, name = "绝技")
    private String skill;
    @SmartColumn(id = 2, name = "年龄")
    private int age;

    public UserInfo(String name, String skill, int age) {
        this.name = name;
        this.skill = skill;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSkill() {
        return skill;
    }

    public int getAge() {
        return age;
    }
}
