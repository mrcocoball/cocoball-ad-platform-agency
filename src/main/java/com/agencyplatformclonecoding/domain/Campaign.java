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
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Campaign extends AuditingFields {

    @Id
    @Column(name = "CAMPAIGN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 다대일 양방향
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "CLIENT_ID") private ClientUser clientUser; // 에이전시 정보 (ID)

    @Setter @Column(length = 50, nullable = false) private String name;
    @Setter @Column private long budget;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private final Set<Creative> creatives = new LinkedHashSet<>();

    protected Campaign() {}

    private Campaign(ClientUser clientUser, String name, long budget) {
        this.clientUser = clientUser;
        this.name = name;
        this.budget = budget;
    }

    public static Campaign of(ClientUser clientUser, String name, long budget) {
        return new Campaign(clientUser, name, budget);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Campaign that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
