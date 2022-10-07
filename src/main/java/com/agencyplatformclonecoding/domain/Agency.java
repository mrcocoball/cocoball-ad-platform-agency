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
        @Index(columnList = "AGENCY_ID"),
        @Index(columnList = "AGENCY_NAME"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Agency extends AuditingFields {

    @Id
    @Column(name = "AGENCY_ID", length = 50)
    private String agencyId;

    @Column(name = "AGENCY_NAME", length = 50)
    private String agencyName;

    @Column(name = "AGENCY_PASSWORD", length = 50)
    private String password;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    private final Set<Agent> agents = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    private final Set<AgentGroup> agentGroups = new LinkedHashSet<>();

    protected Agency() {
    }

    private Agency(String agencyId, String password, String agencyName) {
        this.agencyId = agencyId;
        this.password = password;
        this.agencyName = agencyName;
    }

    public static Agency of(String agencyId, String password, String agencyName) {
        return new Agency(agencyId, password, agencyName);
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
