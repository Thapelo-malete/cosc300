package com.cosc300.suicidal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PsychologistDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detailsId;
    private String position;
    private String office;
    private String telephone;
    private String fax;
    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User psychologist;

}
