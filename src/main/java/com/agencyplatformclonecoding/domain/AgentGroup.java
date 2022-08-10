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
        @Index(columnList = "name"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class AgentGroup extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AGENT_GROUP_ID")
    private long id;

    // 다대일 양방향
    @Setter
    @ManyToOne @JoinColumn(name = "AGENCY_ID", insertable = false, updatable = false) private Agency agency; // 에이전시 정보 (ID)

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "agentGroup", cascade = CascadeType.ALL)
    @JoinColumn(name = "AGENT_GROUP_ID")
    private final Set<Agent> agents = new LinkedHashSet<>();

    @Setter @Column(length = 50, nullable = false) private String name;

    protected AgentGroup() {}

    private AgentGroup(Agency agency, long id, String name) {
        this.agency = agency;
        this.id = id;
        this.name = name;
    }

    public AgentGroup of (Agency agency, long id, String name) {
        return new AgentGroup(agency, id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgentGroup that)) return false;
        return id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
