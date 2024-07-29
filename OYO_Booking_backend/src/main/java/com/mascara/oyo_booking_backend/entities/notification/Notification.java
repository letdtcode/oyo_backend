package com.mascara.oyo_booking_backend.entities.notification;

import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;
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
    private Long id;

    @Column(name = "sender_mail")
    private String senderMail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "recipient_mail",
            referencedColumnName = "mail",
            foreignKey = @ForeignKey(name = "fk_association_user_notification"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private User recipient;

    @Column(name = "recipient_mail")
    private String recipientMail;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "view", nullable = false, columnDefinition = "boolean")
    @Builder.Default
    private boolean view = false;

    @Column(name = "image_url", columnDefinition = "NVARCHAR(255)")
    private String imageUrl;
}
