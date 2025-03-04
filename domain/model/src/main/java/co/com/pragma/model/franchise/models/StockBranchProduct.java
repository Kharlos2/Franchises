package co.com.pragma.model.franchise.models;

public class StockBranchProduct {

    private String branchName;
    private String productName;
    private Integer stock;

    public StockBranchProduct(String branchName, String productName, Integer stock) {
        this.branchName = branchName;
        this.productName = productName;
        this.stock = stock;
    }

    public StockBranchProduct() {
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
