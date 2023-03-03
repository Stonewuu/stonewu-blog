package com.stonewu.blog.core.entity.custom;

import com.stonewu.blog.core.entity.ArticleTag;

public class TagCountInfo extends ArticleTag {
    private Integer artCount;

    public Integer getArtCount() {
        return artCount;
    }

    public void setArtCount(Integer artCount) {
        this.artCount = artCount;
    }

    @Override
    public String toString() {
        return "TagCountInfo{" +
                "artCount=" + artCount +
                '}';
    }
}
