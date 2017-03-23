package br.com.paulo.springbootbase.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.paulo.springbootbase.domain.UserRole;
import br.com.paulo.springbootbase.domain.Usuario;
import br.com.paulo.springbootbase.repository.UserRoleRepository;
import br.com.paulo.springbootbase.repository.UsuarioRepository;

@Component
public class BootstrapData {

	private static final Logger log = LoggerFactory.getLogger(BootstrapData.class);
	
	private final UsuarioRepository usuarioRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;

	private final Boolean fakeData;

	
	public BootstrapData(UsuarioRepository usuarioRepository, 
					PasswordEncoder passwordEncoder,
					UserRoleRepository userRoleRepository,
			@Value("${modelo.fakedata:false}") Boolean fakeData) {
		
		this.usuarioRepository = usuarioRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
		this.fakeData = fakeData;
	}
	
	@EventListener
	public void onApplicationRefresh(ContextRefreshedEvent event) {
		if(fakeData) {
			log.info("Inserindo dados fake de dev...");
			Usuario paulo = new Usuario();
			paulo.setLogin("paulo");
			paulo.setSenha(passwordEncoder.encode("senha"));
			paulo.setEnabled(true);
			usuarioRepository.save(paulo);
			userRoleRepository.save(new UserRole().setUsername("paulo").setAuthority("ROLE_USER"));
			
			Usuario paulo2 = new Usuario();
			paulo2.setLogin("paulo2");
			paulo2.setEnabled(false);
			paulo2.setSenha(passwordEncoder.encode("senha"));
			usuarioRepository.save(paulo2);
			userRoleRepository.save(new UserRole().setUsername("paulo2").setAuthority("ROLE_USER"));

			Usuario paulo3 = new Usuario();
			paulo3.setLogin("paulo3");
			paulo3.setEnabled(true);
			paulo3.setSenha(passwordEncoder.encode("senha"));
			usuarioRepository.save(paulo3);
			userRoleRepository.save(new UserRole().setUsername("paulo3").setAuthority("ROLE_USER"));
			
			for(int i = 0; i < 500; i++) {
				Usuario usuario = new Usuario();
				usuario.setLogin("usuario" + i);
				usuario.setEnabled(true);
				usuario.setSenha("usuario" + i);

				usuarioRepository.save(usuario);
				userRoleRepository.save(new UserRole().setUsername("usuario" + i).setAuthority("ROLE_USER"));
			}
		}		
	}
}
