package br.com.paulo.springbootbase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "USERS")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(name = "username", unique = true)
	private String login;

	@NotBlank
	@Column(name = "password")
	private String senha;

	private Boolean enabled;
	
	@Version
	private Long version;
	
	public Long getId() {
		return id;
	}
	
	public Usuario setId(Long id) {
		this.id = id;
		return this;
	}
	
	public String getLogin() {
		return login;
	}
	
	public Usuario setLogin(String login) {
		this.login = login;
		return this;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public Usuario setSenha(String senha) {
		this.senha = senha;
		return this;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}

	public Usuario setEnabled(Boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public Long getVersion() {
		return version;
	}

	public Usuario setVersion(Long version) {
		this.version = version;
		return this;
	}
}
