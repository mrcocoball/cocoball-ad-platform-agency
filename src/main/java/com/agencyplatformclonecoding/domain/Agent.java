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
        @Index(columnList = "nickname"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Agent extends AuditingFields {

    @Id
    @Column(name = "AGENT_ID", length = 50)
    private String userId;

    // 다대일 양방향
    @Setter @ManyToOne @JoinColumn(name = "AGENCY_ID") private Agency agency; // 에이전시 정보 (ID)
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "AGENCY_GROUP_ID") private AgentGroup agentGroup; // 에이전트 그룹 정보 (ID)

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL)
    private final Set<ClientUser> clientUsers = new LinkedHashSet<>();

    @Setter @Column(nullable = false) private String userPassword;

    @Setter @Column(length = 50, nullable = false) private String email;
    @Setter @Column(length = 50, nullable = false) private String nickname;

    protected Agent() {}

    private Agent(Agency agency, String userId, String userPassword, String email, String nickname) {
        this.agency = agency;
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
    }

    public Agent of(Agency agency, String userId, String userPassword, String email, String nickname) {
        return new Agent(agency, userId, userPassword, email, nickname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agent that)) return false;
        return userId != null && userId.equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
