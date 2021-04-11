package Inl2.inl2.controller;

import Inl2.inl2.model.request.ProdDetailsRequestModel;
import Inl2.inl2.model.response.ProdResponseModel;
import Inl2.inl2.service.ProdService;
import Inl2.inl2.shared.dto.ProdDto;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("products") // table in db: 'inl2'
public class ProdController {

    private final ProdService prodService;

    public ProdController(ProdService prodService) {
        this.prodService = prodService;
    }

    @GetMapping(value="/{id}")
    public ProdResponseModel getProd(@PathVariable String id) {
        ProdResponseModel responseModel = new ProdResponseModel();
        Optional<ProdDto> optionalProdDto = prodService.getProdById(id);
        if (optionalProdDto.isPresent()) {
            ProdDto prodDto = optionalProdDto.get();
            BeanUtils.copyProperties(prodDto, responseModel);
            return responseModel;
        }
        throw new RuntimeException("No product with id " + id);
    }

    @PostMapping
    public ProdResponseModel createProd(@RequestBody ProdDetailsRequestModel prodDetailsModel) {
        // copy json to dto in
        ProdDto prodDtoIn = new ProdDto();
        BeanUtils.copyProperties(prodDetailsModel, prodDtoIn);

        // pass dto in to service layer
        ProdDto prodDtoOut = prodService.createProd(prodDtoIn);

        // copy dto out from service layer to repsonse
        ProdResponseModel response = new ProdResponseModel();
        BeanUtils.copyProperties(prodDtoOut, response);
        return response;
    }

    @PutMapping
    public String updateProd() {
        return prodService.updateProd();
    }

    @DeleteMapping
    public String deleteProd() {
        return prodService.deleteProd();
    }
}
