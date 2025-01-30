package com.lhv.test.common;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseAuditableEntity {

    @CreationTimestamp
    @Column(name = "createddtime", nullable = false, updatable = false)
    private LocalDateTime createddtime;

    @UpdateTimestamp
    @Column(name = "modifieddtime", nullable = false)
    private LocalDateTime modifieddtime;
}