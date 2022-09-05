package com.agencyplatformclonecoding.dto.security;

import com.agencyplatformclonecoding.domain.Agency;
import com.agencyplatformclonecoding.dto.AgencyDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record PlatformPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String name
) implements UserDetails {

    public static PlatformPrincipal of(String username, String password, String name) {
        Set<RoleType> roleTypes = Set.of(RoleType.MASTER);

        return new PlatformPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                name
        );
    }

    public static PlatformPrincipal from(AgencyDto dto) {
        return PlatformPrincipal.of(
                dto.agencyId(),
                dto.password(),
                dto.agencyName()
        );
    }

    public AgencyDto toDto() {
        return AgencyDto.of(
                username,
                password,
                name
        );
    }

    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public enum RoleType {
        MASTER("ROLE_MASTER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }

    }
}
