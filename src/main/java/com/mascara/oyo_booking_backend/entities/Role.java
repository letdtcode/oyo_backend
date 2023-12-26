package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "role")
public class Role extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "role_name", unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;

    @ManyToMany(mappedBy = "roleSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> userSet;
}
