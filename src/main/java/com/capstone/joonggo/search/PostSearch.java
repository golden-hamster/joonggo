package com.capstone.joonggo.search;

import lombok.Data;

@Data
public class PostSearch {

    private String title;

    public PostSearch(String title) {
        this.title = title;
    }
}
