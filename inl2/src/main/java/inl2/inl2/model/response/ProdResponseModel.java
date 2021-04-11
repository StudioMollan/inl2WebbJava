package Inl2.inl2.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdResponseModel {

    @JsonProperty("product_id")
    private String productId;

    private String name;

    private int cost;

    private String category;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
