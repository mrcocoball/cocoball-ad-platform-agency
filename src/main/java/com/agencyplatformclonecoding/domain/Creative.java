package com.agencyplatformclonecoding.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

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
    @JoinColumn(name = "CAMPAIGN_ID") private Campaign campaign; // 에이전시 정보 (ID)

    @Setter @Column private String keyword;
    @Setter @Column private long bidingPrice;
    @Setter @Column private long view;
    @Setter @Column private long click;
    @Setter @Column private long conversion;

    protected Creative() {}

    private Creative(Campaign campaign, String keyword, long bidingPrice) {
        this.campaign = campaign;
        this.keyword = keyword;
        this.bidingPrice = bidingPrice;
    }

    public static Creative of(Campaign campaign, String keyword, long bidingPrice) {
        return new Creative(campaign, keyword, bidingPrice);
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
