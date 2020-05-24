package jp.co.rakus_partners.rakusitem.form;

public class SearchForm {

    private Integer page = 1;
    private String itemKeyword;
    private String brand;
    private Integer daiCategoryId;
    private Integer chuCategoryId;
    private Integer syoCategoryId;
    private String actionMode = "search";

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getItemKeyword() {
        return itemKeyword;
    }

    public void setItemKeyword(String itemKeyword) {
        this.itemKeyword = itemKeyword;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getDaiCategoryId() {
        return daiCategoryId;
    }

    public void setDaiCategoryId(Integer daiCategoryId) {
        this.daiCategoryId = daiCategoryId;
    }

    public Integer getChuCategoryId() {
        return chuCategoryId;
    }

    public void setChuCategoryId(Integer chuCategoryId) {
        this.chuCategoryId = chuCategoryId;
    }

    public Integer getSyoCategoryId() {
        return syoCategoryId;
    }

    public void setSyoCategoryId(Integer syoCategoryId) {
        this.syoCategoryId = syoCategoryId;
    }

    public String getActionMode() {
        return actionMode;
    }

    public void setActionMode(String actionMode) {
        this.actionMode = actionMode;
    }


}
