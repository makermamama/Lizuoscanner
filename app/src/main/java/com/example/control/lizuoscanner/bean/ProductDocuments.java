package com.example.control.lizuoscanner.bean;

/**
 * Created by ma on 2018/3/17.
 */

public class ProductDocuments {
    //产品条码
    private String Documents;

    //单据日期
    private String DocumentsData;
    //客户名
    private String Customer;
    //仓库名
    private String repository;
    //单据类型
    private String DocumentsType;
    //产品编码
    private String Productcode;
    //产品名称
    private String Product;

    private String ProductVersion;

    private String num;
    private String TopFive ;
    private String LatterFive;


    public String getDocuments() {
        return Documents;
    }

    public void setDocuments(String documents) {
        Documents = documents;
    }

    public String getDocumentsData() {
        return DocumentsData;
    }

    public void setDocumentsData(String documentsData) {
        DocumentsData = documentsData;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getDocumentsType() {
        return DocumentsType;
    }

    public void setDocumentsType(String documentsType) {
        DocumentsType = documentsType;
    }

    public String getProductcode() {
        return Productcode;
    }

    public void setProductcode(String productcode) {
        Productcode = productcode;
    }

    public String getProductVersion() {
        return ProductVersion;
    }

    public void setProductVersion(String productVersion) {
        ProductVersion = productVersion;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTopFive() {
        return TopFive;
    }

    public void setTopFive(String topFive) {
        TopFive = topFive;
    }

    public String getLatterFive() {
        return LatterFive;
    }

    public void setLatterFive(String latterFive) {
        LatterFive = latterFive;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }






}
