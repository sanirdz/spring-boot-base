package br.com.paulo.springbootbase.api.resource;

import org.springframework.hateoas.ResourceSupport;

public class UsuarioResource extends ResourceSupport {

	private String login;
	private Boolean enabled;
	
	public String getLogin() {
		return login;
	}
	public UsuarioResource setLogin(String login) {
		this.login = login;
		return this;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public UsuarioResource setEnabled(Boolean enabled) {
		this.enabled = enabled;
		return this;
	}
}
