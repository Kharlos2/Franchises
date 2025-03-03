package co.com.pragma.model.franchise.models;

public class BranchProduct {

    private Long id;
    private Long branchId;
    private Long productId;

    public BranchProduct(Long id, Long branchId, Long productId) {
        this.id = id;
        this.branchId = branchId;
        this.productId = productId;
    }

    public BranchProduct(Long branchId, Long productId) {
        this.branchId = branchId;
        this.productId = productId;
    }

    public BranchProduct() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
