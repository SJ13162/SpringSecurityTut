package com.learning.simran.prod_ready_feature_tutorial.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class PostEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @NotAudited
    private String description;

    //trigger before entry added to DB
    @PrePersist
    void beforeSave(){

    }

    //trigger before updating entity in db
    @PreUpdate
    void beforeUpdate(){

    }

    //trigger before remove entity in db
    @PreRemove
    void beforeDelete(){

    }
}
