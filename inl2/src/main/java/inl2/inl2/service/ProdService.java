package Inl2.inl2.service;

import Inl2.inl2.shared.dto.ProdDto;

import java.util.Optional;

public interface ProdService {

    String getProd();

    Optional<ProdDto> getProdById(String id);

    ProdDto createProd(ProdDto userDetails);

    String updateProd();

    String deleteProd();

}
