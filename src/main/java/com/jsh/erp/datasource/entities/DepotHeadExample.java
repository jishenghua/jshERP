package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DepotHeadExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DepotHeadExample() {
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
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("Type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("Type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("Type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("Type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("Type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("Type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("Type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("Type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("Type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("Type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("Type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("Type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("Type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("Type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSubtypeIsNull() {
            addCriterion("SubType is null");
            return (Criteria) this;
        }

        public Criteria andSubtypeIsNotNull() {
            addCriterion("SubType is not null");
            return (Criteria) this;
        }

        public Criteria andSubtypeEqualTo(String value) {
            addCriterion("SubType =", value, "subtype");
            return (Criteria) this;
        }

        public Criteria andSubtypeNotEqualTo(String value) {
            addCriterion("SubType <>", value, "subtype");
            return (Criteria) this;
        }

        public Criteria andSubtypeGreaterThan(String value) {
            addCriterion("SubType >", value, "subtype");
            return (Criteria) this;
        }

        public Criteria andSubtypeGreaterThanOrEqualTo(String value) {
            addCriterion("SubType >=", value, "subtype");
            return (Criteria) this;
        }

        public Criteria andSubtypeLessThan(String value) {
            addCriterion("SubType <", value, "subtype");
            return (Criteria) this;
        }

        public Criteria andSubtypeLessThanOrEqualTo(String value) {
            addCriterion("SubType <=", value, "subtype");
            return (Criteria) this;
        }

        public Criteria andSubtypeLike(String value) {
            addCriterion("SubType like", value, "subtype");
            return (Criteria) this;
        }

        public Criteria andSubtypeNotLike(String value) {
            addCriterion("SubType not like", value, "subtype");
            return (Criteria) this;
        }

        public Criteria andSubtypeIn(List<String> values) {
            addCriterion("SubType in", values, "subtype");
            return (Criteria) this;
        }

        public Criteria andSubtypeNotIn(List<String> values) {
            addCriterion("SubType not in", values, "subtype");
            return (Criteria) this;
        }

        public Criteria andSubtypeBetween(String value1, String value2) {
            addCriterion("SubType between", value1, value2, "subtype");
            return (Criteria) this;
        }

        public Criteria andSubtypeNotBetween(String value1, String value2) {
            addCriterion("SubType not between", value1, value2, "subtype");
            return (Criteria) this;
        }

        public Criteria andProjectidIsNull() {
            addCriterion("ProjectId is null");
            return (Criteria) this;
        }

        public Criteria andProjectidIsNotNull() {
            addCriterion("ProjectId is not null");
            return (Criteria) this;
        }

        public Criteria andProjectidEqualTo(Long value) {
            addCriterion("ProjectId =", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidNotEqualTo(Long value) {
            addCriterion("ProjectId <>", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidGreaterThan(Long value) {
            addCriterion("ProjectId >", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidGreaterThanOrEqualTo(Long value) {
            addCriterion("ProjectId >=", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidLessThan(Long value) {
            addCriterion("ProjectId <", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidLessThanOrEqualTo(Long value) {
            addCriterion("ProjectId <=", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidIn(List<Long> values) {
            addCriterion("ProjectId in", values, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidNotIn(List<Long> values) {
            addCriterion("ProjectId not in", values, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidBetween(Long value1, Long value2) {
            addCriterion("ProjectId between", value1, value2, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidNotBetween(Long value1, Long value2) {
            addCriterion("ProjectId not between", value1, value2, "projectid");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberIsNull() {
            addCriterion("DefaultNumber is null");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberIsNotNull() {
            addCriterion("DefaultNumber is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberEqualTo(String value) {
            addCriterion("DefaultNumber =", value, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberNotEqualTo(String value) {
            addCriterion("DefaultNumber <>", value, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberGreaterThan(String value) {
            addCriterion("DefaultNumber >", value, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberGreaterThanOrEqualTo(String value) {
            addCriterion("DefaultNumber >=", value, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberLessThan(String value) {
            addCriterion("DefaultNumber <", value, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberLessThanOrEqualTo(String value) {
            addCriterion("DefaultNumber <=", value, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberLike(String value) {
            addCriterion("DefaultNumber like", value, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberNotLike(String value) {
            addCriterion("DefaultNumber not like", value, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberIn(List<String> values) {
            addCriterion("DefaultNumber in", values, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberNotIn(List<String> values) {
            addCriterion("DefaultNumber not in", values, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberBetween(String value1, String value2) {
            addCriterion("DefaultNumber between", value1, value2, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andDefaultnumberNotBetween(String value1, String value2) {
            addCriterion("DefaultNumber not between", value1, value2, "defaultnumber");
            return (Criteria) this;
        }

        public Criteria andNumberIsNull() {
            addCriterion("Number is null");
            return (Criteria) this;
        }

        public Criteria andNumberIsNotNull() {
            addCriterion("Number is not null");
            return (Criteria) this;
        }

        public Criteria andNumberEqualTo(String value) {
            addCriterion("Number =", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(String value) {
            addCriterion("Number <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(String value) {
            addCriterion("Number >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(String value) {
            addCriterion("Number >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(String value) {
            addCriterion("Number <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(String value) {
            addCriterion("Number <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLike(String value) {
            addCriterion("Number like", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotLike(String value) {
            addCriterion("Number not like", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<String> values) {
            addCriterion("Number in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<String> values) {
            addCriterion("Number not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(String value1, String value2) {
            addCriterion("Number between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(String value1, String value2) {
            addCriterion("Number not between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameIsNull() {
            addCriterion("OperPersonName is null");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameIsNotNull() {
            addCriterion("OperPersonName is not null");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameEqualTo(String value) {
            addCriterion("OperPersonName =", value, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameNotEqualTo(String value) {
            addCriterion("OperPersonName <>", value, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameGreaterThan(String value) {
            addCriterion("OperPersonName >", value, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameGreaterThanOrEqualTo(String value) {
            addCriterion("OperPersonName >=", value, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameLessThan(String value) {
            addCriterion("OperPersonName <", value, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameLessThanOrEqualTo(String value) {
            addCriterion("OperPersonName <=", value, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameLike(String value) {
            addCriterion("OperPersonName like", value, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameNotLike(String value) {
            addCriterion("OperPersonName not like", value, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameIn(List<String> values) {
            addCriterion("OperPersonName in", values, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameNotIn(List<String> values) {
            addCriterion("OperPersonName not in", values, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameBetween(String value1, String value2) {
            addCriterion("OperPersonName between", value1, value2, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andOperpersonnameNotBetween(String value1, String value2) {
            addCriterion("OperPersonName not between", value1, value2, "operpersonname");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("CreateTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("CreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("CreateTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("CreateTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("CreateTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CreateTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("CreateTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("CreateTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("CreateTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("CreateTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("CreateTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("CreateTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andOpertimeIsNull() {
            addCriterion("OperTime is null");
            return (Criteria) this;
        }

        public Criteria andOpertimeIsNotNull() {
            addCriterion("OperTime is not null");
            return (Criteria) this;
        }

        public Criteria andOpertimeEqualTo(Date value) {
            addCriterion("OperTime =", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeNotEqualTo(Date value) {
            addCriterion("OperTime <>", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeGreaterThan(Date value) {
            addCriterion("OperTime >", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeGreaterThanOrEqualTo(Date value) {
            addCriterion("OperTime >=", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeLessThan(Date value) {
            addCriterion("OperTime <", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeLessThanOrEqualTo(Date value) {
            addCriterion("OperTime <=", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeIn(List<Date> values) {
            addCriterion("OperTime in", values, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeNotIn(List<Date> values) {
            addCriterion("OperTime not in", values, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeBetween(Date value1, Date value2) {
            addCriterion("OperTime between", value1, value2, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeNotBetween(Date value1, Date value2) {
            addCriterion("OperTime not between", value1, value2, "opertime");
            return (Criteria) this;
        }

        public Criteria andOrganidIsNull() {
            addCriterion("OrganId is null");
            return (Criteria) this;
        }

        public Criteria andOrganidIsNotNull() {
            addCriterion("OrganId is not null");
            return (Criteria) this;
        }

        public Criteria andOrganidEqualTo(Long value) {
            addCriterion("OrganId =", value, "organid");
            return (Criteria) this;
        }

        public Criteria andOrganidNotEqualTo(Long value) {
            addCriterion("OrganId <>", value, "organid");
            return (Criteria) this;
        }

        public Criteria andOrganidGreaterThan(Long value) {
            addCriterion("OrganId >", value, "organid");
            return (Criteria) this;
        }

        public Criteria andOrganidGreaterThanOrEqualTo(Long value) {
            addCriterion("OrganId >=", value, "organid");
            return (Criteria) this;
        }

        public Criteria andOrganidLessThan(Long value) {
            addCriterion("OrganId <", value, "organid");
            return (Criteria) this;
        }

        public Criteria andOrganidLessThanOrEqualTo(Long value) {
            addCriterion("OrganId <=", value, "organid");
            return (Criteria) this;
        }

        public Criteria andOrganidIn(List<Long> values) {
            addCriterion("OrganId in", values, "organid");
            return (Criteria) this;
        }

        public Criteria andOrganidNotIn(List<Long> values) {
            addCriterion("OrganId not in", values, "organid");
            return (Criteria) this;
        }

        public Criteria andOrganidBetween(Long value1, Long value2) {
            addCriterion("OrganId between", value1, value2, "organid");
            return (Criteria) this;
        }

        public Criteria andOrganidNotBetween(Long value1, Long value2) {
            addCriterion("OrganId not between", value1, value2, "organid");
            return (Criteria) this;
        }

        public Criteria andHandspersonidIsNull() {
            addCriterion("HandsPersonId is null");
            return (Criteria) this;
        }

        public Criteria andHandspersonidIsNotNull() {
            addCriterion("HandsPersonId is not null");
            return (Criteria) this;
        }

        public Criteria andHandspersonidEqualTo(Long value) {
            addCriterion("HandsPersonId =", value, "handspersonid");
            return (Criteria) this;
        }

        public Criteria andHandspersonidNotEqualTo(Long value) {
            addCriterion("HandsPersonId <>", value, "handspersonid");
            return (Criteria) this;
        }

        public Criteria andHandspersonidGreaterThan(Long value) {
            addCriterion("HandsPersonId >", value, "handspersonid");
            return (Criteria) this;
        }

        public Criteria andHandspersonidGreaterThanOrEqualTo(Long value) {
            addCriterion("HandsPersonId >=", value, "handspersonid");
            return (Criteria) this;
        }

        public Criteria andHandspersonidLessThan(Long value) {
            addCriterion("HandsPersonId <", value, "handspersonid");
            return (Criteria) this;
        }

        public Criteria andHandspersonidLessThanOrEqualTo(Long value) {
            addCriterion("HandsPersonId <=", value, "handspersonid");
            return (Criteria) this;
        }

        public Criteria andHandspersonidIn(List<Long> values) {
            addCriterion("HandsPersonId in", values, "handspersonid");
            return (Criteria) this;
        }

        public Criteria andHandspersonidNotIn(List<Long> values) {
            addCriterion("HandsPersonId not in", values, "handspersonid");
            return (Criteria) this;
        }

        public Criteria andHandspersonidBetween(Long value1, Long value2) {
            addCriterion("HandsPersonId between", value1, value2, "handspersonid");
            return (Criteria) this;
        }

        public Criteria andHandspersonidNotBetween(Long value1, Long value2) {
            addCriterion("HandsPersonId not between", value1, value2, "handspersonid");
            return (Criteria) this;
        }

        public Criteria andAccountidIsNull() {
            addCriterion("AccountId is null");
            return (Criteria) this;
        }

        public Criteria andAccountidIsNotNull() {
            addCriterion("AccountId is not null");
            return (Criteria) this;
        }

        public Criteria andAccountidEqualTo(Long value) {
            addCriterion("AccountId =", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidNotEqualTo(Long value) {
            addCriterion("AccountId <>", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidGreaterThan(Long value) {
            addCriterion("AccountId >", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidGreaterThanOrEqualTo(Long value) {
            addCriterion("AccountId >=", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidLessThan(Long value) {
            addCriterion("AccountId <", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidLessThanOrEqualTo(Long value) {
            addCriterion("AccountId <=", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidIn(List<Long> values) {
            addCriterion("AccountId in", values, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidNotIn(List<Long> values) {
            addCriterion("AccountId not in", values, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidBetween(Long value1, Long value2) {
            addCriterion("AccountId between", value1, value2, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidNotBetween(Long value1, Long value2) {
            addCriterion("AccountId not between", value1, value2, "accountid");
            return (Criteria) this;
        }

        public Criteria andChangeamountIsNull() {
            addCriterion("ChangeAmount is null");
            return (Criteria) this;
        }

        public Criteria andChangeamountIsNotNull() {
            addCriterion("ChangeAmount is not null");
            return (Criteria) this;
        }

        public Criteria andChangeamountEqualTo(BigDecimal value) {
            addCriterion("ChangeAmount =", value, "changeamount");
            return (Criteria) this;
        }

        public Criteria andChangeamountNotEqualTo(BigDecimal value) {
            addCriterion("ChangeAmount <>", value, "changeamount");
            return (Criteria) this;
        }

        public Criteria andChangeamountGreaterThan(BigDecimal value) {
            addCriterion("ChangeAmount >", value, "changeamount");
            return (Criteria) this;
        }

        public Criteria andChangeamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ChangeAmount >=", value, "changeamount");
            return (Criteria) this;
        }

        public Criteria andChangeamountLessThan(BigDecimal value) {
            addCriterion("ChangeAmount <", value, "changeamount");
            return (Criteria) this;
        }

        public Criteria andChangeamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ChangeAmount <=", value, "changeamount");
            return (Criteria) this;
        }

        public Criteria andChangeamountIn(List<BigDecimal> values) {
            addCriterion("ChangeAmount in", values, "changeamount");
            return (Criteria) this;
        }

        public Criteria andChangeamountNotIn(List<BigDecimal> values) {
            addCriterion("ChangeAmount not in", values, "changeamount");
            return (Criteria) this;
        }

        public Criteria andChangeamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ChangeAmount between", value1, value2, "changeamount");
            return (Criteria) this;
        }

        public Criteria andChangeamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ChangeAmount not between", value1, value2, "changeamount");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidIsNull() {
            addCriterion("AllocationProjectId is null");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidIsNotNull() {
            addCriterion("AllocationProjectId is not null");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidEqualTo(Long value) {
            addCriterion("AllocationProjectId =", value, "allocationprojectid");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidNotEqualTo(Long value) {
            addCriterion("AllocationProjectId <>", value, "allocationprojectid");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidGreaterThan(Long value) {
            addCriterion("AllocationProjectId >", value, "allocationprojectid");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidGreaterThanOrEqualTo(Long value) {
            addCriterion("AllocationProjectId >=", value, "allocationprojectid");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidLessThan(Long value) {
            addCriterion("AllocationProjectId <", value, "allocationprojectid");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidLessThanOrEqualTo(Long value) {
            addCriterion("AllocationProjectId <=", value, "allocationprojectid");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidIn(List<Long> values) {
            addCriterion("AllocationProjectId in", values, "allocationprojectid");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidNotIn(List<Long> values) {
            addCriterion("AllocationProjectId not in", values, "allocationprojectid");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidBetween(Long value1, Long value2) {
            addCriterion("AllocationProjectId between", value1, value2, "allocationprojectid");
            return (Criteria) this;
        }

        public Criteria andAllocationprojectidNotBetween(Long value1, Long value2) {
            addCriterion("AllocationProjectId not between", value1, value2, "allocationprojectid");
            return (Criteria) this;
        }

        public Criteria andTotalpriceIsNull() {
            addCriterion("TotalPrice is null");
            return (Criteria) this;
        }

        public Criteria andTotalpriceIsNotNull() {
            addCriterion("TotalPrice is not null");
            return (Criteria) this;
        }

        public Criteria andTotalpriceEqualTo(BigDecimal value) {
            addCriterion("TotalPrice =", value, "totalprice");
            return (Criteria) this;
        }

        public Criteria andTotalpriceNotEqualTo(BigDecimal value) {
            addCriterion("TotalPrice <>", value, "totalprice");
            return (Criteria) this;
        }

        public Criteria andTotalpriceGreaterThan(BigDecimal value) {
            addCriterion("TotalPrice >", value, "totalprice");
            return (Criteria) this;
        }

        public Criteria andTotalpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TotalPrice >=", value, "totalprice");
            return (Criteria) this;
        }

        public Criteria andTotalpriceLessThan(BigDecimal value) {
            addCriterion("TotalPrice <", value, "totalprice");
            return (Criteria) this;
        }

        public Criteria andTotalpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TotalPrice <=", value, "totalprice");
            return (Criteria) this;
        }

        public Criteria andTotalpriceIn(List<BigDecimal> values) {
            addCriterion("TotalPrice in", values, "totalprice");
            return (Criteria) this;
        }

        public Criteria andTotalpriceNotIn(List<BigDecimal> values) {
            addCriterion("TotalPrice not in", values, "totalprice");
            return (Criteria) this;
        }

        public Criteria andTotalpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TotalPrice between", value1, value2, "totalprice");
            return (Criteria) this;
        }

        public Criteria andTotalpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TotalPrice not between", value1, value2, "totalprice");
            return (Criteria) this;
        }

        public Criteria andPaytypeIsNull() {
            addCriterion("PayType is null");
            return (Criteria) this;
        }

        public Criteria andPaytypeIsNotNull() {
            addCriterion("PayType is not null");
            return (Criteria) this;
        }

        public Criteria andPaytypeEqualTo(String value) {
            addCriterion("PayType =", value, "paytype");
            return (Criteria) this;
        }

        public Criteria andPaytypeNotEqualTo(String value) {
            addCriterion("PayType <>", value, "paytype");
            return (Criteria) this;
        }

        public Criteria andPaytypeGreaterThan(String value) {
            addCriterion("PayType >", value, "paytype");
            return (Criteria) this;
        }

        public Criteria andPaytypeGreaterThanOrEqualTo(String value) {
            addCriterion("PayType >=", value, "paytype");
            return (Criteria) this;
        }

        public Criteria andPaytypeLessThan(String value) {
            addCriterion("PayType <", value, "paytype");
            return (Criteria) this;
        }

        public Criteria andPaytypeLessThanOrEqualTo(String value) {
            addCriterion("PayType <=", value, "paytype");
            return (Criteria) this;
        }

        public Criteria andPaytypeLike(String value) {
            addCriterion("PayType like", value, "paytype");
            return (Criteria) this;
        }

        public Criteria andPaytypeNotLike(String value) {
            addCriterion("PayType not like", value, "paytype");
            return (Criteria) this;
        }

        public Criteria andPaytypeIn(List<String> values) {
            addCriterion("PayType in", values, "paytype");
            return (Criteria) this;
        }

        public Criteria andPaytypeNotIn(List<String> values) {
            addCriterion("PayType not in", values, "paytype");
            return (Criteria) this;
        }

        public Criteria andPaytypeBetween(String value1, String value2) {
            addCriterion("PayType between", value1, value2, "paytype");
            return (Criteria) this;
        }

        public Criteria andPaytypeNotBetween(String value1, String value2) {
            addCriterion("PayType not between", value1, value2, "paytype");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("Remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("Remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("Remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("Remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("Remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("Remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("Remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("Remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("Remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("Remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("Remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("Remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("Remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andSalesmanIsNull() {
            addCriterion("Salesman is null");
            return (Criteria) this;
        }

        public Criteria andSalesmanIsNotNull() {
            addCriterion("Salesman is not null");
            return (Criteria) this;
        }

        public Criteria andSalesmanEqualTo(String value) {
            addCriterion("Salesman =", value, "salesman");
            return (Criteria) this;
        }

        public Criteria andSalesmanNotEqualTo(String value) {
            addCriterion("Salesman <>", value, "salesman");
            return (Criteria) this;
        }

        public Criteria andSalesmanGreaterThan(String value) {
            addCriterion("Salesman >", value, "salesman");
            return (Criteria) this;
        }

        public Criteria andSalesmanGreaterThanOrEqualTo(String value) {
            addCriterion("Salesman >=", value, "salesman");
            return (Criteria) this;
        }

        public Criteria andSalesmanLessThan(String value) {
            addCriterion("Salesman <", value, "salesman");
            return (Criteria) this;
        }

        public Criteria andSalesmanLessThanOrEqualTo(String value) {
            addCriterion("Salesman <=", value, "salesman");
            return (Criteria) this;
        }

        public Criteria andSalesmanLike(String value) {
            addCriterion("Salesman like", value, "salesman");
            return (Criteria) this;
        }

        public Criteria andSalesmanNotLike(String value) {
            addCriterion("Salesman not like", value, "salesman");
            return (Criteria) this;
        }

        public Criteria andSalesmanIn(List<String> values) {
            addCriterion("Salesman in", values, "salesman");
            return (Criteria) this;
        }

        public Criteria andSalesmanNotIn(List<String> values) {
            addCriterion("Salesman not in", values, "salesman");
            return (Criteria) this;
        }

        public Criteria andSalesmanBetween(String value1, String value2) {
            addCriterion("Salesman between", value1, value2, "salesman");
            return (Criteria) this;
        }

        public Criteria andSalesmanNotBetween(String value1, String value2) {
            addCriterion("Salesman not between", value1, value2, "salesman");
            return (Criteria) this;
        }

        public Criteria andAccountidlistIsNull() {
            addCriterion("AccountIdList is null");
            return (Criteria) this;
        }

        public Criteria andAccountidlistIsNotNull() {
            addCriterion("AccountIdList is not null");
            return (Criteria) this;
        }

        public Criteria andAccountidlistEqualTo(String value) {
            addCriterion("AccountIdList =", value, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountidlistNotEqualTo(String value) {
            addCriterion("AccountIdList <>", value, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountidlistGreaterThan(String value) {
            addCriterion("AccountIdList >", value, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountidlistGreaterThanOrEqualTo(String value) {
            addCriterion("AccountIdList >=", value, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountidlistLessThan(String value) {
            addCriterion("AccountIdList <", value, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountidlistLessThanOrEqualTo(String value) {
            addCriterion("AccountIdList <=", value, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountidlistLike(String value) {
            addCriterion("AccountIdList like", value, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountidlistNotLike(String value) {
            addCriterion("AccountIdList not like", value, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountidlistIn(List<String> values) {
            addCriterion("AccountIdList in", values, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountidlistNotIn(List<String> values) {
            addCriterion("AccountIdList not in", values, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountidlistBetween(String value1, String value2) {
            addCriterion("AccountIdList between", value1, value2, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountidlistNotBetween(String value1, String value2) {
            addCriterion("AccountIdList not between", value1, value2, "accountidlist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistIsNull() {
            addCriterion("AccountMoneyList is null");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistIsNotNull() {
            addCriterion("AccountMoneyList is not null");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistEqualTo(String value) {
            addCriterion("AccountMoneyList =", value, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistNotEqualTo(String value) {
            addCriterion("AccountMoneyList <>", value, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistGreaterThan(String value) {
            addCriterion("AccountMoneyList >", value, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistGreaterThanOrEqualTo(String value) {
            addCriterion("AccountMoneyList >=", value, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistLessThan(String value) {
            addCriterion("AccountMoneyList <", value, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistLessThanOrEqualTo(String value) {
            addCriterion("AccountMoneyList <=", value, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistLike(String value) {
            addCriterion("AccountMoneyList like", value, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistNotLike(String value) {
            addCriterion("AccountMoneyList not like", value, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistIn(List<String> values) {
            addCriterion("AccountMoneyList in", values, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistNotIn(List<String> values) {
            addCriterion("AccountMoneyList not in", values, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistBetween(String value1, String value2) {
            addCriterion("AccountMoneyList between", value1, value2, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andAccountmoneylistNotBetween(String value1, String value2) {
            addCriterion("AccountMoneyList not between", value1, value2, "accountmoneylist");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNull() {
            addCriterion("Discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("Discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(BigDecimal value) {
            addCriterion("Discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(BigDecimal value) {
            addCriterion("Discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(BigDecimal value) {
            addCriterion("Discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(BigDecimal value) {
            addCriterion("Discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<BigDecimal> values) {
            addCriterion("Discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<BigDecimal> values) {
            addCriterion("Discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Discount not between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyIsNull() {
            addCriterion("DiscountMoney is null");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyIsNotNull() {
            addCriterion("DiscountMoney is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyEqualTo(BigDecimal value) {
            addCriterion("DiscountMoney =", value, "discountmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyNotEqualTo(BigDecimal value) {
            addCriterion("DiscountMoney <>", value, "discountmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyGreaterThan(BigDecimal value) {
            addCriterion("DiscountMoney >", value, "discountmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DiscountMoney >=", value, "discountmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyLessThan(BigDecimal value) {
            addCriterion("DiscountMoney <", value, "discountmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DiscountMoney <=", value, "discountmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyIn(List<BigDecimal> values) {
            addCriterion("DiscountMoney in", values, "discountmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyNotIn(List<BigDecimal> values) {
            addCriterion("DiscountMoney not in", values, "discountmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DiscountMoney between", value1, value2, "discountmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountmoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DiscountMoney not between", value1, value2, "discountmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyIsNull() {
            addCriterion("DiscountLastMoney is null");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyIsNotNull() {
            addCriterion("DiscountLastMoney is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyEqualTo(BigDecimal value) {
            addCriterion("DiscountLastMoney =", value, "discountlastmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyNotEqualTo(BigDecimal value) {
            addCriterion("DiscountLastMoney <>", value, "discountlastmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyGreaterThan(BigDecimal value) {
            addCriterion("DiscountLastMoney >", value, "discountlastmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DiscountLastMoney >=", value, "discountlastmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyLessThan(BigDecimal value) {
            addCriterion("DiscountLastMoney <", value, "discountlastmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DiscountLastMoney <=", value, "discountlastmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyIn(List<BigDecimal> values) {
            addCriterion("DiscountLastMoney in", values, "discountlastmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyNotIn(List<BigDecimal> values) {
            addCriterion("DiscountLastMoney not in", values, "discountlastmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DiscountLastMoney between", value1, value2, "discountlastmoney");
            return (Criteria) this;
        }

        public Criteria andDiscountlastmoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DiscountLastMoney not between", value1, value2, "discountlastmoney");
            return (Criteria) this;
        }

        public Criteria andOthermoneyIsNull() {
            addCriterion("OtherMoney is null");
            return (Criteria) this;
        }

        public Criteria andOthermoneyIsNotNull() {
            addCriterion("OtherMoney is not null");
            return (Criteria) this;
        }

        public Criteria andOthermoneyEqualTo(BigDecimal value) {
            addCriterion("OtherMoney =", value, "othermoney");
            return (Criteria) this;
        }

        public Criteria andOthermoneyNotEqualTo(BigDecimal value) {
            addCriterion("OtherMoney <>", value, "othermoney");
            return (Criteria) this;
        }

        public Criteria andOthermoneyGreaterThan(BigDecimal value) {
            addCriterion("OtherMoney >", value, "othermoney");
            return (Criteria) this;
        }

        public Criteria andOthermoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OtherMoney >=", value, "othermoney");
            return (Criteria) this;
        }

        public Criteria andOthermoneyLessThan(BigDecimal value) {
            addCriterion("OtherMoney <", value, "othermoney");
            return (Criteria) this;
        }

        public Criteria andOthermoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OtherMoney <=", value, "othermoney");
            return (Criteria) this;
        }

        public Criteria andOthermoneyIn(List<BigDecimal> values) {
            addCriterion("OtherMoney in", values, "othermoney");
            return (Criteria) this;
        }

        public Criteria andOthermoneyNotIn(List<BigDecimal> values) {
            addCriterion("OtherMoney not in", values, "othermoney");
            return (Criteria) this;
        }

        public Criteria andOthermoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OtherMoney between", value1, value2, "othermoney");
            return (Criteria) this;
        }

        public Criteria andOthermoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OtherMoney not between", value1, value2, "othermoney");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistIsNull() {
            addCriterion("OtherMoneyList is null");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistIsNotNull() {
            addCriterion("OtherMoneyList is not null");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistEqualTo(String value) {
            addCriterion("OtherMoneyList =", value, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistNotEqualTo(String value) {
            addCriterion("OtherMoneyList <>", value, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistGreaterThan(String value) {
            addCriterion("OtherMoneyList >", value, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistGreaterThanOrEqualTo(String value) {
            addCriterion("OtherMoneyList >=", value, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistLessThan(String value) {
            addCriterion("OtherMoneyList <", value, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistLessThanOrEqualTo(String value) {
            addCriterion("OtherMoneyList <=", value, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistLike(String value) {
            addCriterion("OtherMoneyList like", value, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistNotLike(String value) {
            addCriterion("OtherMoneyList not like", value, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistIn(List<String> values) {
            addCriterion("OtherMoneyList in", values, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistNotIn(List<String> values) {
            addCriterion("OtherMoneyList not in", values, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistBetween(String value1, String value2) {
            addCriterion("OtherMoneyList between", value1, value2, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneylistNotBetween(String value1, String value2) {
            addCriterion("OtherMoneyList not between", value1, value2, "othermoneylist");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemIsNull() {
            addCriterion("OtherMoneyItem is null");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemIsNotNull() {
            addCriterion("OtherMoneyItem is not null");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemEqualTo(String value) {
            addCriterion("OtherMoneyItem =", value, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemNotEqualTo(String value) {
            addCriterion("OtherMoneyItem <>", value, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemGreaterThan(String value) {
            addCriterion("OtherMoneyItem >", value, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemGreaterThanOrEqualTo(String value) {
            addCriterion("OtherMoneyItem >=", value, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemLessThan(String value) {
            addCriterion("OtherMoneyItem <", value, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemLessThanOrEqualTo(String value) {
            addCriterion("OtherMoneyItem <=", value, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemLike(String value) {
            addCriterion("OtherMoneyItem like", value, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemNotLike(String value) {
            addCriterion("OtherMoneyItem not like", value, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemIn(List<String> values) {
            addCriterion("OtherMoneyItem in", values, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemNotIn(List<String> values) {
            addCriterion("OtherMoneyItem not in", values, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemBetween(String value1, String value2) {
            addCriterion("OtherMoneyItem between", value1, value2, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andOthermoneyitemNotBetween(String value1, String value2) {
            addCriterion("OtherMoneyItem not between", value1, value2, "othermoneyitem");
            return (Criteria) this;
        }

        public Criteria andAccountdayIsNull() {
            addCriterion("AccountDay is null");
            return (Criteria) this;
        }

        public Criteria andAccountdayIsNotNull() {
            addCriterion("AccountDay is not null");
            return (Criteria) this;
        }

        public Criteria andAccountdayEqualTo(Integer value) {
            addCriterion("AccountDay =", value, "accountday");
            return (Criteria) this;
        }

        public Criteria andAccountdayNotEqualTo(Integer value) {
            addCriterion("AccountDay <>", value, "accountday");
            return (Criteria) this;
        }

        public Criteria andAccountdayGreaterThan(Integer value) {
            addCriterion("AccountDay >", value, "accountday");
            return (Criteria) this;
        }

        public Criteria andAccountdayGreaterThanOrEqualTo(Integer value) {
            addCriterion("AccountDay >=", value, "accountday");
            return (Criteria) this;
        }

        public Criteria andAccountdayLessThan(Integer value) {
            addCriterion("AccountDay <", value, "accountday");
            return (Criteria) this;
        }

        public Criteria andAccountdayLessThanOrEqualTo(Integer value) {
            addCriterion("AccountDay <=", value, "accountday");
            return (Criteria) this;
        }

        public Criteria andAccountdayIn(List<Integer> values) {
            addCriterion("AccountDay in", values, "accountday");
            return (Criteria) this;
        }

        public Criteria andAccountdayNotIn(List<Integer> values) {
            addCriterion("AccountDay not in", values, "accountday");
            return (Criteria) this;
        }

        public Criteria andAccountdayBetween(Integer value1, Integer value2) {
            addCriterion("AccountDay between", value1, value2, "accountday");
            return (Criteria) this;
        }

        public Criteria andAccountdayNotBetween(Integer value1, Integer value2) {
            addCriterion("AccountDay not between", value1, value2, "accountday");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("Status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("Status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("Status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("Status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("Status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("Status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("Status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("Status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("Status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("Status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("Status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("Status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("Status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("Status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andLinknumberIsNull() {
            addCriterion("LinkNumber is null");
            return (Criteria) this;
        }

        public Criteria andLinknumberIsNotNull() {
            addCriterion("LinkNumber is not null");
            return (Criteria) this;
        }

        public Criteria andLinknumberEqualTo(String value) {
            addCriterion("LinkNumber =", value, "linknumber");
            return (Criteria) this;
        }

        public Criteria andLinknumberNotEqualTo(String value) {
            addCriterion("LinkNumber <>", value, "linknumber");
            return (Criteria) this;
        }

        public Criteria andLinknumberGreaterThan(String value) {
            addCriterion("LinkNumber >", value, "linknumber");
            return (Criteria) this;
        }

        public Criteria andLinknumberGreaterThanOrEqualTo(String value) {
            addCriterion("LinkNumber >=", value, "linknumber");
            return (Criteria) this;
        }

        public Criteria andLinknumberLessThan(String value) {
            addCriterion("LinkNumber <", value, "linknumber");
            return (Criteria) this;
        }

        public Criteria andLinknumberLessThanOrEqualTo(String value) {
            addCriterion("LinkNumber <=", value, "linknumber");
            return (Criteria) this;
        }

        public Criteria andLinknumberLike(String value) {
            addCriterion("LinkNumber like", value, "linknumber");
            return (Criteria) this;
        }

        public Criteria andLinknumberNotLike(String value) {
            addCriterion("LinkNumber not like", value, "linknumber");
            return (Criteria) this;
        }

        public Criteria andLinknumberIn(List<String> values) {
            addCriterion("LinkNumber in", values, "linknumber");
            return (Criteria) this;
        }

        public Criteria andLinknumberNotIn(List<String> values) {
            addCriterion("LinkNumber not in", values, "linknumber");
            return (Criteria) this;
        }

        public Criteria andLinknumberBetween(String value1, String value2) {
            addCriterion("LinkNumber between", value1, value2, "linknumber");
            return (Criteria) this;
        }

        public Criteria andLinknumberNotBetween(String value1, String value2) {
            addCriterion("LinkNumber not between", value1, value2, "linknumber");
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