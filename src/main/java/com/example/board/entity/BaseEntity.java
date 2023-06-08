package com.example.board.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {   // 작성시간과 수정시간을 가지고 있는 Entity 다른 엔티티에서 상속받아서 사용
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;    // 작성시간

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;    // 수정시간
}
