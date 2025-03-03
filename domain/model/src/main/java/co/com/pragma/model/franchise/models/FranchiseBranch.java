package co.com.pragma.model.franchise.models;

public class FranchiseBranch {

    private Long id;
    private Long franchiseId;
    private Long branchId;

    public FranchiseBranch(Long id, Long franchiseId, Long branchId) {
        this.id = id;
        this.franchiseId = franchiseId;
        this.branchId = branchId;
    }

    public FranchiseBranch(Long franchiseId, Long branchId) {
        this.franchiseId = franchiseId;
        this.branchId = branchId;
    }

    public FranchiseBranch() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(Long franchiseId) {
        this.franchiseId = franchiseId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
