package com.api.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data @Builder
public class UserLombok {
    private String name;
    private String job;
    private int id;
    private String createdAt;
    private String updatedAt;
}
