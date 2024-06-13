package com.mascara.oyo_booking_backend.entities.notification;

import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/06/2024
 * Time      : 8:37 CH
 * Filename  : Notification
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "sender_id")
    private Long senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "recipient_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_user_notification"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private User recipient;

    @Column(name = "recipient_id")
    private Long recipientId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "title", columnDefinition = "NVARCHAR(255)")
    private String title;

    @Column(name = "image_url", columnDefinition = "NVARCHAR(255)")
    private String imageUrl;
}
