package com.zhangxin.study;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 食品类
 */
public class FoodBean implements Serializable {

    private String id;
    private String name;
    private String scale;
    private String isCommand;
    private BigDecimal price;
    private String cutPrice;
    private String type;
    private int iconId;
    private long selectCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getIsCommand() {
        return isCommand;
    }

    public void setIsCommand(String isCommand) {
        this.isCommand = isCommand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCutPrice() {
        return cutPrice;
    }

    public void setCutPrice(String cutPrice) {
        this.cutPrice = cutPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public long getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(long selectCount) {
        this.selectCount = selectCount;
    }

    public String getFoodPrice(){
        return "¥ " + price;
    }
}
