package com.ps.back.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "TBLUSER")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TblUser implements UserDetails {
    @Id
    @Column(name = "CVEUSER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cveUser;

    @Column(name = "NAMEUSeR", length = 200, nullable = false)
    private String nameUser;

    @Column(name = "LASTNAMEUSER", length = 200, nullable = false)
    private String lastNameUser;

    @Column(name = "LOGINUSER", length = 200, nullable = false, unique = true)
    private String loginUser;

    @Column(name = "EMAILUSER", length = 200, nullable = false, unique = true)
    private String emailUser;

    @Column(name = "PASSWORDUSER", length = 200, nullable = false)
    private String passwordUser;

    @Column(name = "GENDERUSER", length = 1, nullable = false)
    private String genderUser;

    @ElementCollection
    @CollectionTable(name = "USU_CAR", joinColumns = @JoinColumn(name = "CVEUSER"))
    @Column(name = "CVEUSUCAR")
    private Set<String> cveUsuCar = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USU_ROL", joinColumns = @JoinColumn(name = "CVEUSER"),
            inverseJoinColumns = @JoinColumn(name = "CVEROL"))
    private Set<TblRol> tblRoles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USU_REG", joinColumns = @JoinColumn(name = "CVEUSER"),
            inverseJoinColumns = @JoinColumn(name = "CVEREG"))
    private Set<TblRegistry> tblRegistries = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return tblRoles.stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_"+rol.getNameRol()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {return passwordUser;}

    @Override
    public String getUsername() {return loginUser;}

    @Override
    public boolean isAccountNonExpired() {return UserDetails.super.isAccountNonExpired();}

    @Override
    public boolean isAccountNonLocked() {return UserDetails.super.isAccountNonLocked();}

    @Override
    public boolean isCredentialsNonExpired() {return UserDetails.super.isCredentialsNonExpired();}

    @Override
    public boolean isEnabled() {return UserDetails.super.isEnabled();}
}
