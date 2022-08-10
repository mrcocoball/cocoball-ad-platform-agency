package com.agencyplatformclonecoding.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "agents"),
        @Index(columnList = "agentGroups"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Agency extends AuditingFields {

    @Id
    @Column(name = "id", length = 50)
    private String agencyId;

    @Column(name = "name", length = 50)
    private String agencyName;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    @JoinColumn(name = "AGENCY_ID")
    private final Set<Agent> agents = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    @JoinColumn(name = "AGENT_GROUP_ID")
    private final Set<AgentGroup> agentGroups = new LinkedHashSet<>();

    protected Agency() {}

    private Agency(String agencyId, String agencyName) {
        this.agencyId = agencyId;
        this.agencyName = agencyName;
    }

    public Agency of (String agencyId, String agencyName) {
        return new Agency(agencyId, agencyName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agency that)) return false;
        return agencyId != null && agencyId.equals(that.getAgencyId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(agencyId);
    }
}
