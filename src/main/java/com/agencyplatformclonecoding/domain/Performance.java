package com.agencyplatformclonecoding.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "purchase"),
        @Index(columnList = "createdAt"),
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Performance {

    @Id
    @Column(name = "PERFORMANCE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 다대일 양방향
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "CREATIVE_ID") private Creative creative; // 소재 정보 (ID)

    @Setter @Column private Long view;
    @Setter @Column private Long click;
    @Setter @Column private Long conversion;
    @Setter @Column private Long purchase;
    @Setter @Column private Long spend;
    @Setter @Column private LocalDate createdAt;

    protected Performance() {
    }

    private Performance(Creative creative, Long view, Long click, Long conversion, Long purchase) {
        this.creative = creative;
        this.view = view;
        this.click = click;
        this.conversion = conversion;
        this.purchase = purchase;
        this.spend = creative.getBidingPrice() * click;
    }

    public static Performance of(Creative creative, Long view, Long click, Long conversion, Long purchase) {
        return new Performance(creative, view, click, conversion, purchase);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Performance that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
