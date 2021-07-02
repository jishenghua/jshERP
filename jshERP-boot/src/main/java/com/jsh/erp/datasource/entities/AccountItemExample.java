package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AccountItemExample() {
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

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(Long value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(Long value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(Long value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(Long value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(Long value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(Long value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<Long> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<Long> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(Long value1, Long value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(Long value1, Long value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdIsNull() {
            addCriterion("in_out_item_id is null");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdIsNotNull() {
            addCriterion("in_out_item_id is not null");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdEqualTo(Long value) {
            addCriterion("in_out_item_id =", value, "inOutItemId");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdNotEqualTo(Long value) {
            addCriterion("in_out_item_id <>", value, "inOutItemId");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdGreaterThan(Long value) {
            addCriterion("in_out_item_id >", value, "inOutItemId");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdGreaterThanOrEqualTo(Long value) {
            addCriterion("in_out_item_id >=", value, "inOutItemId");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdLessThan(Long value) {
            addCriterion("in_out_item_id <", value, "inOutItemId");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdLessThanOrEqualTo(Long value) {
            addCriterion("in_out_item_id <=", value, "inOutItemId");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdIn(List<Long> values) {
            addCriterion("in_out_item_id in", values, "inOutItemId");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdNotIn(List<Long> values) {
            addCriterion("in_out_item_id not in", values, "inOutItemId");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdBetween(Long value1, Long value2) {
            addCriterion("in_out_item_id between", value1, value2, "inOutItemId");
            return (Criteria) this;
        }

        public Criteria andInOutItemIdNotBetween(Long value1, Long value2) {
            addCriterion("in_out_item_id not between", value1, value2, "inOutItemId");
            return (Criteria) this;
        }

        public Criteria andBillIdIsNull() {
            addCriterion("bill_id is null");
            return (Criteria) this;
        }

        public Criteria andBillIdIsNotNull() {
            addCriterion("bill_id is not null");
            return (Criteria) this;
        }

        public Criteria andBillIdEqualTo(Long value) {
            addCriterion("bill_id =", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdNotEqualTo(Long value) {
            addCriterion("bill_id <>", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdGreaterThan(Long value) {
            addCriterion("bill_id >", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdGreaterThanOrEqualTo(Long value) {
            addCriterion("bill_id >=", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdLessThan(Long value) {
            addCriterion("bill_id <", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdLessThanOrEqualTo(Long value) {
            addCriterion("bill_id <=", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdIn(List<Long> values) {
            addCriterion("bill_id in", values, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdNotIn(List<Long> values) {
            addCriterion("bill_id not in", values, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdBetween(Long value1, Long value2) {
            addCriterion("bill_id between", value1, value2, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdNotBetween(Long value1, Long value2) {
            addCriterion("bill_id not between", value1, value2, "billId");
            return (Criteria) this;
        }

        public Criteria andNeedDebtIsNull() {
            addCriterion("need_debt is null");
            return (Criteria) this;
        }

        public Criteria andNeedDebtIsNotNull() {
            addCriterion("need_debt is not null");
            return (Criteria) this;
        }

        public Criteria andNeedDebtEqualTo(BigDecimal value) {
            addCriterion("need_debt =", value, "needDebt");
            return (Criteria) this;
        }

        public Criteria andNeedDebtNotEqualTo(BigDecimal value) {
            addCriterion("need_debt <>", value, "needDebt");
            return (Criteria) this;
        }

        public Criteria andNeedDebtGreaterThan(BigDecimal value) {
            addCriterion("need_debt >", value, "needDebt");
            return (Criteria) this;
        }

        public Criteria andNeedDebtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("need_debt >=", value, "needDebt");
            return (Criteria) this;
        }

        public Criteria andNeedDebtLessThan(BigDecimal value) {
            addCriterion("need_debt <", value, "needDebt");
            return (Criteria) this;
        }

        public Criteria andNeedDebtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("need_debt <=", value, "needDebt");
            return (Criteria) this;
        }

        public Criteria andNeedDebtIn(List<BigDecimal> values) {
            addCriterion("need_debt in", values, "needDebt");
            return (Criteria) this;
        }

        public Criteria andNeedDebtNotIn(List<BigDecimal> values) {
            addCriterion("need_debt not in", values, "needDebt");
            return (Criteria) this;
        }

        public Criteria andNeedDebtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("need_debt between", value1, value2, "needDebt");
            return (Criteria) this;
        }

        public Criteria andNeedDebtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("need_debt not between", value1, value2, "needDebt");
            return (Criteria) this;
        }

        public Criteria andFinishDebtIsNull() {
            addCriterion("finish_debt is null");
            return (Criteria) this;
        }

        public Criteria andFinishDebtIsNotNull() {
            addCriterion("finish_debt is not null");
            return (Criteria) this;
        }

        public Criteria andFinishDebtEqualTo(BigDecimal value) {
            addCriterion("finish_debt =", value, "finishDebt");
            return (Criteria) this;
        }

        public Criteria andFinishDebtNotEqualTo(BigDecimal value) {
            addCriterion("finish_debt <>", value, "finishDebt");
            return (Criteria) this;
        }

        public Criteria andFinishDebtGreaterThan(BigDecimal value) {
            addCriterion("finish_debt >", value, "finishDebt");
            return (Criteria) this;
        }

        public Criteria andFinishDebtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("finish_debt >=", value, "finishDebt");
            return (Criteria) this;
        }

        public Criteria andFinishDebtLessThan(BigDecimal value) {
            addCriterion("finish_debt <", value, "finishDebt");
            return (Criteria) this;
        }

        public Criteria andFinishDebtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("finish_debt <=", value, "finishDebt");
            return (Criteria) this;
        }

        public Criteria andFinishDebtIn(List<BigDecimal> values) {
            addCriterion("finish_debt in", values, "finishDebt");
            return (Criteria) this;
        }

        public Criteria andFinishDebtNotIn(List<BigDecimal> values) {
            addCriterion("finish_debt not in", values, "finishDebt");
            return (Criteria) this;
        }

        public Criteria andFinishDebtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finish_debt between", value1, value2, "finishDebt");
            return (Criteria) this;
        }

        public Criteria andFinishDebtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finish_debt not between", value1, value2, "finishDebt");
            return (Criteria) this;
        }

        public Criteria andEachAmountIsNull() {
            addCriterion("each_amount is null");
            return (Criteria) this;
        }

        public Criteria andEachAmountIsNotNull() {
            addCriterion("each_amount is not null");
            return (Criteria) this;
        }

        public Criteria andEachAmountEqualTo(BigDecimal value) {
            addCriterion("each_amount =", value, "eachAmount");
            return (Criteria) this;
        }

        public Criteria andEachAmountNotEqualTo(BigDecimal value) {
            addCriterion("each_amount <>", value, "eachAmount");
            return (Criteria) this;
        }

        public Criteria andEachAmountGreaterThan(BigDecimal value) {
            addCriterion("each_amount >", value, "eachAmount");
            return (Criteria) this;
        }

        public Criteria andEachAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("each_amount >=", value, "eachAmount");
            return (Criteria) this;
        }

        public Criteria andEachAmountLessThan(BigDecimal value) {
            addCriterion("each_amount <", value, "eachAmount");
            return (Criteria) this;
        }

        public Criteria andEachAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("each_amount <=", value, "eachAmount");
            return (Criteria) this;
        }

        public Criteria andEachAmountIn(List<BigDecimal> values) {
            addCriterion("each_amount in", values, "eachAmount");
            return (Criteria) this;
        }

        public Criteria andEachAmountNotIn(List<BigDecimal> values) {
            addCriterion("each_amount not in", values, "eachAmount");
            return (Criteria) this;
        }

        public Criteria andEachAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("each_amount between", value1, value2, "eachAmount");
            return (Criteria) this;
        }

        public Criteria andEachAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("each_amount not between", value1, value2, "eachAmount");
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