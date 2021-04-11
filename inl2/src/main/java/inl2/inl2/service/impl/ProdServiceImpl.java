package Inl2.inl2.service.impl;

import Inl2.inl2.repository.ProdRepository;
import Inl2.inl2.repository.entity.ProdEntity;
import Inl2.inl2.service.ProdService;
import Inl2.inl2.shared.Util;
import Inl2.inl2.shared.dto.ProdDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdServiceImpl implements ProdService {
    private final ProdRepository prodRepository;
    private final Util util;

    public ProdServiceImpl(ProdRepository prodRepository, Util util) {
        this.prodRepository = prodRepository;
        this.util = util;
    }

    public String getProd() {
        return "getProd";
    }

    public Optional<ProdDto> getProdById(String id) {
        Optional<ProdEntity> prodIdEntity = prodRepository.findByProdId(id);
        return prodIdEntity.map(prodEntity -> {
            ProdDto prodDto = new ProdDto();
            BeanUtils.copyProperties(prodEntity, prodDto);
            return prodDto;
        });
    }

    public ProdDto createProd(ProdDto prodDetailsIn) {
        Optional<ProdEntity> checkCategoryEntity = prodRepository.findByEmail(prodDetailsIn.getCategory());
        if (checkCategoryEntity.isPresent()) {
            throw new RuntimeException("Product already exists");
        }
        ProdEntity prodEntity = new ProdEntity();
        BeanUtils.copyProperties(prodDetailsIn, prodEntity);

//        String productId = util.generateHash(prodDetailsIn.getCategory());
//
//        prodEntity.setProductId(productId.substring(3));
//        String encryptedPassword = BCrypt.hashpw(prodDetailsIn.getPassword(), BCrypt.gensalt(10));
//        prodEntity.setEncryptedPassword(encryptedPassword);

        ProdEntity prodEntityOut = prodRepository.save(prodEntity);
        ProdDto prodDtoOut = new ProdDto();
        BeanUtils.copyProperties(prodEntityOut, prodDtoOut);
        return prodDtoOut;
    }

    public String updateProd() {
        return "updateProd";
    }

    public String deleteProd() {
        return "deleteProd";
    }
}
