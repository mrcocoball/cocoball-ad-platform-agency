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
        @Index(columnList = "name"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class AgentGroup extends AuditingFields {

    @Id
    @Column(name = "AGENT_GROUP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 다대일 양방향
    @Setter @ManyToOne @JoinColumn(name = "AGENCY_ID") private Agency agency; // 에이전시 정보 (ID)

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "agentGroup", cascade = CascadeType.ALL)
    private final Set<Agent> agents = new LinkedHashSet<>();

    @Setter @Column(length = 50, nullable = false) private String name;

    protected AgentGroup() {
    }

    private AgentGroup(Agency agency, String name) {
        this.agency = agency;
        this.name = name;
    }

    private AgentGroup(Agency agency, Long id, String name) {
        this.agency = agency;
        this.id = id;
        this.name = name;
    }

    public static AgentGroup of(Agency agency, String name) {
        return new AgentGroup(agency, name);
    }

    public static AgentGroup of(Agency agency, Long id, String name) {
        return new AgentGroup(agency, id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgentGroup that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
