package com.jsh.erp.datasource.entities;

import java.util.ArrayList;
import java.util.List;

public class UnitExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UnitExample() {
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

        public Criteria andBasicUnitIsNull() {
            addCriterion("basic_unit is null");
            return (Criteria) this;
        }

        public Criteria andBasicUnitIsNotNull() {
            addCriterion("basic_unit is not null");
            return (Criteria) this;
        }

        public Criteria andBasicUnitEqualTo(String value) {
            addCriterion("basic_unit =", value, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andBasicUnitNotEqualTo(String value) {
            addCriterion("basic_unit <>", value, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andBasicUnitGreaterThan(String value) {
            addCriterion("basic_unit >", value, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andBasicUnitGreaterThanOrEqualTo(String value) {
            addCriterion("basic_unit >=", value, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andBasicUnitLessThan(String value) {
            addCriterion("basic_unit <", value, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andBasicUnitLessThanOrEqualTo(String value) {
            addCriterion("basic_unit <=", value, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andBasicUnitLike(String value) {
            addCriterion("basic_unit like", value, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andBasicUnitNotLike(String value) {
            addCriterion("basic_unit not like", value, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andBasicUnitIn(List<String> values) {
            addCriterion("basic_unit in", values, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andBasicUnitNotIn(List<String> values) {
            addCriterion("basic_unit not in", values, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andBasicUnitBetween(String value1, String value2) {
            addCriterion("basic_unit between", value1, value2, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andBasicUnitNotBetween(String value1, String value2) {
            addCriterion("basic_unit not between", value1, value2, "basicUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitIsNull() {
            addCriterion("other_unit is null");
            return (Criteria) this;
        }

        public Criteria andOtherUnitIsNotNull() {
            addCriterion("other_unit is not null");
            return (Criteria) this;
        }

        public Criteria andOtherUnitEqualTo(String value) {
            addCriterion("other_unit =", value, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitNotEqualTo(String value) {
            addCriterion("other_unit <>", value, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitGreaterThan(String value) {
            addCriterion("other_unit >", value, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitGreaterThanOrEqualTo(String value) {
            addCriterion("other_unit >=", value, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitLessThan(String value) {
            addCriterion("other_unit <", value, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitLessThanOrEqualTo(String value) {
            addCriterion("other_unit <=", value, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitLike(String value) {
            addCriterion("other_unit like", value, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitNotLike(String value) {
            addCriterion("other_unit not like", value, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitIn(List<String> values) {
            addCriterion("other_unit in", values, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitNotIn(List<String> values) {
            addCriterion("other_unit not in", values, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitBetween(String value1, String value2) {
            addCriterion("other_unit between", value1, value2, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitNotBetween(String value1, String value2) {
            addCriterion("other_unit not between", value1, value2, "otherUnit");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoIsNull() {
            addCriterion("other_unit_two is null");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoIsNotNull() {
            addCriterion("other_unit_two is not null");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoEqualTo(String value) {
            addCriterion("other_unit_two =", value, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoNotEqualTo(String value) {
            addCriterion("other_unit_two <>", value, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoGreaterThan(String value) {
            addCriterion("other_unit_two >", value, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoGreaterThanOrEqualTo(String value) {
            addCriterion("other_unit_two >=", value, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoLessThan(String value) {
            addCriterion("other_unit_two <", value, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoLessThanOrEqualTo(String value) {
            addCriterion("other_unit_two <=", value, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoLike(String value) {
            addCriterion("other_unit_two like", value, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoNotLike(String value) {
            addCriterion("other_unit_two not like", value, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoIn(List<String> values) {
            addCriterion("other_unit_two in", values, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoNotIn(List<String> values) {
            addCriterion("other_unit_two not in", values, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoBetween(String value1, String value2) {
            addCriterion("other_unit_two between", value1, value2, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitTwoNotBetween(String value1, String value2) {
            addCriterion("other_unit_two not between", value1, value2, "otherUnitTwo");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeIsNull() {
            addCriterion("other_unit_three is null");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeIsNotNull() {
            addCriterion("other_unit_three is not null");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeEqualTo(String value) {
            addCriterion("other_unit_three =", value, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeNotEqualTo(String value) {
            addCriterion("other_unit_three <>", value, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeGreaterThan(String value) {
            addCriterion("other_unit_three >", value, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeGreaterThanOrEqualTo(String value) {
            addCriterion("other_unit_three >=", value, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeLessThan(String value) {
            addCriterion("other_unit_three <", value, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeLessThanOrEqualTo(String value) {
            addCriterion("other_unit_three <=", value, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeLike(String value) {
            addCriterion("other_unit_three like", value, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeNotLike(String value) {
            addCriterion("other_unit_three not like", value, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeIn(List<String> values) {
            addCriterion("other_unit_three in", values, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeNotIn(List<String> values) {
            addCriterion("other_unit_three not in", values, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeBetween(String value1, String value2) {
            addCriterion("other_unit_three between", value1, value2, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andOtherUnitThreeNotBetween(String value1, String value2) {
            addCriterion("other_unit_three not between", value1, value2, "otherUnitThree");
            return (Criteria) this;
        }

        public Criteria andRatioIsNull() {
            addCriterion("ratio is null");
            return (Criteria) this;
        }

        public Criteria andRatioIsNotNull() {
            addCriterion("ratio is not null");
            return (Criteria) this;
        }

        public Criteria andRatioEqualTo(Integer value) {
            addCriterion("ratio =", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioNotEqualTo(Integer value) {
            addCriterion("ratio <>", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioGreaterThan(Integer value) {
            addCriterion("ratio >", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioGreaterThanOrEqualTo(Integer value) {
            addCriterion("ratio >=", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioLessThan(Integer value) {
            addCriterion("ratio <", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioLessThanOrEqualTo(Integer value) {
            addCriterion("ratio <=", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioIn(List<Integer> values) {
            addCriterion("ratio in", values, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioNotIn(List<Integer> values) {
            addCriterion("ratio not in", values, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioBetween(Integer value1, Integer value2) {
            addCriterion("ratio between", value1, value2, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioNotBetween(Integer value1, Integer value2) {
            addCriterion("ratio not between", value1, value2, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioTwoIsNull() {
            addCriterion("ratio_two is null");
            return (Criteria) this;
        }

        public Criteria andRatioTwoIsNotNull() {
            addCriterion("ratio_two is not null");
            return (Criteria) this;
        }

        public Criteria andRatioTwoEqualTo(Integer value) {
            addCriterion("ratio_two =", value, "ratioTwo");
            return (Criteria) this;
        }

        public Criteria andRatioTwoNotEqualTo(Integer value) {
            addCriterion("ratio_two <>", value, "ratioTwo");
            return (Criteria) this;
        }

        public Criteria andRatioTwoGreaterThan(Integer value) {
            addCriterion("ratio_two >", value, "ratioTwo");
            return (Criteria) this;
        }

        public Criteria andRatioTwoGreaterThanOrEqualTo(Integer value) {
            addCriterion("ratio_two >=", value, "ratioTwo");
            return (Criteria) this;
        }

        public Criteria andRatioTwoLessThan(Integer value) {
            addCriterion("ratio_two <", value, "ratioTwo");
            return (Criteria) this;
        }

        public Criteria andRatioTwoLessThanOrEqualTo(Integer value) {
            addCriterion("ratio_two <=", value, "ratioTwo");
            return (Criteria) this;
        }

        public Criteria andRatioTwoIn(List<Integer> values) {
            addCriterion("ratio_two in", values, "ratioTwo");
            return (Criteria) this;
        }

        public Criteria andRatioTwoNotIn(List<Integer> values) {
            addCriterion("ratio_two not in", values, "ratioTwo");
            return (Criteria) this;
        }

        public Criteria andRatioTwoBetween(Integer value1, Integer value2) {
            addCriterion("ratio_two between", value1, value2, "ratioTwo");
            return (Criteria) this;
        }

        public Criteria andRatioTwoNotBetween(Integer value1, Integer value2) {
            addCriterion("ratio_two not between", value1, value2, "ratioTwo");
            return (Criteria) this;
        }

        public Criteria andRatioThreeIsNull() {
            addCriterion("ratio_three is null");
            return (Criteria) this;
        }

        public Criteria andRatioThreeIsNotNull() {
            addCriterion("ratio_three is not null");
            return (Criteria) this;
        }

        public Criteria andRatioThreeEqualTo(Integer value) {
            addCriterion("ratio_three =", value, "ratioThree");
            return (Criteria) this;
        }

        public Criteria andRatioThreeNotEqualTo(Integer value) {
            addCriterion("ratio_three <>", value, "ratioThree");
            return (Criteria) this;
        }

        public Criteria andRatioThreeGreaterThan(Integer value) {
            addCriterion("ratio_three >", value, "ratioThree");
            return (Criteria) this;
        }

        public Criteria andRatioThreeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ratio_three >=", value, "ratioThree");
            return (Criteria) this;
        }

        public Criteria andRatioThreeLessThan(Integer value) {
            addCriterion("ratio_three <", value, "ratioThree");
            return (Criteria) this;
        }

        public Criteria andRatioThreeLessThanOrEqualTo(Integer value) {
            addCriterion("ratio_three <=", value, "ratioThree");
            return (Criteria) this;
        }

        public Criteria andRatioThreeIn(List<Integer> values) {
            addCriterion("ratio_three in", values, "ratioThree");
            return (Criteria) this;
        }

        public Criteria andRatioThreeNotIn(List<Integer> values) {
            addCriterion("ratio_three not in", values, "ratioThree");
            return (Criteria) this;
        }

        public Criteria andRatioThreeBetween(Integer value1, Integer value2) {
            addCriterion("ratio_three between", value1, value2, "ratioThree");
            return (Criteria) this;
        }

        public Criteria andRatioThreeNotBetween(Integer value1, Integer value2) {
            addCriterion("ratio_three not between", value1, value2, "ratioThree");
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