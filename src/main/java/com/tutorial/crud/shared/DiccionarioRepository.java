package com.tutorial.crud.shared;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface DiccionarioRepository<T, ID> extends JpaRepository<T, ID> {
  Optional<T> findByCodigo(String codigo);
}
