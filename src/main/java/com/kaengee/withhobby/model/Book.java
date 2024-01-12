package com.kaengee.withhobby.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //한개의 동아리는 여러 모임 가능
    @ManyToOne(fetch=FetchType.EAGER)
    private Meeting meeting; //모임 id

    //한명의 유저는 여러 모임 가능
    @ManyToOne(fetch = FetchType.EAGER)
    private User user; //유저아이디

    @Column(name="booking_staus", nullable = false)
    private BookStatus bookStatus; //예약상태 (대기중, 승인)
}
