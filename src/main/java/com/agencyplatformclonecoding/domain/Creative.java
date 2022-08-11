package com.agencyplatformclonecoding.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "bidingPrice"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Creative extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CREATIVE_ID")
    private long id;

    // 다대일 양방향
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "CAMPAIGN_ID", insertable = false, updatable = false) private AdCampaign adCampaign; // 에이전시 정보 (ID) */

    @Setter @Column private String keyword;
    @Setter @Column private long bidingPrice;
    @Setter @Column private long view;
    @Setter @Column private long click;
    @Setter @Column private long conversion;

    protected Creative() {}

    private Creative(long id, AdCampaign adCampaign, String keyword, long bidingPrice) {
        this.id = id;
        this.adCampaign = adCampaign;
        this.keyword = keyword;
        this.bidingPrice = bidingPrice;
    }

    public static Creative of(long id, AdCampaign adCampaign, String keyword, long bidingPrice) {
        return new Creative(id, adCampaign, keyword, bidingPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creative that)) return false;
        return id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
