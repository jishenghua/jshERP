package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MaterialExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MaterialExample() {
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

        public Criteria andCategoryIdIsNull() {
            addCriterion("category_id is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNotNull() {
            addCriterion("category_id is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdEqualTo(Long value) {
            addCriterion("category_id =", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotEqualTo(Long value) {
            addCriterion("category_id <>", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThan(Long value) {
            addCriterion("category_id >", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("category_id >=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThan(Long value) {
            addCriterion("category_id <", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThanOrEqualTo(Long value) {
            addCriterion("category_id <=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIn(List<Long> values) {
            addCriterion("category_id in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotIn(List<Long> values) {
            addCriterion("category_id not in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdBetween(Long value1, Long value2) {
            addCriterion("category_id between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotBetween(Long value1, Long value2) {
            addCriterion("category_id not between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andMfrsIsNull() {
            addCriterion("mfrs is null");
            return (Criteria) this;
        }

        public Criteria andMfrsIsNotNull() {
            addCriterion("mfrs is not null");
            return (Criteria) this;
        }

        public Criteria andMfrsEqualTo(String value) {
            addCriterion("mfrs =", value, "mfrs");
            return (Criteria) this;
        }

        public Criteria andMfrsNotEqualTo(String value) {
            addCriterion("mfrs <>", value, "mfrs");
            return (Criteria) this;
        }

        public Criteria andMfrsGreaterThan(String value) {
            addCriterion("mfrs >", value, "mfrs");
            return (Criteria) this;
        }

        public Criteria andMfrsGreaterThanOrEqualTo(String value) {
            addCriterion("mfrs >=", value, "mfrs");
            return (Criteria) this;
        }

        public Criteria andMfrsLessThan(String value) {
            addCriterion("mfrs <", value, "mfrs");
            return (Criteria) this;
        }

        public Criteria andMfrsLessThanOrEqualTo(String value) {
            addCriterion("mfrs <=", value, "mfrs");
            return (Criteria) this;
        }

        public Criteria andMfrsLike(String value) {
            addCriterion("mfrs like", value, "mfrs");
            return (Criteria) this;
        }

        public Criteria andMfrsNotLike(String value) {
            addCriterion("mfrs not like", value, "mfrs");
            return (Criteria) this;
        }

        public Criteria andMfrsIn(List<String> values) {
            addCriterion("mfrs in", values, "mfrs");
            return (Criteria) this;
        }

        public Criteria andMfrsNotIn(List<String> values) {
            addCriterion("mfrs not in", values, "mfrs");
            return (Criteria) this;
        }

        public Criteria andMfrsBetween(String value1, String value2) {
            addCriterion("mfrs between", value1, value2, "mfrs");
            return (Criteria) this;
        }

        public Criteria andMfrsNotBetween(String value1, String value2) {
            addCriterion("mfrs not between", value1, value2, "mfrs");
            return (Criteria) this;
        }

        public Criteria andModelIsNull() {
            addCriterion("model is null");
            return (Criteria) this;
        }

        public Criteria andModelIsNotNull() {
            addCriterion("model is not null");
            return (Criteria) this;
        }

        public Criteria andModelEqualTo(String value) {
            addCriterion("model =", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotEqualTo(String value) {
            addCriterion("model <>", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThan(String value) {
            addCriterion("model >", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThanOrEqualTo(String value) {
            addCriterion("model >=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThan(String value) {
            addCriterion("model <", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThanOrEqualTo(String value) {
            addCriterion("model <=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLike(String value) {
            addCriterion("model like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotLike(String value) {
            addCriterion("model not like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelIn(List<String> values) {
            addCriterion("model in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotIn(List<String> values) {
            addCriterion("model not in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelBetween(String value1, String value2) {
            addCriterion("model between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotBetween(String value1, String value2) {
            addCriterion("model not between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andStandardIsNull() {
            addCriterion("standard is null");
            return (Criteria) this;
        }

        public Criteria andStandardIsNotNull() {
            addCriterion("standard is not null");
            return (Criteria) this;
        }

        public Criteria andStandardEqualTo(String value) {
            addCriterion("standard =", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardNotEqualTo(String value) {
            addCriterion("standard <>", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardGreaterThan(String value) {
            addCriterion("standard >", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardGreaterThanOrEqualTo(String value) {
            addCriterion("standard >=", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardLessThan(String value) {
            addCriterion("standard <", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardLessThanOrEqualTo(String value) {
            addCriterion("standard <=", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardLike(String value) {
            addCriterion("standard like", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardNotLike(String value) {
            addCriterion("standard not like", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardIn(List<String> values) {
            addCriterion("standard in", values, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardNotIn(List<String> values) {
            addCriterion("standard not in", values, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardBetween(String value1, String value2) {
            addCriterion("standard between", value1, value2, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardNotBetween(String value1, String value2) {
            addCriterion("standard not between", value1, value2, "standard");
            return (Criteria) this;
        }

        public Criteria andColorIsNull() {
            addCriterion("color is null");
            return (Criteria) this;
        }

        public Criteria andColorIsNotNull() {
            addCriterion("color is not null");
            return (Criteria) this;
        }

        public Criteria andColorEqualTo(String value) {
            addCriterion("color =", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotEqualTo(String value) {
            addCriterion("color <>", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorGreaterThan(String value) {
            addCriterion("color >", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorGreaterThanOrEqualTo(String value) {
            addCriterion("color >=", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLessThan(String value) {
            addCriterion("color <", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLessThanOrEqualTo(String value) {
            addCriterion("color <=", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLike(String value) {
            addCriterion("color like", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotLike(String value) {
            addCriterion("color not like", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorIn(List<String> values) {
            addCriterion("color in", values, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotIn(List<String> values) {
            addCriterion("color not in", values, "color");
            return (Criteria) this;
        }

        public Criteria andColorBetween(String value1, String value2) {
            addCriterion("color between", value1, value2, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotBetween(String value1, String value2) {
            addCriterion("color not between", value1, value2, "color");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
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

        public Criteria andImgNameIsNull() {
            addCriterion("img_name is null");
            return (Criteria) this;
        }

        public Criteria andImgNameIsNotNull() {
            addCriterion("img_name is not null");
            return (Criteria) this;
        }

        public Criteria andImgNameEqualTo(String value) {
            addCriterion("img_name =", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameNotEqualTo(String value) {
            addCriterion("img_name <>", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameGreaterThan(String value) {
            addCriterion("img_name >", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameGreaterThanOrEqualTo(String value) {
            addCriterion("img_name >=", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameLessThan(String value) {
            addCriterion("img_name <", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameLessThanOrEqualTo(String value) {
            addCriterion("img_name <=", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameLike(String value) {
            addCriterion("img_name like", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameNotLike(String value) {
            addCriterion("img_name not like", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameIn(List<String> values) {
            addCriterion("img_name in", values, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameNotIn(List<String> values) {
            addCriterion("img_name not in", values, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameBetween(String value1, String value2) {
            addCriterion("img_name between", value1, value2, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameNotBetween(String value1, String value2) {
            addCriterion("img_name not between", value1, value2, "imgName");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNull() {
            addCriterion("unit_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNotNull() {
            addCriterion("unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitIdEqualTo(Long value) {
            addCriterion("unit_id =", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotEqualTo(Long value) {
            addCriterion("unit_id <>", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThan(Long value) {
            addCriterion("unit_id >", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThanOrEqualTo(Long value) {
            addCriterion("unit_id >=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThan(Long value) {
            addCriterion("unit_id <", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThanOrEqualTo(Long value) {
            addCriterion("unit_id <=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdIn(List<Long> values) {
            addCriterion("unit_id in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotIn(List<Long> values) {
            addCriterion("unit_id not in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdBetween(Long value1, Long value2) {
            addCriterion("unit_id between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotBetween(Long value1, Long value2) {
            addCriterion("unit_id not between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andExpiryNumIsNull() {
            addCriterion("expiry_num is null");
            return (Criteria) this;
        }

        public Criteria andExpiryNumIsNotNull() {
            addCriterion("expiry_num is not null");
            return (Criteria) this;
        }

        public Criteria andExpiryNumEqualTo(Integer value) {
            addCriterion("expiry_num =", value, "expiryNum");
            return (Criteria) this;
        }

        public Criteria andExpiryNumNotEqualTo(Integer value) {
            addCriterion("expiry_num <>", value, "expiryNum");
            return (Criteria) this;
        }

        public Criteria andExpiryNumGreaterThan(Integer value) {
            addCriterion("expiry_num >", value, "expiryNum");
            return (Criteria) this;
        }

        public Criteria andExpiryNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("expiry_num >=", value, "expiryNum");
            return (Criteria) this;
        }

        public Criteria andExpiryNumLessThan(Integer value) {
            addCriterion("expiry_num <", value, "expiryNum");
            return (Criteria) this;
        }

        public Criteria andExpiryNumLessThanOrEqualTo(Integer value) {
            addCriterion("expiry_num <=", value, "expiryNum");
            return (Criteria) this;
        }

        public Criteria andExpiryNumIn(List<Integer> values) {
            addCriterion("expiry_num in", values, "expiryNum");
            return (Criteria) this;
        }

        public Criteria andExpiryNumNotIn(List<Integer> values) {
            addCriterion("expiry_num not in", values, "expiryNum");
            return (Criteria) this;
        }

        public Criteria andExpiryNumBetween(Integer value1, Integer value2) {
            addCriterion("expiry_num between", value1, value2, "expiryNum");
            return (Criteria) this;
        }

        public Criteria andExpiryNumNotBetween(Integer value1, Integer value2) {
            addCriterion("expiry_num not between", value1, value2, "expiryNum");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(BigDecimal value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(BigDecimal value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(BigDecimal value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(BigDecimal value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<BigDecimal> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<BigDecimal> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNull() {
            addCriterion("enabled is null");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNotNull() {
            addCriterion("enabled is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledEqualTo(Boolean value) {
            addCriterion("enabled =", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotEqualTo(Boolean value) {
            addCriterion("enabled <>", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThan(Boolean value) {
            addCriterion("enabled >", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enabled >=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThan(Boolean value) {
            addCriterion("enabled <", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThanOrEqualTo(Boolean value) {
            addCriterion("enabled <=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledIn(List<Boolean> values) {
            addCriterion("enabled in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotIn(List<Boolean> values) {
            addCriterion("enabled not in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled not between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andOtherField1IsNull() {
            addCriterion("other_field1 is null");
            return (Criteria) this;
        }

        public Criteria andOtherField1IsNotNull() {
            addCriterion("other_field1 is not null");
            return (Criteria) this;
        }

        public Criteria andOtherField1EqualTo(String value) {
            addCriterion("other_field1 =", value, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField1NotEqualTo(String value) {
            addCriterion("other_field1 <>", value, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField1GreaterThan(String value) {
            addCriterion("other_field1 >", value, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField1GreaterThanOrEqualTo(String value) {
            addCriterion("other_field1 >=", value, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField1LessThan(String value) {
            addCriterion("other_field1 <", value, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField1LessThanOrEqualTo(String value) {
            addCriterion("other_field1 <=", value, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField1Like(String value) {
            addCriterion("other_field1 like", value, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField1NotLike(String value) {
            addCriterion("other_field1 not like", value, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField1In(List<String> values) {
            addCriterion("other_field1 in", values, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField1NotIn(List<String> values) {
            addCriterion("other_field1 not in", values, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField1Between(String value1, String value2) {
            addCriterion("other_field1 between", value1, value2, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField1NotBetween(String value1, String value2) {
            addCriterion("other_field1 not between", value1, value2, "otherField1");
            return (Criteria) this;
        }

        public Criteria andOtherField2IsNull() {
            addCriterion("other_field2 is null");
            return (Criteria) this;
        }

        public Criteria andOtherField2IsNotNull() {
            addCriterion("other_field2 is not null");
            return (Criteria) this;
        }

        public Criteria andOtherField2EqualTo(String value) {
            addCriterion("other_field2 =", value, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField2NotEqualTo(String value) {
            addCriterion("other_field2 <>", value, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField2GreaterThan(String value) {
            addCriterion("other_field2 >", value, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField2GreaterThanOrEqualTo(String value) {
            addCriterion("other_field2 >=", value, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField2LessThan(String value) {
            addCriterion("other_field2 <", value, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField2LessThanOrEqualTo(String value) {
            addCriterion("other_field2 <=", value, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField2Like(String value) {
            addCriterion("other_field2 like", value, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField2NotLike(String value) {
            addCriterion("other_field2 not like", value, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField2In(List<String> values) {
            addCriterion("other_field2 in", values, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField2NotIn(List<String> values) {
            addCriterion("other_field2 not in", values, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField2Between(String value1, String value2) {
            addCriterion("other_field2 between", value1, value2, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField2NotBetween(String value1, String value2) {
            addCriterion("other_field2 not between", value1, value2, "otherField2");
            return (Criteria) this;
        }

        public Criteria andOtherField3IsNull() {
            addCriterion("other_field3 is null");
            return (Criteria) this;
        }

        public Criteria andOtherField3IsNotNull() {
            addCriterion("other_field3 is not null");
            return (Criteria) this;
        }

        public Criteria andOtherField3EqualTo(String value) {
            addCriterion("other_field3 =", value, "otherField3");
            return (Criteria) this;
        }

        public Criteria andOtherField3NotEqualTo(String value) {
            addCriterion("other_field3 <>", value, "otherField3");
            return (Criteria) this;
        }

        public Criteria andOtherField3GreaterThan(String value) {
            addCriterion("other_field3 >", value, "otherField3");
            return (Criteria) this;
        }

        public Criteria andOtherField3GreaterThanOrEqualTo(String value) {
            addCriterion("other_field3 >=", value, "otherField3");
            return (Criteria) this;
        }

        public Criteria andOtherField3LessThan(String value) {
            addCriterion("other_field3 <", value, "otherField3");
            return (Criteria) this;
        }

        public Criteria andOtherField3LessThanOrEqualTo(String value) {
            addCriterion("other_field3 <=", value, "otherField3");
            return (Criteria) this;
        }

        public Criteria andOtherField3Like(String value) {
            addCriterion("other_field3 like", value, "otherField3");
            return (Criteria) this;
        }

        public Criteria andOtherField3NotLike(String value) {
            addCriterion("other_field3 not like", value, "otherField3");
            return (Criteria) this;
        }

        public Criteria andOtherField3In(List<String> values) {
            addCriterion("other_field3 in", values, "otherField3");
            return (Criteria) this;
        }

        public Criteria andOtherField3NotIn(List<String> values) {
            addCriterion("other_field3 not in", values, "otherField3");
            return (Criteria) this;
        }

        public Criteria andOtherField3Between(String value1, String value2) {
            addCriterion("other_field3 between", value1, value2, "otherField3");
            return (Criteria) this;
        }

        public Criteria andOtherField3NotBetween(String value1, String value2) {
            addCriterion("other_field3 not between", value1, value2, "otherField3");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberIsNull() {
            addCriterion("enable_serial_number is null");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberIsNotNull() {
            addCriterion("enable_serial_number is not null");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberEqualTo(String value) {
            addCriterion("enable_serial_number =", value, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberNotEqualTo(String value) {
            addCriterion("enable_serial_number <>", value, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberGreaterThan(String value) {
            addCriterion("enable_serial_number >", value, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberGreaterThanOrEqualTo(String value) {
            addCriterion("enable_serial_number >=", value, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberLessThan(String value) {
            addCriterion("enable_serial_number <", value, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberLessThanOrEqualTo(String value) {
            addCriterion("enable_serial_number <=", value, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberLike(String value) {
            addCriterion("enable_serial_number like", value, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberNotLike(String value) {
            addCriterion("enable_serial_number not like", value, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberIn(List<String> values) {
            addCriterion("enable_serial_number in", values, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberNotIn(List<String> values) {
            addCriterion("enable_serial_number not in", values, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberBetween(String value1, String value2) {
            addCriterion("enable_serial_number between", value1, value2, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableSerialNumberNotBetween(String value1, String value2) {
            addCriterion("enable_serial_number not between", value1, value2, "enableSerialNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberIsNull() {
            addCriterion("enable_batch_number is null");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberIsNotNull() {
            addCriterion("enable_batch_number is not null");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberEqualTo(String value) {
            addCriterion("enable_batch_number =", value, "enableBatchNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberNotEqualTo(String value) {
            addCriterion("enable_batch_number <>", value, "enableBatchNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberGreaterThan(String value) {
            addCriterion("enable_batch_number >", value, "enableBatchNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberGreaterThanOrEqualTo(String value) {
            addCriterion("enable_batch_number >=", value, "enableBatchNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberLessThan(String value) {
            addCriterion("enable_batch_number <", value, "enableBatchNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberLessThanOrEqualTo(String value) {
            addCriterion("enable_batch_number <=", value, "enableBatchNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberLike(String value) {
            addCriterion("enable_batch_number like", value, "enableBatchNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberNotLike(String value) {
            addCriterion("enable_batch_number not like", value, "enableBatchNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberIn(List<String> values) {
            addCriterion("enable_batch_number in", values, "enableBatchNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberNotIn(List<String> values) {
            addCriterion("enable_batch_number not in", values, "enableBatchNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberBetween(String value1, String value2) {
            addCriterion("enable_batch_number between", value1, value2, "enableBatchNumber");
            return (Criteria) this;
        }

        public Criteria andEnableBatchNumberNotBetween(String value1, String value2) {
            addCriterion("enable_batch_number not between", value1, value2, "enableBatchNumber");
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