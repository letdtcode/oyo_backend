package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/10/2023
 * Time      : 3:52 CH
 * Filename  : RefreshToken
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "refresh_token")
public class RefreshToken extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "token", nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String refreshToken;


    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "refresh_count")
    private Long refreshCount;

    @Column(name = "expired_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime expiryDate;

    public void incrementRefreshCount() {
        refreshCount = refreshCount + 1;
    }
}
