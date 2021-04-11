package Inl2.inl2.repository;

import Inl2.inl2.repository.entity.ProdEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdRepository extends CrudRepository<ProdEntity, Long> {
    Optional<ProdEntity> findByEmail(String email);
    Optional<ProdEntity> findByProdId(String email);
}
