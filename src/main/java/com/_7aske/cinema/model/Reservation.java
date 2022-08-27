package com._7aske.cinema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "reservation")
@Getter @Setter @ToString
public class Reservation {
	@Id
	@Column(name = "reservation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "screening_fk", referencedColumnName = "screening_id", nullable = false)
	private Screening screening;
	@ManyToOne
	@JoinColumn(name = "user_fk", referencedColumnName = "user_id", nullable = false)
	private User user;
	@Column(name = "seat", nullable = false)
	private Integer seat;
}
