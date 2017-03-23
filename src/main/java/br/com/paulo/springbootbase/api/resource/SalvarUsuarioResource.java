package br.com.paulo.springbootbase.api.resource;

import org.hibernate.validator.constraints.NotBlank;

public class SalvarUsuarioResource {

	@NotBlank
	private String login;
	
	@NotBlank
	private String senha;
	
	private Boolean enabled;
	
	public String getLogin() {
		return login;
	}
	public SalvarUsuarioResource setLogin(String login) {
		this.login = login;
		return this;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public SalvarUsuarioResource setEnabled(Boolean enabled) {
		this.enabled = enabled;
		return this;
	}
	public String getSenha() {
		return senha;
	}
	public SalvarUsuarioResource setSenha(String senha) {
		this.senha = senha;
		return this;
	}
	
}
