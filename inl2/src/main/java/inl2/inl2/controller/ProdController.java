package Inl2.inl2.controller;

import Inl2.inl2.exception.NotFoundException;
import Inl2.inl2.model.request.ProdDetailsRequestModel;
import Inl2.inl2.model.response.ProdResponseModel;
import Inl2.inl2.service.ProdService;
import Inl2.inl2.shared.dto.ProdDto;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products") // table in db: 'inl2'
public class ProdController {

    private final ProdService prodService;

    public ProdController(ProdService prodService) {
        this.prodService = prodService;
    }

    @GetMapping // products
    public ResponseEntity <List<ProdResponseModel>> getProducts(){
        List<ProdDto> prodDtos = prodService.getProducts();
        ArrayList<ProdResponseModel> responseList = new ArrayList<>();
        for (ProdDto prodDto : prodDtos) {
            ProdResponseModel responseModel = new ProdResponseModel();
            BeanUtils.copyProperties(prodDto, responseModel);
            responseList.add(responseModel);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")// prod
    public ResponseEntity <ProdResponseModel> getProd(@PathVariable String id) {
        ProdResponseModel responseModel = new ProdResponseModel();
        Optional<ProdDto> optionalProdDto = prodService.getProdById(id);
        if (optionalProdDto.isPresent()) {
            ProdDto prodDto = optionalProdDto.get();
            BeanUtils.copyProperties(prodDto, responseModel);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ProdResponseModel> createProd(@RequestBody ProdDetailsRequestModel prodDetailsModel) {
        // copy json to dto in
        ProdDto prodDtoIn = new ProdDto();
        BeanUtils.copyProperties(prodDetailsModel, prodDtoIn);

        // pass dto in to service layer
        ProdDto prodDtoOut = prodService.createProd(prodDtoIn);

        // copy dto out from service layer to repsonse
        ProdResponseModel response = new ProdResponseModel();
        BeanUtils.copyProperties(prodDtoOut, response);


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdResponseModel> updateProd(@PathVariable String id, @RequestBody ProdDetailsRequestModel requestData) {

        ProdDto prodDtoIn = new ProdDto();
        BeanUtils.copyProperties(requestData, prodDtoIn);

        Optional<ProdDto> prodDtoOut = prodService.updateProd(id, prodDtoIn);
        if (prodDtoOut.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProdDto prodDto = prodDtoOut.get();
        ProdResponseModel responseModel = new ProdResponseModel();
        BeanUtils.copyProperties(prodDto, responseModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProd(@PathVariable String id) {
        boolean deleted = prodService.deleteProd(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //throw new NotFoundException("No product with id " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
