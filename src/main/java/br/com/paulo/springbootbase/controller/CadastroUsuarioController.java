package br.com.paulo.springbootbase.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.paulo.springbootbase.domain.Usuario;
import br.com.paulo.springbootbase.service.UsuarioService;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/cadastro")
@ApiIgnore
public class CadastroUsuarioController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(CadastroUsuarioController.class);

	private final UsuarioService usuarioService;
	
	public CadastroUsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String index(Model model, Pageable pageable) {
		log.debug("Listando usuarios...");
		
		Page<Usuario> lista = usuarioService.findAll(pageable);
		model.addAttribute("lista", lista);
		
		configuraModelPaginacao(pageable, model, lista);
		
		return "cadastro/index";
	}
	
	@RequestMapping(path = "", method = RequestMethod.POST)
	public String pesquisar(Pageable pageable, Model model) {
		log.info("paginando usuarios...");
		
		Page<Usuario> lista = usuarioService.findAll(pageable);
		model.addAttribute("lista", lista);

		configuraModelPaginacao(pageable, model, lista);
		
		return "cadastro/fragments/listagem";
	}

	@RequestMapping(path = "/novo", method = RequestMethod.GET)
	public String novo(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "cadastro/novo";
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public String editar(@PathVariable("id") Long id, Model model) {
		log.info("Editando usuário {}", id);
		Usuario usuario = usuarioService.findOne(id);
		if(usuario == null) {
			//TODO retorna 404...
		}
		
		model.addAttribute("usuario", usuario);
		
		return "cadastro/editar";
	}
	
	@RequestMapping(path = "/", method = RequestMethod.PUT)
	public String update(@Valid Usuario usuario, BindingResult result, RedirectAttributes redirectAttributes) {
		log.info("Atualizando usuário {}", usuario.getId());
		
		if(result.hasErrors()) {
			return "cadastro/editar"; 
		} else {
			usuario = usuarioService.update(usuario);
			redirectAttributes.addFlashAttribute("mensagem", "Usuário " + usuario.getLogin() + " atualizado com sucesso.");
		}
		
		return "redirect:/cadastro";
		
	}
	
	@RequestMapping(path = "/", method = RequestMethod.POST)
	public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes redirectAttributes) {
		log.info("Salvando usuario");
		
		if(result.hasErrors()) {
			return "cadastro/novo"; 
		} else {
			usuario = usuarioService.insert(usuario);
			redirectAttributes.addFlashAttribute("mensagem", "Usuário " + usuario.getLogin() + " salvo com sucesso.");
		}
		
		return "redirect:/cadastro";
	}
	
	@RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		log.debug("Removendo usuário " + id + "...");
	
		usuarioService.delete(id);
		redirectAttributes.addFlashAttribute("mensagem", "Usuário removido com sucesso.");
		
		log.debug("Usuário " + id + " removido com sucesso.");
		
		return "redirect:/cadastro";
	}
}
