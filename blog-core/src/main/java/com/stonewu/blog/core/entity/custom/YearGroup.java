package com.stonewu.blog.core.entity.custom;

import java.io.Serializable;

/**
 * @author StoneWu
 */
public class YearGroup implements Serializable {

    private String year;

    private Integer count;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
