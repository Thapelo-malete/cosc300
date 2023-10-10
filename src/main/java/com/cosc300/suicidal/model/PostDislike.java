package com.cosc300.suicidal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDislike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dislikeId;

    @ManyToOne
    private User author;

    @ManyToOne
    private Post post;
}
