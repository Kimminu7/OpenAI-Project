package com.example.project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExploreDTO {
    private String email;
    private String name;
    private String content;
    private int hit;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private String memberEmail;
    private String memberName;


}
