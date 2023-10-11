package com.capstone.joonggo.search;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PostSearch {

    private String title;
    private Sort.Direction sortBy;

    public PostSearch(String title) {
        this.title = title;
        this.sortBy = Sort.Direction.DESC;
    }
}
