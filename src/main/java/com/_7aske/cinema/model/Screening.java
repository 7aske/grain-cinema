package com._7aske.cinema.model;


import com._7aske.grain.security.Authentication;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "screening")
@Getter @Setter @ToString
public class Screening {
	@Id
    @Column(name = "screening_id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "time", nullable = false)
    private LocalDateTime time;
	@ManyToOne(optional = false)
	@JoinColumn(name = "room_fk", referencedColumnName = "room_id", nullable = false)
	private Room room;
	@ManyToOne(optional = false)
	@JoinColumn(name = "movie_fk", referencedColumnName = "movie_id", nullable = false)
	private Movie movie;
	@OneToMany(mappedBy = "screening")
	@ToString.Exclude
	private List<Reservation> reservations;

	public boolean isSeatTaken(int seat) {
		return reservations.stream()
				.anyMatch(r -> r.getSeat().equals(seat));
	}

	public boolean isSeatTakenBy(int seat, Authentication authentication) {
		return reservations.stream()
				.anyMatch(r -> r.getSeat().equals(seat) && authentication != null && r.getUser().getUsername().equals(authentication.getName()));
	}
}
