package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MaterialExtendExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MaterialExtendExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMaterialIdIsNull() {
            addCriterion("material_id is null");
            return (Criteria) this;
        }

        public Criteria andMaterialIdIsNotNull() {
            addCriterion("material_id is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialIdEqualTo(Long value) {
            addCriterion("material_id =", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdNotEqualTo(Long value) {
            addCriterion("material_id <>", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdGreaterThan(Long value) {
            addCriterion("material_id >", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdGreaterThanOrEqualTo(Long value) {
            addCriterion("material_id >=", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdLessThan(Long value) {
            addCriterion("material_id <", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdLessThanOrEqualTo(Long value) {
            addCriterion("material_id <=", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdIn(List<Long> values) {
            addCriterion("material_id in", values, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdNotIn(List<Long> values) {
            addCriterion("material_id not in", values, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdBetween(Long value1, Long value2) {
            addCriterion("material_id between", value1, value2, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdNotBetween(Long value1, Long value2) {
            addCriterion("material_id not between", value1, value2, "materialId");
            return (Criteria) this;
        }

        public Criteria andBarCodeIsNull() {
            addCriterion("bar_code is null");
            return (Criteria) this;
        }

        public Criteria andBarCodeIsNotNull() {
            addCriterion("bar_code is not null");
            return (Criteria) this;
        }

        public Criteria andBarCodeEqualTo(String value) {
            addCriterion("bar_code =", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeNotEqualTo(String value) {
            addCriterion("bar_code <>", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeGreaterThan(String value) {
            addCriterion("bar_code >", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeGreaterThanOrEqualTo(String value) {
            addCriterion("bar_code >=", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeLessThan(String value) {
            addCriterion("bar_code <", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeLessThanOrEqualTo(String value) {
            addCriterion("bar_code <=", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeLike(String value) {
            addCriterion("bar_code like", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeNotLike(String value) {
            addCriterion("bar_code not like", value, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeIn(List<String> values) {
            addCriterion("bar_code in", values, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeNotIn(List<String> values) {
            addCriterion("bar_code not in", values, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeBetween(String value1, String value2) {
            addCriterion("bar_code between", value1, value2, "barCode");
            return (Criteria) this;
        }

        public Criteria andBarCodeNotBetween(String value1, String value2) {
            addCriterion("bar_code not between", value1, value2, "barCode");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitIsNull() {
            addCriterion("commodity_unit is null");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitIsNotNull() {
            addCriterion("commodity_unit is not null");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitEqualTo(String value) {
            addCriterion("commodity_unit =", value, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitNotEqualTo(String value) {
            addCriterion("commodity_unit <>", value, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitGreaterThan(String value) {
            addCriterion("commodity_unit >", value, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitGreaterThanOrEqualTo(String value) {
            addCriterion("commodity_unit >=", value, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitLessThan(String value) {
            addCriterion("commodity_unit <", value, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitLessThanOrEqualTo(String value) {
            addCriterion("commodity_unit <=", value, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitLike(String value) {
            addCriterion("commodity_unit like", value, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitNotLike(String value) {
            addCriterion("commodity_unit not like", value, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitIn(List<String> values) {
            addCriterion("commodity_unit in", values, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitNotIn(List<String> values) {
            addCriterion("commodity_unit not in", values, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitBetween(String value1, String value2) {
            addCriterion("commodity_unit between", value1, value2, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andCommodityUnitNotBetween(String value1, String value2) {
            addCriterion("commodity_unit not between", value1, value2, "commodityUnit");
            return (Criteria) this;
        }

        public Criteria andSkuIsNull() {
            addCriterion("sku is null");
            return (Criteria) this;
        }

        public Criteria andSkuIsNotNull() {
            addCriterion("sku is not null");
            return (Criteria) this;
        }

        public Criteria andSkuEqualTo(String value) {
            addCriterion("sku =", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotEqualTo(String value) {
            addCriterion("sku <>", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuGreaterThan(String value) {
            addCriterion("sku >", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuGreaterThanOrEqualTo(String value) {
            addCriterion("sku >=", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLessThan(String value) {
            addCriterion("sku <", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLessThanOrEqualTo(String value) {
            addCriterion("sku <=", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLike(String value) {
            addCriterion("sku like", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotLike(String value) {
            addCriterion("sku not like", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuIn(List<String> values) {
            addCriterion("sku in", values, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotIn(List<String> values) {
            addCriterion("sku not in", values, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuBetween(String value1, String value2) {
            addCriterion("sku between", value1, value2, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotBetween(String value1, String value2) {
            addCriterion("sku not between", value1, value2, "sku");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalIsNull() {
            addCriterion("purchase_decimal is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalIsNotNull() {
            addCriterion("purchase_decimal is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalEqualTo(BigDecimal value) {
            addCriterion("purchase_decimal =", value, "purchaseDecimal");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalNotEqualTo(BigDecimal value) {
            addCriterion("purchase_decimal <>", value, "purchaseDecimal");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalGreaterThan(BigDecimal value) {
            addCriterion("purchase_decimal >", value, "purchaseDecimal");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_decimal >=", value, "purchaseDecimal");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalLessThan(BigDecimal value) {
            addCriterion("purchase_decimal <", value, "purchaseDecimal");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_decimal <=", value, "purchaseDecimal");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalIn(List<BigDecimal> values) {
            addCriterion("purchase_decimal in", values, "purchaseDecimal");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalNotIn(List<BigDecimal> values) {
            addCriterion("purchase_decimal not in", values, "purchaseDecimal");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_decimal between", value1, value2, "purchaseDecimal");
            return (Criteria) this;
        }

        public Criteria andPurchaseDecimalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_decimal not between", value1, value2, "purchaseDecimal");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalIsNull() {
            addCriterion("commodity_decimal is null");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalIsNotNull() {
            addCriterion("commodity_decimal is not null");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalEqualTo(BigDecimal value) {
            addCriterion("commodity_decimal =", value, "commodityDecimal");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalNotEqualTo(BigDecimal value) {
            addCriterion("commodity_decimal <>", value, "commodityDecimal");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalGreaterThan(BigDecimal value) {
            addCriterion("commodity_decimal >", value, "commodityDecimal");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("commodity_decimal >=", value, "commodityDecimal");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalLessThan(BigDecimal value) {
            addCriterion("commodity_decimal <", value, "commodityDecimal");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("commodity_decimal <=", value, "commodityDecimal");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalIn(List<BigDecimal> values) {
            addCriterion("commodity_decimal in", values, "commodityDecimal");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalNotIn(List<BigDecimal> values) {
            addCriterion("commodity_decimal not in", values, "commodityDecimal");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("commodity_decimal between", value1, value2, "commodityDecimal");
            return (Criteria) this;
        }

        public Criteria andCommodityDecimalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("commodity_decimal not between", value1, value2, "commodityDecimal");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalIsNull() {
            addCriterion("wholesale_decimal is null");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalIsNotNull() {
            addCriterion("wholesale_decimal is not null");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalEqualTo(BigDecimal value) {
            addCriterion("wholesale_decimal =", value, "wholesaleDecimal");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalNotEqualTo(BigDecimal value) {
            addCriterion("wholesale_decimal <>", value, "wholesaleDecimal");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalGreaterThan(BigDecimal value) {
            addCriterion("wholesale_decimal >", value, "wholesaleDecimal");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("wholesale_decimal >=", value, "wholesaleDecimal");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalLessThan(BigDecimal value) {
            addCriterion("wholesale_decimal <", value, "wholesaleDecimal");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("wholesale_decimal <=", value, "wholesaleDecimal");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalIn(List<BigDecimal> values) {
            addCriterion("wholesale_decimal in", values, "wholesaleDecimal");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalNotIn(List<BigDecimal> values) {
            addCriterion("wholesale_decimal not in", values, "wholesaleDecimal");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wholesale_decimal between", value1, value2, "wholesaleDecimal");
            return (Criteria) this;
        }

        public Criteria andWholesaleDecimalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wholesale_decimal not between", value1, value2, "wholesaleDecimal");
            return (Criteria) this;
        }

        public Criteria andLowDecimalIsNull() {
            addCriterion("low_decimal is null");
            return (Criteria) this;
        }

        public Criteria andLowDecimalIsNotNull() {
            addCriterion("low_decimal is not null");
            return (Criteria) this;
        }

        public Criteria andLowDecimalEqualTo(BigDecimal value) {
            addCriterion("low_decimal =", value, "lowDecimal");
            return (Criteria) this;
        }

        public Criteria andLowDecimalNotEqualTo(BigDecimal value) {
            addCriterion("low_decimal <>", value, "lowDecimal");
            return (Criteria) this;
        }

        public Criteria andLowDecimalGreaterThan(BigDecimal value) {
            addCriterion("low_decimal >", value, "lowDecimal");
            return (Criteria) this;
        }

        public Criteria andLowDecimalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("low_decimal >=", value, "lowDecimal");
            return (Criteria) this;
        }

        public Criteria andLowDecimalLessThan(BigDecimal value) {
            addCriterion("low_decimal <", value, "lowDecimal");
            return (Criteria) this;
        }

        public Criteria andLowDecimalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("low_decimal <=", value, "lowDecimal");
            return (Criteria) this;
        }

        public Criteria andLowDecimalIn(List<BigDecimal> values) {
            addCriterion("low_decimal in", values, "lowDecimal");
            return (Criteria) this;
        }

        public Criteria andLowDecimalNotIn(List<BigDecimal> values) {
            addCriterion("low_decimal not in", values, "lowDecimal");
            return (Criteria) this;
        }

        public Criteria andLowDecimalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("low_decimal between", value1, value2, "lowDecimal");
            return (Criteria) this;
        }

        public Criteria andLowDecimalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("low_decimal not between", value1, value2, "lowDecimal");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagIsNull() {
            addCriterion("default_flag is null");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagIsNotNull() {
            addCriterion("default_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagEqualTo(String value) {
            addCriterion("default_flag =", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagNotEqualTo(String value) {
            addCriterion("default_flag <>", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagGreaterThan(String value) {
            addCriterion("default_flag >", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagGreaterThanOrEqualTo(String value) {
            addCriterion("default_flag >=", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagLessThan(String value) {
            addCriterion("default_flag <", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagLessThanOrEqualTo(String value) {
            addCriterion("default_flag <=", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagLike(String value) {
            addCriterion("default_flag like", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagNotLike(String value) {
            addCriterion("default_flag not like", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagIn(List<String> values) {
            addCriterion("default_flag in", values, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagNotIn(List<String> values) {
            addCriterion("default_flag not in", values, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagBetween(String value1, String value2) {
            addCriterion("default_flag between", value1, value2, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagNotBetween(String value1, String value2) {
            addCriterion("default_flag not between", value1, value2, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateSerialIsNull() {
            addCriterion("create_serial is null");
            return (Criteria) this;
        }

        public Criteria andCreateSerialIsNotNull() {
            addCriterion("create_serial is not null");
            return (Criteria) this;
        }

        public Criteria andCreateSerialEqualTo(String value) {
            addCriterion("create_serial =", value, "createSerial");
            return (Criteria) this;
        }

        public Criteria andCreateSerialNotEqualTo(String value) {
            addCriterion("create_serial <>", value, "createSerial");
            return (Criteria) this;
        }

        public Criteria andCreateSerialGreaterThan(String value) {
            addCriterion("create_serial >", value, "createSerial");
            return (Criteria) this;
        }

        public Criteria andCreateSerialGreaterThanOrEqualTo(String value) {
            addCriterion("create_serial >=", value, "createSerial");
            return (Criteria) this;
        }

        public Criteria andCreateSerialLessThan(String value) {
            addCriterion("create_serial <", value, "createSerial");
            return (Criteria) this;
        }

        public Criteria andCreateSerialLessThanOrEqualTo(String value) {
            addCriterion("create_serial <=", value, "createSerial");
            return (Criteria) this;
        }

        public Criteria andCreateSerialLike(String value) {
            addCriterion("create_serial like", value, "createSerial");
            return (Criteria) this;
        }

        public Criteria andCreateSerialNotLike(String value) {
            addCriterion("create_serial not like", value, "createSerial");
            return (Criteria) this;
        }

        public Criteria andCreateSerialIn(List<String> values) {
            addCriterion("create_serial in", values, "createSerial");
            return (Criteria) this;
        }

        public Criteria andCreateSerialNotIn(List<String> values) {
            addCriterion("create_serial not in", values, "createSerial");
            return (Criteria) this;
        }

        public Criteria andCreateSerialBetween(String value1, String value2) {
            addCriterion("create_serial between", value1, value2, "createSerial");
            return (Criteria) this;
        }

        public Criteria andCreateSerialNotBetween(String value1, String value2) {
            addCriterion("create_serial not between", value1, value2, "createSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialIsNull() {
            addCriterion("update_serial is null");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialIsNotNull() {
            addCriterion("update_serial is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialEqualTo(String value) {
            addCriterion("update_serial =", value, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialNotEqualTo(String value) {
            addCriterion("update_serial <>", value, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialGreaterThan(String value) {
            addCriterion("update_serial >", value, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialGreaterThanOrEqualTo(String value) {
            addCriterion("update_serial >=", value, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialLessThan(String value) {
            addCriterion("update_serial <", value, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialLessThanOrEqualTo(String value) {
            addCriterion("update_serial <=", value, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialLike(String value) {
            addCriterion("update_serial like", value, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialNotLike(String value) {
            addCriterion("update_serial not like", value, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialIn(List<String> values) {
            addCriterion("update_serial in", values, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialNotIn(List<String> values) {
            addCriterion("update_serial not in", values, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialBetween(String value1, String value2) {
            addCriterion("update_serial between", value1, value2, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateSerialNotBetween(String value1, String value2) {
            addCriterion("update_serial not between", value1, value2, "updateSerial");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Long value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Long value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Long value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Long value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Long value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Long> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Long> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Long value1, Long value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Long value1, Long value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNull() {
            addCriterion("tenant_id is null");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNotNull() {
            addCriterion("tenant_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenantIdEqualTo(Long value) {
            addCriterion("tenant_id =", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotEqualTo(Long value) {
            addCriterion("tenant_id <>", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThan(Long value) {
            addCriterion("tenant_id >", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThanOrEqualTo(Long value) {
            addCriterion("tenant_id >=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThan(Long value) {
            addCriterion("tenant_id <", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThanOrEqualTo(Long value) {
            addCriterion("tenant_id <=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIn(List<Long> values) {
            addCriterion("tenant_id in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotIn(List<Long> values) {
            addCriterion("tenant_id not in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdBetween(Long value1, Long value2) {
            addCriterion("tenant_id between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotBetween(Long value1, Long value2) {
            addCriterion("tenant_id not between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNull() {
            addCriterion("delete_Flag is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNotNull() {
            addCriterion("delete_Flag is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagEqualTo(String value) {
            addCriterion("delete_Flag =", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotEqualTo(String value) {
            addCriterion("delete_Flag <>", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThan(String value) {
            addCriterion("delete_Flag >", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThanOrEqualTo(String value) {
            addCriterion("delete_Flag >=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThan(String value) {
            addCriterion("delete_Flag <", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThanOrEqualTo(String value) {
            addCriterion("delete_Flag <=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLike(String value) {
            addCriterion("delete_Flag like", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotLike(String value) {
            addCriterion("delete_Flag not like", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIn(List<String> values) {
            addCriterion("delete_Flag in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotIn(List<String> values) {
            addCriterion("delete_Flag not in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagBetween(String value1, String value2) {
            addCriterion("delete_Flag between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotBetween(String value1, String value2) {
            addCriterion("delete_Flag not between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}