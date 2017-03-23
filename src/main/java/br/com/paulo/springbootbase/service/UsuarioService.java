package br.com.paulo.springbootbase.service;

import static org.springframework.data.domain.ExampleMatcher.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.paulo.springbootbase.domain.UserRole;
import br.com.paulo.springbootbase.domain.Usuario;
import br.com.paulo.springbootbase.repository.UserRoleRepository;
import br.com.paulo.springbootbase.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UsuarioService(UsuarioRepository usuarioRepository, UserRoleRepository userRoleRepository,
			PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Usuario insert(Usuario usuario) {
		usuario
			.setSenha(passwordEncoder.encode(usuario.getSenha()))
			.setEnabled(true);

		userRoleRepository.save(new UserRole().setUsername(usuario.getLogin()).setAuthority("ROLE_USER"));

		return usuarioRepository.save(usuario);
	}
	
	public Usuario update(Usuario usuario) {
		usuario
			.setSenha(passwordEncoder.encode(usuario.getSenha()));
		
		return usuarioRepository.save(usuario);
	}

	public Usuario findOne(Long id) {
		return usuarioRepository.findOne(id);
	}

	public void delete(Long id) {
		usuarioRepository.delete(id);
	}

	public Page<Usuario> pesquisa(Usuario filtro, Pageable pageable) {
		Example<Usuario> example = Example.of(filtro,
				matchingAll()
					.withMatcher("login", contains().ignoreCase()));

		return usuarioRepository.findAll(example, pageable);
	}

	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}
}