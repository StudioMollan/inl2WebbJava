package Inl2.inl2.service.impl;

import Inl2.inl2.repository.ProdRepository;
import Inl2.inl2.repository.entity.ProdEntity;
import Inl2.inl2.service.ProdService;
import Inl2.inl2.shared.Util;
import Inl2.inl2.shared.dto.ProdDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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

    public ProdDto createProd(ProdDto prodDetails) {
        Optional<ProdEntity> checkCategoryEntity = prodRepository.findByEmail(prodDetails.getCategory());
        if (checkCategoryEntity.isPresent()) {
            throw new RuntimeException("Product already exists");
        }
        ProdEntity prodEntity = new ProdEntity();
        BeanUtils.copyProperties(prodDetails, prodEntity);

        String productId = util.generateHash(prodDetails.getName());

        prodEntity.setProductId(productId.substring(3));

        ProdEntity prodEntityOut = prodRepository.save(prodEntity);
        ProdDto prodDtoOut = new ProdDto();
        BeanUtils.copyProperties(prodEntityOut, prodDtoOut);
        return prodDtoOut;
    }


    public Optional<ProdDto> updateProd(String id, ProdDto prodDto) {
        Optional<ProdEntity> productIdEntity = prodRepository.findByProdId(id);
        if (productIdEntity.isEmpty())
            return Optional.empty();
        return productIdEntity.map(prodEntity -> {
            ProdDto response = new ProdDto();
            // Set all non-null properties in entity
            prodEntity.setProductId(prodDto.getProductId() != null ? util.generateHash(prodDto.getName()).substring(3) :
                    prodEntity.getProductId());
            prodEntity.setName(prodDto.getName() != null ? prodDto.getName() : prodEntity.getName());
            prodEntity.setCost(prodDto.getCost() != null ? prodDto.getCost() : prodEntity.getCost());
            prodEntity.setCategory(prodDto.getCategory() != null ? prodDto.getCategory() : prodEntity.getCategory());

            ProdEntity updatedProdEntity = prodRepository.save(prodEntity);
            BeanUtils.copyProperties(updatedProdEntity, response);
            return response;
        });
    }

    @Transactional
    public boolean deleteProd(String id) {
        long removedUsersCount = prodRepository.deleteByProdId(id);
        return removedUsersCount > 0;
    }

    @Override
    public List<ProdDto> getProducts() {
        Iterable<ProdEntity> prodEntities = prodRepository.findAll();
        ArrayList<ProdDto> prodDtos = new ArrayList<>();
        for (ProdEntity prodEntity : prodEntities) {
            ProdDto prodDto = new ProdDto();
            BeanUtils.copyProperties(prodEntity, prodDto);
            prodDtos.add(prodDto);
        }
        return prodDtos;
    }


}
