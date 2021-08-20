package com.com.blog.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUpdateDto {
    private String title;
    private String content;
}
