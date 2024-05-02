package com.api.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data @Builder
public class UserListLombok {
    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}
