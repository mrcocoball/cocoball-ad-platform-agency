package com.agencyplatformclonecoding.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
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
    @Setter @Column private long biding_price;
    @Setter @Column private long view;
    @Setter @Column private long click;
    @Setter @Column private long conversion;

    protected Creative() {}

    private Creative(long id, AdCampaign adCampaign, String keyword, long biding_price, long view, long click, long conversion) {
        this.id = id;
        this.adCampaign = adCampaign;
        this.keyword = keyword;
        this.biding_price = biding_price;
        this.view = view;
        this.click = click;
        this.conversion = conversion;
    }

    public Creative of (long id, AdCampaign adCampaign, String keyword, long biding_price, long view, long click, long conversion) {
        return new Creative(id, adCampaign, keyword, biding_price, view, click, conversion);
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
