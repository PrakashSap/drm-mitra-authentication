package com.drm.mitra.authentication.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_DATA", uniqueConstraints = {@UniqueConstraint(name = "UniqueUserNamePasswordActive",columnNames = {"username","password","active"})})
@Data
public class UserData {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue
    @Column(name = "id", nullable = false,length = 16)
    private UUID id;
    @Column(name = "username",nullable = false)
    @NotBlank(message = "User Name Mandatory")
    private String username;
    @NotBlank(message = "User Password Mandatory")
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "role",nullable = false)
    @NotBlank(message = "User Role Mandatory")
    private String role;
    @Column(name = "active",nullable = false)
    @NotNull(message = "User status active Mandatory. Default will be true")
    private boolean active;
    @NotNull(message = "User status disable Mandatory. Default will be false")
    @Column(name = "disable", nullable = false)
    private boolean disable;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdOn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedOn;
}
