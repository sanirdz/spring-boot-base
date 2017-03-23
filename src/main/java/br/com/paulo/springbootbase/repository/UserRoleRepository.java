package br.com.paulo.springbootbase.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.paulo.springbootbase.domain.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

}
