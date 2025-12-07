//package com.learning.gymback.entity.user_profiles;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.SuperBuilder;
//
//@Entity
//@Table(name = "trainers")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@SuperBuilder
//public class Trainer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_profile_id", nullable = false, unique = true)
//    private UserProfile userProfile;
//
//    private String bio;
//    private String phone;
//
//    // convenience helper to keep both sides in sync
//    public void setUserProfile(UserProfile userProfile) {
//        this.userProfile = userProfile;
//        if (userProfile != null && userProfile.getTrainer() != this) {
//            userProfile.setTrainer(this);
//        }
//    }
//}
