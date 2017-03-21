package br.com.kuka.developer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.kuka.developer.model.Artigo;

@Repository
public interface ArtigoRepository extends CrudRepository<Artigo, Long>{

	public boolean findArtigoByTitulo(String Titulo);
}
