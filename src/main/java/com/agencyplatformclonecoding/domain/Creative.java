package com.agencyplatformclonecoding.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "bidingPrice"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Creative extends AuditingFields {

    @Id
    @Column(name = "CREATIVE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 다대일 양방향
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "CAMPAIGN_ID")
    private Campaign campaign; // 에이전시 정보 (ID)

    @Setter
    @Column
    private String keyword;
    @Setter
    @Column
    private Long bidingPrice;
    @Setter
    @Column(length = 100)
    private String description;
    @Setter
    @Column
    private String url;
    @Setter
    @Column
    private Long view;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "creative", cascade = CascadeType.ALL)
    private final Set<Performance> performances = new LinkedHashSet<>();

    @Setter
    @Column
    private boolean activated;

    @Setter
    @Column
    private boolean deleted;

    protected Creative() {
    }

    private Creative(Campaign campaign, String keyword, Long bidingPrice, String description, String url) {
        this.campaign = campaign;
        this.keyword = keyword;
        this.bidingPrice = bidingPrice;
        this.description = description;
        this.url = url;
        this.deleted = false;
    }

    public static Creative of(Campaign campaign, String keyword, Long bidingPrice, String description, String url) {
        return new Creative(campaign, keyword, bidingPrice, description, url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creative that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
