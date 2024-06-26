package com.mascara.oyo_booking_backend.entities.authentication;

import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/10/2023
 * Time      : 3:52 CH
 * Filename  : RefreshToken
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "refresh_token")
public class RefreshToken extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_refresh_token_user"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private User user;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "refresh_count", nullable = false, columnDefinition = "bigint")
    @Builder.Default
    private Long refreshCount = 0L;

    @Column(name = "expired_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime expiredDate;

    public void incrementRefreshCount() {
        refreshCount = refreshCount + 1;
    }
}
