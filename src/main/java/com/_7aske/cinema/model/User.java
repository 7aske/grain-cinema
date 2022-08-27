package com._7aske.cinema.model;

import com._7aske.cinema.model.domain.Role;
import com._7aske.grain.http.json.JsonIgnore;
import com._7aske.grain.security.Authority;
import com._7aske.grain.security.BasicAuthority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "user", indexes = {
		@Index(name = "user_username_idx", columnList = "username", unique = true)
})
@Getter @Setter @ToString
public class User implements com._7aske.grain.security.User {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	@Column(name = "password", nullable = false)
	@JsonIgnore
	private String password;
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@Override
	public Collection<? super Authority> getAuthorities() {
		return Collections.singletonList(new BasicAuthority(role.name()));
	}

	@Override
	public boolean isAccountLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
