package com.agencyplatformclonecoding.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "nickname"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ClientUser extends AuditingFields {

    @Id
    @Column(name = "CLIENT_ID", length = 50)
    private String userId;

    @Setter @ManyToOne @JoinColumn(name = "AGENCY_ID") private Agency agency; // 에이전트 정보 (ID)
    @Setter @ManyToOne @JoinColumn(name = "AGENT_ID") private Agent agent; // 에이전트 정보 (ID)

    @Setter @ManyToOne @JoinColumn(name = "CATEGORY_ID") private Category category; // 카테고리 정보 (ID)

    @Setter @Column(nullable = false) private String userPassword;
    @Setter @Column(length = 50, nullable = false) private String email;
    @Setter @Column(length = 50, nullable = false) private String nickname;

    @ToString.Exclude
    @OneToMany(mappedBy = "clientUser", cascade = CascadeType.ALL)
    private final Set<Campaign> campaigns = new LinkedHashSet<>();

    protected ClientUser() {
    }

    private ClientUser(Agency agency, Agent agent, Category category, String userId, String userPassword, String email, String nickname) {
        this.agency = agency;
        this.agent = agent;
        this.category = category;
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
    }

    public static ClientUser of(Agency agency, Agent agent, Category category, String userId, String userPassword, String email, String nickname) {
        return new ClientUser(agency, agent, category, userId, userPassword, email, nickname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientUser that)) return false;
        return userId != null && userId.equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
