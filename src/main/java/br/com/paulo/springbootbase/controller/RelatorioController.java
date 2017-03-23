package br.com.paulo.springbootbase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.paulo.springbootbase.domain.Usuario;
import br.com.paulo.springbootbase.service.UsuarioService;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/relatorio")
@ApiIgnore
public class RelatorioController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(RelatorioController.class);

	private final UsuarioService usuarioService;

	public RelatorioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "relatorio/index";
	}

	@RequestMapping(path = "", method = RequestMethod.POST)
	public String pesquisar(Usuario usuario, Pageable pageable, Model model) {
		log.info("pesquisando...");
		log.debug("pageable " + pageable);
		
		Page<Usuario> lista = usuarioService.pesquisa(usuario, pageable);
		model.addAttribute("lista", lista);

		configuraModelPaginacao(pageable, model, lista);
		
		//TODO ficou muito ruim
		if(lista.getSort() != null) {
			if(lista.getSort().getOrderFor("login").getDirection().isAscending()) {
				model.addAttribute("sortColumn", "login,desc");
				model.addAttribute("sort", "login,asc");
				
			} else {
				model.addAttribute("sortColumn", "login,asc");
				model.addAttribute("sort", "login,desc");
			}
		}
		
		log.debug("model " + model);
		
		return "relatorio/fragments/listagem";
	}
}
