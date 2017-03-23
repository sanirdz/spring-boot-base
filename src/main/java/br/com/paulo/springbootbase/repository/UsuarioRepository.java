package br.com.paulo.springbootbase.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import br.com.paulo.springbootbase.domain.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>, QueryByExampleExecutor<Usuario> {

}
