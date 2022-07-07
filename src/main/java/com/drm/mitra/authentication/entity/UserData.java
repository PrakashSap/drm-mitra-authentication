package com.drm.mitra.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_DATA")
@Data
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String password;
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private String role;
    private boolean active;
    private boolean disable;
    @Enumerated(EnumType.STRING)
    private Provider provider;
}
