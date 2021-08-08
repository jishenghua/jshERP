package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DepotItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DepotItemExample() {
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

        public Criteria andHeaderIdIsNull() {
            addCriterion("header_id is null");
            return (Criteria) this;
        }

        public Criteria andHeaderIdIsNotNull() {
            addCriterion("header_id is not null");
            return (Criteria) this;
        }

        public Criteria andHeaderIdEqualTo(Long value) {
            addCriterion("header_id =", value, "headerId");
            return (Criteria) this;
        }

        public Criteria andHeaderIdNotEqualTo(Long value) {
            addCriterion("header_id <>", value, "headerId");
            return (Criteria) this;
        }

        public Criteria andHeaderIdGreaterThan(Long value) {
            addCriterion("header_id >", value, "headerId");
            return (Criteria) this;
        }

        public Criteria andHeaderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("header_id >=", value, "headerId");
            return (Criteria) this;
        }

        public Criteria andHeaderIdLessThan(Long value) {
            addCriterion("header_id <", value, "headerId");
            return (Criteria) this;
        }

        public Criteria andHeaderIdLessThanOrEqualTo(Long value) {
            addCriterion("header_id <=", value, "headerId");
            return (Criteria) this;
        }

        public Criteria andHeaderIdIn(List<Long> values) {
            addCriterion("header_id in", values, "headerId");
            return (Criteria) this;
        }

        public Criteria andHeaderIdNotIn(List<Long> values) {
            addCriterion("header_id not in", values, "headerId");
            return (Criteria) this;
        }

        public Criteria andHeaderIdBetween(Long value1, Long value2) {
            addCriterion("header_id between", value1, value2, "headerId");
            return (Criteria) this;
        }

        public Criteria andHeaderIdNotBetween(Long value1, Long value2) {
            addCriterion("header_id not between", value1, value2, "headerId");
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

        public Criteria andMaterialExtendIdIsNull() {
            addCriterion("material_extend_id is null");
            return (Criteria) this;
        }

        public Criteria andMaterialExtendIdIsNotNull() {
            addCriterion("material_extend_id is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialExtendIdEqualTo(Long value) {
            addCriterion("material_extend_id =", value, "materialExtendId");
            return (Criteria) this;
        }

        public Criteria andMaterialExtendIdNotEqualTo(Long value) {
            addCriterion("material_extend_id <>", value, "materialExtendId");
            return (Criteria) this;
        }

        public Criteria andMaterialExtendIdGreaterThan(Long value) {
            addCriterion("material_extend_id >", value, "materialExtendId");
            return (Criteria) this;
        }

        public Criteria andMaterialExtendIdGreaterThanOrEqualTo(Long value) {
            addCriterion("material_extend_id >=", value, "materialExtendId");
            return (Criteria) this;
        }

        public Criteria andMaterialExtendIdLessThan(Long value) {
            addCriterion("material_extend_id <", value, "materialExtendId");
            return (Criteria) this;
        }

        public Criteria andMaterialExtendIdLessThanOrEqualTo(Long value) {
            addCriterion("material_extend_id <=", value, "materialExtendId");
            return (Criteria) this;
        }

        public Criteria andMaterialExtendIdIn(List<Long> values) {
            addCriterion("material_extend_id in", values, "materialExtendId");
            return (Criteria) this;
        }

        public Criteria andMaterialExtendIdNotIn(List<Long> values) {
            addCriterion("material_extend_id not in", values, "materialExtendId");
            return (Criteria) this;
        }

        public Criteria andMaterialExtendIdBetween(Long value1, Long value2) {
            addCriterion("material_extend_id between", value1, value2, "materialExtendId");
            return (Criteria) this;
        }

        public Criteria andMaterialExtendIdNotBetween(Long value1, Long value2) {
            addCriterion("material_extend_id not between", value1, value2, "materialExtendId");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitIsNull() {
            addCriterion("material_unit is null");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitIsNotNull() {
            addCriterion("material_unit is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitEqualTo(String value) {
            addCriterion("material_unit =", value, "materialUnit");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitNotEqualTo(String value) {
            addCriterion("material_unit <>", value, "materialUnit");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitGreaterThan(String value) {
            addCriterion("material_unit >", value, "materialUnit");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitGreaterThanOrEqualTo(String value) {
            addCriterion("material_unit >=", value, "materialUnit");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitLessThan(String value) {
            addCriterion("material_unit <", value, "materialUnit");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitLessThanOrEqualTo(String value) {
            addCriterion("material_unit <=", value, "materialUnit");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitLike(String value) {
            addCriterion("material_unit like", value, "materialUnit");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitNotLike(String value) {
            addCriterion("material_unit not like", value, "materialUnit");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitIn(List<String> values) {
            addCriterion("material_unit in", values, "materialUnit");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitNotIn(List<String> values) {
            addCriterion("material_unit not in", values, "materialUnit");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitBetween(String value1, String value2) {
            addCriterion("material_unit between", value1, value2, "materialUnit");
            return (Criteria) this;
        }

        public Criteria andMaterialUnitNotBetween(String value1, String value2) {
            addCriterion("material_unit not between", value1, value2, "materialUnit");
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

        public Criteria andOperNumberIsNull() {
            addCriterion("oper_number is null");
            return (Criteria) this;
        }

        public Criteria andOperNumberIsNotNull() {
            addCriterion("oper_number is not null");
            return (Criteria) this;
        }

        public Criteria andOperNumberEqualTo(BigDecimal value) {
            addCriterion("oper_number =", value, "operNumber");
            return (Criteria) this;
        }

        public Criteria andOperNumberNotEqualTo(BigDecimal value) {
            addCriterion("oper_number <>", value, "operNumber");
            return (Criteria) this;
        }

        public Criteria andOperNumberGreaterThan(BigDecimal value) {
            addCriterion("oper_number >", value, "operNumber");
            return (Criteria) this;
        }

        public Criteria andOperNumberGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("oper_number >=", value, "operNumber");
            return (Criteria) this;
        }

        public Criteria andOperNumberLessThan(BigDecimal value) {
            addCriterion("oper_number <", value, "operNumber");
            return (Criteria) this;
        }

        public Criteria andOperNumberLessThanOrEqualTo(BigDecimal value) {
            addCriterion("oper_number <=", value, "operNumber");
            return (Criteria) this;
        }

        public Criteria andOperNumberIn(List<BigDecimal> values) {
            addCriterion("oper_number in", values, "operNumber");
            return (Criteria) this;
        }

        public Criteria andOperNumberNotIn(List<BigDecimal> values) {
            addCriterion("oper_number not in", values, "operNumber");
            return (Criteria) this;
        }

        public Criteria andOperNumberBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("oper_number between", value1, value2, "operNumber");
            return (Criteria) this;
        }

        public Criteria andOperNumberNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("oper_number not between", value1, value2, "operNumber");
            return (Criteria) this;
        }

        public Criteria andBasicNumberIsNull() {
            addCriterion("basic_number is null");
            return (Criteria) this;
        }

        public Criteria andBasicNumberIsNotNull() {
            addCriterion("basic_number is not null");
            return (Criteria) this;
        }

        public Criteria andBasicNumberEqualTo(BigDecimal value) {
            addCriterion("basic_number =", value, "basicNumber");
            return (Criteria) this;
        }

        public Criteria andBasicNumberNotEqualTo(BigDecimal value) {
            addCriterion("basic_number <>", value, "basicNumber");
            return (Criteria) this;
        }

        public Criteria andBasicNumberGreaterThan(BigDecimal value) {
            addCriterion("basic_number >", value, "basicNumber");
            return (Criteria) this;
        }

        public Criteria andBasicNumberGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("basic_number >=", value, "basicNumber");
            return (Criteria) this;
        }

        public Criteria andBasicNumberLessThan(BigDecimal value) {
            addCriterion("basic_number <", value, "basicNumber");
            return (Criteria) this;
        }

        public Criteria andBasicNumberLessThanOrEqualTo(BigDecimal value) {
            addCriterion("basic_number <=", value, "basicNumber");
            return (Criteria) this;
        }

        public Criteria andBasicNumberIn(List<BigDecimal> values) {
            addCriterion("basic_number in", values, "basicNumber");
            return (Criteria) this;
        }

        public Criteria andBasicNumberNotIn(List<BigDecimal> values) {
            addCriterion("basic_number not in", values, "basicNumber");
            return (Criteria) this;
        }

        public Criteria andBasicNumberBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("basic_number between", value1, value2, "basicNumber");
            return (Criteria) this;
        }

        public Criteria andBasicNumberNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("basic_number not between", value1, value2, "basicNumber");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIsNull() {
            addCriterion("unit_price is null");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIsNotNull() {
            addCriterion("unit_price is not null");
            return (Criteria) this;
        }

        public Criteria andUnitPriceEqualTo(BigDecimal value) {
            addCriterion("unit_price =", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotEqualTo(BigDecimal value) {
            addCriterion("unit_price <>", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceGreaterThan(BigDecimal value) {
            addCriterion("unit_price >", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unit_price >=", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceLessThan(BigDecimal value) {
            addCriterion("unit_price <", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unit_price <=", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIn(List<BigDecimal> values) {
            addCriterion("unit_price in", values, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotIn(List<BigDecimal> values) {
            addCriterion("unit_price not in", values, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unit_price between", value1, value2, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unit_price not between", value1, value2, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceIsNull() {
            addCriterion("tax_unit_price is null");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceIsNotNull() {
            addCriterion("tax_unit_price is not null");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceEqualTo(BigDecimal value) {
            addCriterion("tax_unit_price =", value, "taxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceNotEqualTo(BigDecimal value) {
            addCriterion("tax_unit_price <>", value, "taxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceGreaterThan(BigDecimal value) {
            addCriterion("tax_unit_price >", value, "taxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_unit_price >=", value, "taxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceLessThan(BigDecimal value) {
            addCriterion("tax_unit_price <", value, "taxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_unit_price <=", value, "taxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceIn(List<BigDecimal> values) {
            addCriterion("tax_unit_price in", values, "taxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceNotIn(List<BigDecimal> values) {
            addCriterion("tax_unit_price not in", values, "taxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_unit_price between", value1, value2, "taxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andTaxUnitPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_unit_price not between", value1, value2, "taxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andAllPriceIsNull() {
            addCriterion("all_price is null");
            return (Criteria) this;
        }

        public Criteria andAllPriceIsNotNull() {
            addCriterion("all_price is not null");
            return (Criteria) this;
        }

        public Criteria andAllPriceEqualTo(BigDecimal value) {
            addCriterion("all_price =", value, "allPrice");
            return (Criteria) this;
        }

        public Criteria andAllPriceNotEqualTo(BigDecimal value) {
            addCriterion("all_price <>", value, "allPrice");
            return (Criteria) this;
        }

        public Criteria andAllPriceGreaterThan(BigDecimal value) {
            addCriterion("all_price >", value, "allPrice");
            return (Criteria) this;
        }

        public Criteria andAllPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("all_price >=", value, "allPrice");
            return (Criteria) this;
        }

        public Criteria andAllPriceLessThan(BigDecimal value) {
            addCriterion("all_price <", value, "allPrice");
            return (Criteria) this;
        }

        public Criteria andAllPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("all_price <=", value, "allPrice");
            return (Criteria) this;
        }

        public Criteria andAllPriceIn(List<BigDecimal> values) {
            addCriterion("all_price in", values, "allPrice");
            return (Criteria) this;
        }

        public Criteria andAllPriceNotIn(List<BigDecimal> values) {
            addCriterion("all_price not in", values, "allPrice");
            return (Criteria) this;
        }

        public Criteria andAllPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("all_price between", value1, value2, "allPrice");
            return (Criteria) this;
        }

        public Criteria andAllPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("all_price not between", value1, value2, "allPrice");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andDepotIdIsNull() {
            addCriterion("depot_id is null");
            return (Criteria) this;
        }

        public Criteria andDepotIdIsNotNull() {
            addCriterion("depot_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepotIdEqualTo(Long value) {
            addCriterion("depot_id =", value, "depotId");
            return (Criteria) this;
        }

        public Criteria andDepotIdNotEqualTo(Long value) {
            addCriterion("depot_id <>", value, "depotId");
            return (Criteria) this;
        }

        public Criteria andDepotIdGreaterThan(Long value) {
            addCriterion("depot_id >", value, "depotId");
            return (Criteria) this;
        }

        public Criteria andDepotIdGreaterThanOrEqualTo(Long value) {
            addCriterion("depot_id >=", value, "depotId");
            return (Criteria) this;
        }

        public Criteria andDepotIdLessThan(Long value) {
            addCriterion("depot_id <", value, "depotId");
            return (Criteria) this;
        }

        public Criteria andDepotIdLessThanOrEqualTo(Long value) {
            addCriterion("depot_id <=", value, "depotId");
            return (Criteria) this;
        }

        public Criteria andDepotIdIn(List<Long> values) {
            addCriterion("depot_id in", values, "depotId");
            return (Criteria) this;
        }

        public Criteria andDepotIdNotIn(List<Long> values) {
            addCriterion("depot_id not in", values, "depotId");
            return (Criteria) this;
        }

        public Criteria andDepotIdBetween(Long value1, Long value2) {
            addCriterion("depot_id between", value1, value2, "depotId");
            return (Criteria) this;
        }

        public Criteria andDepotIdNotBetween(Long value1, Long value2) {
            addCriterion("depot_id not between", value1, value2, "depotId");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdIsNull() {
            addCriterion("another_depot_id is null");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdIsNotNull() {
            addCriterion("another_depot_id is not null");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdEqualTo(Long value) {
            addCriterion("another_depot_id =", value, "anotherDepotId");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdNotEqualTo(Long value) {
            addCriterion("another_depot_id <>", value, "anotherDepotId");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdGreaterThan(Long value) {
            addCriterion("another_depot_id >", value, "anotherDepotId");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdGreaterThanOrEqualTo(Long value) {
            addCriterion("another_depot_id >=", value, "anotherDepotId");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdLessThan(Long value) {
            addCriterion("another_depot_id <", value, "anotherDepotId");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdLessThanOrEqualTo(Long value) {
            addCriterion("another_depot_id <=", value, "anotherDepotId");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdIn(List<Long> values) {
            addCriterion("another_depot_id in", values, "anotherDepotId");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdNotIn(List<Long> values) {
            addCriterion("another_depot_id not in", values, "anotherDepotId");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdBetween(Long value1, Long value2) {
            addCriterion("another_depot_id between", value1, value2, "anotherDepotId");
            return (Criteria) this;
        }

        public Criteria andAnotherDepotIdNotBetween(Long value1, Long value2) {
            addCriterion("another_depot_id not between", value1, value2, "anotherDepotId");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNull() {
            addCriterion("tax_rate is null");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNotNull() {
            addCriterion("tax_rate is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRateEqualTo(BigDecimal value) {
            addCriterion("tax_rate =", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotEqualTo(BigDecimal value) {
            addCriterion("tax_rate <>", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThan(BigDecimal value) {
            addCriterion("tax_rate >", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_rate >=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThan(BigDecimal value) {
            addCriterion("tax_rate <", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_rate <=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateIn(List<BigDecimal> values) {
            addCriterion("tax_rate in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotIn(List<BigDecimal> values) {
            addCriterion("tax_rate not in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_rate between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_rate not between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyIsNull() {
            addCriterion("tax_money is null");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyIsNotNull() {
            addCriterion("tax_money is not null");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyEqualTo(BigDecimal value) {
            addCriterion("tax_money =", value, "taxMoney");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyNotEqualTo(BigDecimal value) {
            addCriterion("tax_money <>", value, "taxMoney");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyGreaterThan(BigDecimal value) {
            addCriterion("tax_money >", value, "taxMoney");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_money >=", value, "taxMoney");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyLessThan(BigDecimal value) {
            addCriterion("tax_money <", value, "taxMoney");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_money <=", value, "taxMoney");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyIn(List<BigDecimal> values) {
            addCriterion("tax_money in", values, "taxMoney");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyNotIn(List<BigDecimal> values) {
            addCriterion("tax_money not in", values, "taxMoney");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_money between", value1, value2, "taxMoney");
            return (Criteria) this;
        }

        public Criteria andTaxMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_money not between", value1, value2, "taxMoney");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyIsNull() {
            addCriterion("tax_last_money is null");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyIsNotNull() {
            addCriterion("tax_last_money is not null");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyEqualTo(BigDecimal value) {
            addCriterion("tax_last_money =", value, "taxLastMoney");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyNotEqualTo(BigDecimal value) {
            addCriterion("tax_last_money <>", value, "taxLastMoney");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyGreaterThan(BigDecimal value) {
            addCriterion("tax_last_money >", value, "taxLastMoney");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_last_money >=", value, "taxLastMoney");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyLessThan(BigDecimal value) {
            addCriterion("tax_last_money <", value, "taxLastMoney");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_last_money <=", value, "taxLastMoney");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyIn(List<BigDecimal> values) {
            addCriterion("tax_last_money in", values, "taxLastMoney");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyNotIn(List<BigDecimal> values) {
            addCriterion("tax_last_money not in", values, "taxLastMoney");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_last_money between", value1, value2, "taxLastMoney");
            return (Criteria) this;
        }

        public Criteria andTaxLastMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_last_money not between", value1, value2, "taxLastMoney");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeIsNull() {
            addCriterion("material_type is null");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeIsNotNull() {
            addCriterion("material_type is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeEqualTo(String value) {
            addCriterion("material_type =", value, "materialType");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeNotEqualTo(String value) {
            addCriterion("material_type <>", value, "materialType");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeGreaterThan(String value) {
            addCriterion("material_type >", value, "materialType");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeGreaterThanOrEqualTo(String value) {
            addCriterion("material_type >=", value, "materialType");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeLessThan(String value) {
            addCriterion("material_type <", value, "materialType");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeLessThanOrEqualTo(String value) {
            addCriterion("material_type <=", value, "materialType");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeLike(String value) {
            addCriterion("material_type like", value, "materialType");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeNotLike(String value) {
            addCriterion("material_type not like", value, "materialType");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeIn(List<String> values) {
            addCriterion("material_type in", values, "materialType");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeNotIn(List<String> values) {
            addCriterion("material_type not in", values, "materialType");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeBetween(String value1, String value2) {
            addCriterion("material_type between", value1, value2, "materialType");
            return (Criteria) this;
        }

        public Criteria andMaterialTypeNotBetween(String value1, String value2) {
            addCriterion("material_type not between", value1, value2, "materialType");
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
            addCriterion("delete_flag is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNotNull() {
            addCriterion("delete_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagEqualTo(String value) {
            addCriterion("delete_flag =", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotEqualTo(String value) {
            addCriterion("delete_flag <>", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThan(String value) {
            addCriterion("delete_flag >", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThanOrEqualTo(String value) {
            addCriterion("delete_flag >=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThan(String value) {
            addCriterion("delete_flag <", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThanOrEqualTo(String value) {
            addCriterion("delete_flag <=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLike(String value) {
            addCriterion("delete_flag like", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotLike(String value) {
            addCriterion("delete_flag not like", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIn(List<String> values) {
            addCriterion("delete_flag in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotIn(List<String> values) {
            addCriterion("delete_flag not in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagBetween(String value1, String value2) {
            addCriterion("delete_flag between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotBetween(String value1, String value2) {
            addCriterion("delete_flag not between", value1, value2, "deleteFlag");
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