package com.naja.analyticsmicroservice.dependency;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment{

    private Long id;
    private String content;
    @JsonBackReference
    private Comment parentComment;
    @JsonManagedReference
    private Set<Comment> comments = new HashSet<>();
    @JsonManagedReference

    public void addComment( String content){
        this.content = content;
    }

    public void deleteComment(){
        this.content = "deleted";      
    }

}
