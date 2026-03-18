package com.heitor.aprendendo_spring.infrastructure.repository;

import com.heitor.aprendendo_spring.infrastructure.entity.Endereco;
import com.heitor.aprendendo_spring.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
