package br.com.paulo.springbootbase.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.paulo.springbootbase.api.resource.SalvarUsuarioResource;
import br.com.paulo.springbootbase.api.resource.UsuarioResource;
import br.com.paulo.springbootbase.domain.Usuario;
import br.com.paulo.springbootbase.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosRestController {

	private static final Logger log = LoggerFactory.getLogger(UsuariosRestController.class);
	
	private final UsuarioService usuarioService;
	
	public UsuariosRestController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@ApiOperation(value = "Salva um novo usuário.", tags = {"UsuariosRestController"})
	@ApiResponses({
			@ApiResponse(code = 200, message = "Usuário salvo com sucesso."),
			@ApiResponse(code = 400, message = "Dados do usuário inválidos."),
			@ApiResponse(code = 500, message = "Erro inesperado no servidor.")})
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Usuario> save(@ApiParam(value = "Dados do usuário para salvar.") @RequestBody @Valid SalvarUsuarioResource usuario) {
		log.debug("POST para salvar usuario");
		Usuario usuarioSalvo = usuarioService.insert(new Usuario().setLogin(usuario.getLogin()).setSenha(usuario.getSenha()));
		
		return ResponseEntity.created(linkTo(methodOn(UsuariosRestController.class).show(usuarioSalvo.getId())).toUri()).build();
	}

	@ApiOperation(value = "Exibe os dados de um usuário.", tags = {"UsuariosRestController"})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário exibido com sucesso."),
		@ApiResponse(code = 404, message = "Usuário não encontrado"),
		@ApiResponse(code = 500, message = "Erro inesperado no servidor.")})
	@RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<UsuarioResource> show(@ApiParam(value = "Id do usuário") @PathVariable("id") Long id) {
		log.debug("GET para exibir o usuário {}", id);
		
		Usuario usuario = usuarioService.findOne(id);
		
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(criarUsuarioResource(usuario));
	}

	private UsuarioResource criarUsuarioResource(Usuario usuario) {
		UsuarioResource resource = new UsuarioResource()
				.setEnabled(usuario.getEnabled())
				.setLogin(usuario.getLogin());
		
		resource.add(linkTo(methodOn(UsuariosRestController.class).show(usuario.getId())).withSelfRel());
		resource.add(linkTo(methodOn(UsuariosRestController.class).delete(usuario.getId())).withRel("deletar"));
		resource.add(linkTo(methodOn(UsuariosRestController.class).update(usuario.getId(), null)).withRel("atualizar"));
		
		return resource;
	}

	@ApiOperation(value = "Pesquisa usuários.", tags = {"UsuariosRestController"})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário salvo com sucesso."),
		@ApiResponse(code = 400, message = "Dados do usuário inválidos."),
		@ApiResponse(code = 500, message = "Erro inesperado no servidor.")})
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Iterable<Usuario>> list(Pageable pageable) {
		log.debug("GET para pesquisar usuários size {}, page {}, sort {}", pageable.getPageSize(), pageable.getPageNumber(), pageable.getSort()); //?size=2&page=30&sort=login,desc
		
		return ResponseEntity.ok(usuarioService.pesquisa(new Usuario(), pageable));
	}
	
	@ApiOperation(value = "Atualiza dados de um usuário.", tags = {"UsuariosRestController"})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário salvo com sucesso."),
		@ApiResponse(code = 400, message = "Dados do usuário inválidos."),
		@ApiResponse(code = 500, message = "Erro inesperado no servidor.")})
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Usuario> update(@ApiParam(value = "Id do usuário") @PathVariable("id") Long id, 
			@ApiParam(value = "Dados do usuário para atualizar.") @RequestBody @Valid SalvarUsuarioResource usuario) {
		
		log.debug("PUT para atualizar o usuário {}");
		Usuario usuarioDoBanco = usuarioService.findOne(id);
		
		if(usuarioDoBanco == null) {
			return ResponseEntity.notFound().build();
		}
		
		Usuario usuarioSalvo = usuarioService.update(usuarioDoBanco
				.setSenha(usuario.getSenha())
				.setLogin(usuario.getLogin())
				.setEnabled(usuario.getEnabled()));
		
		return ResponseEntity.ok(usuarioSalvo);
	}
	
	@ApiOperation(value = "Deleta um usuário.", tags = {"UsuariosRestController"})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário excluído com sucesso."),
		@ApiResponse(code = 400, message = "Dados do usuário inválidos."),
		@ApiResponse(code = 500, message = "Erro inesperado no servidor.")})
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Usuario> delete(@ApiParam(value = "Id do usuário") @PathVariable("id") Long id) {
		log.debug("DELETE para excluir o usuário {}", id);
		usuarioService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
