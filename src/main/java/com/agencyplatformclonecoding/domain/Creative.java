package com.agencyplatformclonecoding.domain;

import com.agencyplatformclonecoding.converter.CreativeStatusConverter;
import com.agencyplatformclonecoding.domain.constrant.CreativeStatus;
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
    @Setter @Column private Long bidingPrice;
    @Setter @Column private Long view;
    @Setter @Column private Long click;
    @Setter @Column private Long conversion;
    @Setter @Column private Long purchase;

    @Convert(converter = CreativeStatusConverter.class)
   	@Setter @Column private CreativeStatus status;

    @Setter @Column private boolean deleted;

    protected Creative() {}

    private Creative(Campaign campaign, String keyword, Long bidingPrice) {
        this.campaign = campaign;
        this.keyword = keyword;
        this.bidingPrice = bidingPrice;
		this.status = initiaizeStatus();
        this.deleted = false;
    }

	public CreativeStatus initiaizeStatus() {
		CreativeStatus status = new CreativeStatus(200);
		return status;
	}

    public static Creative of(Campaign campaign, String keyword, Long bidingPrice) {
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
