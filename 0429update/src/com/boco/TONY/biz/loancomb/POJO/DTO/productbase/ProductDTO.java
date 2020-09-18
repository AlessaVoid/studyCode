package com.boco.TONY.biz.loancomb.POJO.DTO.productbase;

import java.io.Serializable;

/**
 * 信贷产品DTO
 *
 * @author tony
 * @describe ProductDTO
 * @date 2019-09-17
 */
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = -8198245686831308885L;
    /***产品id*/
    private String productId;

    /***产品所属业务系统*/
    private String productSystemId;

    /***产品编号*/
    private String productCode;

    /***产品名称*/
    private String productName;

    /***产品级别*/
    private String productLevel;

    /***产品上级编号*/
    private String productUpCode;

    /***产品状态*/
    private String productStatus;

    /***产品归属*/
    private String productBelong;

    /***级别编码-1*/
    private String level1Code;

    /***级别编码-2*/
    private String level2Code;

    /***级别编码-3*/
    private String level3Code;

    /***级别编码-4*/
    private String level4Code;

    /***级别编码-5*/
    private String level5Code;

    /***级别编码-6*/
    private String level6Code;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductSystemId() {
        return productSystemId;
    }

    public void setProductSystemId(String productSystemId) {
        this.productSystemId = productSystemId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(String productLevel) {
        this.productLevel = productLevel;
    }

    public String getProductUpCode() {
        return productUpCode;
    }

    public void setProductUpCode(String productUpCode) {
        this.productUpCode = productUpCode;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductBelong() {
        return productBelong;
    }

    public void setProductBelong(String productBelong) {
        this.productBelong = productBelong;
    }

    public String getLevel1Code() {
        return level1Code;
    }

    public void setLevel1Code(String level1Code) {
        this.level1Code = level1Code;
    }

    public String getLevel2Code() {
        return level2Code;
    }

    public void setLevel2Code(String level2Code) {
        this.level2Code = level2Code;
    }

    public String getLevel3Code() {
        return level3Code;
    }

    public void setLevel3Code(String level3Code) {
        this.level3Code = level3Code;
    }

    public String getLevel4Code() {
        return level4Code;
    }

    public void setLevel4Code(String level4Code) {
        this.level4Code = level4Code;
    }

    public String getLevel5Code() {
        return level5Code;
    }

    public void setLevel5Code(String level5Code) {
        this.level5Code = level5Code;
    }

    public String getLevel6Code() {
        return level6Code;
    }

    public void setLevel6Code(String level6Code) {
        this.level6Code = level6Code;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId='" + productId + '\'' +
                ", productSystemId='" + productSystemId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", productLevel='" + productLevel + '\'' +
                ", productUpCode='" + productUpCode + '\'' +
                ", productStatus='" + productStatus + '\'' +
                ", productBelong='" + productBelong + '\'' +
                ", level1Code='" + level1Code + '\'' +
                ", level2Code='" + level2Code + '\'' +
                ", level3Code='" + level3Code + '\'' +
                ", level4Code='" + level4Code + '\'' +
                ", level5Code='" + level5Code + '\'' +
                ", level6Code='" + level6Code + '\'' +
                '}';
    }
}
