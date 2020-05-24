package jp.co.rakus_partners.rakusitem.form;

public class ItemForm {

    private String name;
    private double price;
    private Integer daiCategoryId;
    private Integer chuCategoryId;
    private Integer syoCategoryId;
    private String brand;
    private Integer condition;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
