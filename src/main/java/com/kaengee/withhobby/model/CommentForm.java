package com.kaengee.withhobby.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    private String text;
    private Long postId;
    private Long userId;
}
