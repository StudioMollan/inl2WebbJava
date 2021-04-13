package Inl2.inl2.service;

import Inl2.inl2.shared.dto.ProdDto;

import java.util.List;
import java.util.Optional;

public interface ProdService {

    String getProd();

    Optional<ProdDto> getProdById(String id);

    ProdDto createProd(ProdDto prodDetails);

    Optional<ProdDto> updateProd(String id, ProdDto prodDto);

    boolean deleteProd(String id);

    List<ProdDto> getProducts();

}
