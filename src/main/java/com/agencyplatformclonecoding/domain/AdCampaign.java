package com.agencyplatformclonecoding.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class AdCampaign extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAMPAIGN_ID")
    private long id;

    // 다대일 양방향
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "CLIENT_ID", insertable = false, updatable = false) private ClientUser clientUser; // 에이전시 정보 (ID)

    @Setter @Column(length = 50, nullable = false) private String name;
    @Setter @Column private long budget;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "adCampaign", cascade = CascadeType.ALL)
    private final Set<Creative> creatives = new LinkedHashSet<>();

    protected AdCampaign() {}

    private AdCampaign(ClientUser clientUser, long id, String name, long budget) {
        this.clientUser = clientUser;
        this.id = id;
        this.name = name;
        this.budget = budget;
    }

    public static AdCampaign of(ClientUser clientUser, long id, String name, long budget) {
        return new AdCampaign(clientUser, id, name, budget);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdCampaign that)) return false;
        return id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
