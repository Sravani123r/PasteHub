package com.pastebin.pastebin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "pastes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paste {

    @Id
    private String id;

    @Lob
    @Column(nullable = false)
    private String content;

    private Instant createdAt;
    private Instant expiresAt;
    private Integer maxViews;
    private int viewCount;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
        this.viewCount = 0;
    }
}
