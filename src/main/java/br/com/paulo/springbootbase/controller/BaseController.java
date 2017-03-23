package br.com.paulo.springbootbase.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public abstract class BaseController {

	protected void configuraModelPaginacao(Pageable pageable, Model model, Page<?> lista) {
		int pagesSize = 10; //TODO externalizar
		Integer beginIndex = Math.max(0, pageable.getPageNumber() - pagesSize/2);
		Integer endIndex = Math.min(beginIndex + pagesSize, lista.getTotalPages()) - 1;

		model.addAttribute("paginacao_begin", beginIndex);
		model.addAttribute("paginacao_end", endIndex);
	}
}
