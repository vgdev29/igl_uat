package com.example.igl.MataData;

public class Pdf_Item{
        public String name,unitName, brandName, categoryName;

        public Pdf_Item(String product_name,String unit) {
            this.setName(product_name);
            this.setUnitName(unit);

        }

        public String getCategoryName() {
            return categoryName;
        }
        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
        public String getBrandName() {
            return brandName;
        }
        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getUnitName() {
            return unitName;
        }
        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }
}