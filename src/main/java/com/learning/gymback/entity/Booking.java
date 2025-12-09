package com.learning.gymback.entity;

import com.learning.gymback.security.entity.SecurityUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "slot_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_bookings_slot"))
    private Slot slot;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_bookings_user"))
    private SecurityUser user;

    private int participantCount;
    private String status;
    private long createdAt;
    private String cancelReason;
}
