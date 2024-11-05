package com.jsh.erp.datasource.entities;

import java.util.ArrayList;
import java.util.List;

public class SystemConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SystemConfigExample() {
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

        public Criteria andCompanyNameIsNull() {
            addCriterion("company_name is null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNotNull() {
            addCriterion("company_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameEqualTo(String value) {
            addCriterion("company_name =", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotEqualTo(String value) {
            addCriterion("company_name <>", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThan(String value) {
            addCriterion("company_name >", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("company_name >=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThan(String value) {
            addCriterion("company_name <", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("company_name <=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLike(String value) {
            addCriterion("company_name like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotLike(String value) {
            addCriterion("company_name not like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIn(List<String> values) {
            addCriterion("company_name in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotIn(List<String> values) {
            addCriterion("company_name not in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameBetween(String value1, String value2) {
            addCriterion("company_name between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotBetween(String value1, String value2) {
            addCriterion("company_name not between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsIsNull() {
            addCriterion("company_contacts is null");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsIsNotNull() {
            addCriterion("company_contacts is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsEqualTo(String value) {
            addCriterion("company_contacts =", value, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsNotEqualTo(String value) {
            addCriterion("company_contacts <>", value, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsGreaterThan(String value) {
            addCriterion("company_contacts >", value, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsGreaterThanOrEqualTo(String value) {
            addCriterion("company_contacts >=", value, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsLessThan(String value) {
            addCriterion("company_contacts <", value, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsLessThanOrEqualTo(String value) {
            addCriterion("company_contacts <=", value, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsLike(String value) {
            addCriterion("company_contacts like", value, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsNotLike(String value) {
            addCriterion("company_contacts not like", value, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsIn(List<String> values) {
            addCriterion("company_contacts in", values, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsNotIn(List<String> values) {
            addCriterion("company_contacts not in", values, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsBetween(String value1, String value2) {
            addCriterion("company_contacts between", value1, value2, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyContactsNotBetween(String value1, String value2) {
            addCriterion("company_contacts not between", value1, value2, "companyContacts");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressIsNull() {
            addCriterion("company_address is null");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressIsNotNull() {
            addCriterion("company_address is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressEqualTo(String value) {
            addCriterion("company_address =", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressNotEqualTo(String value) {
            addCriterion("company_address <>", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressGreaterThan(String value) {
            addCriterion("company_address >", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressGreaterThanOrEqualTo(String value) {
            addCriterion("company_address >=", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressLessThan(String value) {
            addCriterion("company_address <", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressLessThanOrEqualTo(String value) {
            addCriterion("company_address <=", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressLike(String value) {
            addCriterion("company_address like", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressNotLike(String value) {
            addCriterion("company_address not like", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressIn(List<String> values) {
            addCriterion("company_address in", values, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressNotIn(List<String> values) {
            addCriterion("company_address not in", values, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressBetween(String value1, String value2) {
            addCriterion("company_address between", value1, value2, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressNotBetween(String value1, String value2) {
            addCriterion("company_address not between", value1, value2, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyTelIsNull() {
            addCriterion("company_tel is null");
            return (Criteria) this;
        }

        public Criteria andCompanyTelIsNotNull() {
            addCriterion("company_tel is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyTelEqualTo(String value) {
            addCriterion("company_tel =", value, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyTelNotEqualTo(String value) {
            addCriterion("company_tel <>", value, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyTelGreaterThan(String value) {
            addCriterion("company_tel >", value, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyTelGreaterThanOrEqualTo(String value) {
            addCriterion("company_tel >=", value, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyTelLessThan(String value) {
            addCriterion("company_tel <", value, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyTelLessThanOrEqualTo(String value) {
            addCriterion("company_tel <=", value, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyTelLike(String value) {
            addCriterion("company_tel like", value, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyTelNotLike(String value) {
            addCriterion("company_tel not like", value, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyTelIn(List<String> values) {
            addCriterion("company_tel in", values, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyTelNotIn(List<String> values) {
            addCriterion("company_tel not in", values, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyTelBetween(String value1, String value2) {
            addCriterion("company_tel between", value1, value2, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyTelNotBetween(String value1, String value2) {
            addCriterion("company_tel not between", value1, value2, "companyTel");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxIsNull() {
            addCriterion("company_fax is null");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxIsNotNull() {
            addCriterion("company_fax is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxEqualTo(String value) {
            addCriterion("company_fax =", value, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxNotEqualTo(String value) {
            addCriterion("company_fax <>", value, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxGreaterThan(String value) {
            addCriterion("company_fax >", value, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxGreaterThanOrEqualTo(String value) {
            addCriterion("company_fax >=", value, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxLessThan(String value) {
            addCriterion("company_fax <", value, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxLessThanOrEqualTo(String value) {
            addCriterion("company_fax <=", value, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxLike(String value) {
            addCriterion("company_fax like", value, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxNotLike(String value) {
            addCriterion("company_fax not like", value, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxIn(List<String> values) {
            addCriterion("company_fax in", values, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxNotIn(List<String> values) {
            addCriterion("company_fax not in", values, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxBetween(String value1, String value2) {
            addCriterion("company_fax between", value1, value2, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyFaxNotBetween(String value1, String value2) {
            addCriterion("company_fax not between", value1, value2, "companyFax");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeIsNull() {
            addCriterion("company_post_code is null");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeIsNotNull() {
            addCriterion("company_post_code is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeEqualTo(String value) {
            addCriterion("company_post_code =", value, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeNotEqualTo(String value) {
            addCriterion("company_post_code <>", value, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeGreaterThan(String value) {
            addCriterion("company_post_code >", value, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeGreaterThanOrEqualTo(String value) {
            addCriterion("company_post_code >=", value, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeLessThan(String value) {
            addCriterion("company_post_code <", value, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeLessThanOrEqualTo(String value) {
            addCriterion("company_post_code <=", value, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeLike(String value) {
            addCriterion("company_post_code like", value, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeNotLike(String value) {
            addCriterion("company_post_code not like", value, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeIn(List<String> values) {
            addCriterion("company_post_code in", values, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeNotIn(List<String> values) {
            addCriterion("company_post_code not in", values, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeBetween(String value1, String value2) {
            addCriterion("company_post_code between", value1, value2, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPostCodeNotBetween(String value1, String value2) {
            addCriterion("company_post_code not between", value1, value2, "companyPostCode");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementIsNull() {
            addCriterion("sale_agreement is null");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementIsNotNull() {
            addCriterion("sale_agreement is not null");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementEqualTo(String value) {
            addCriterion("sale_agreement =", value, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementNotEqualTo(String value) {
            addCriterion("sale_agreement <>", value, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementGreaterThan(String value) {
            addCriterion("sale_agreement >", value, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementGreaterThanOrEqualTo(String value) {
            addCriterion("sale_agreement >=", value, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementLessThan(String value) {
            addCriterion("sale_agreement <", value, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementLessThanOrEqualTo(String value) {
            addCriterion("sale_agreement <=", value, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementLike(String value) {
            addCriterion("sale_agreement like", value, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementNotLike(String value) {
            addCriterion("sale_agreement not like", value, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementIn(List<String> values) {
            addCriterion("sale_agreement in", values, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementNotIn(List<String> values) {
            addCriterion("sale_agreement not in", values, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementBetween(String value1, String value2) {
            addCriterion("sale_agreement between", value1, value2, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andSaleAgreementNotBetween(String value1, String value2) {
            addCriterion("sale_agreement not between", value1, value2, "saleAgreement");
            return (Criteria) this;
        }

        public Criteria andDepotFlagIsNull() {
            addCriterion("depot_flag is null");
            return (Criteria) this;
        }

        public Criteria andDepotFlagIsNotNull() {
            addCriterion("depot_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDepotFlagEqualTo(String value) {
            addCriterion("depot_flag =", value, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andDepotFlagNotEqualTo(String value) {
            addCriterion("depot_flag <>", value, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andDepotFlagGreaterThan(String value) {
            addCriterion("depot_flag >", value, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andDepotFlagGreaterThanOrEqualTo(String value) {
            addCriterion("depot_flag >=", value, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andDepotFlagLessThan(String value) {
            addCriterion("depot_flag <", value, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andDepotFlagLessThanOrEqualTo(String value) {
            addCriterion("depot_flag <=", value, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andDepotFlagLike(String value) {
            addCriterion("depot_flag like", value, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andDepotFlagNotLike(String value) {
            addCriterion("depot_flag not like", value, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andDepotFlagIn(List<String> values) {
            addCriterion("depot_flag in", values, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andDepotFlagNotIn(List<String> values) {
            addCriterion("depot_flag not in", values, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andDepotFlagBetween(String value1, String value2) {
            addCriterion("depot_flag between", value1, value2, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andDepotFlagNotBetween(String value1, String value2) {
            addCriterion("depot_flag not between", value1, value2, "depotFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagIsNull() {
            addCriterion("customer_flag is null");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagIsNotNull() {
            addCriterion("customer_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagEqualTo(String value) {
            addCriterion("customer_flag =", value, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagNotEqualTo(String value) {
            addCriterion("customer_flag <>", value, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagGreaterThan(String value) {
            addCriterion("customer_flag >", value, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagGreaterThanOrEqualTo(String value) {
            addCriterion("customer_flag >=", value, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagLessThan(String value) {
            addCriterion("customer_flag <", value, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagLessThanOrEqualTo(String value) {
            addCriterion("customer_flag <=", value, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagLike(String value) {
            addCriterion("customer_flag like", value, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagNotLike(String value) {
            addCriterion("customer_flag not like", value, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagIn(List<String> values) {
            addCriterion("customer_flag in", values, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagNotIn(List<String> values) {
            addCriterion("customer_flag not in", values, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagBetween(String value1, String value2) {
            addCriterion("customer_flag between", value1, value2, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andCustomerFlagNotBetween(String value1, String value2) {
            addCriterion("customer_flag not between", value1, value2, "customerFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagIsNull() {
            addCriterion("minus_stock_flag is null");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagIsNotNull() {
            addCriterion("minus_stock_flag is not null");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagEqualTo(String value) {
            addCriterion("minus_stock_flag =", value, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagNotEqualTo(String value) {
            addCriterion("minus_stock_flag <>", value, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagGreaterThan(String value) {
            addCriterion("minus_stock_flag >", value, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagGreaterThanOrEqualTo(String value) {
            addCriterion("minus_stock_flag >=", value, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagLessThan(String value) {
            addCriterion("minus_stock_flag <", value, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagLessThanOrEqualTo(String value) {
            addCriterion("minus_stock_flag <=", value, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagLike(String value) {
            addCriterion("minus_stock_flag like", value, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagNotLike(String value) {
            addCriterion("minus_stock_flag not like", value, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagIn(List<String> values) {
            addCriterion("minus_stock_flag in", values, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagNotIn(List<String> values) {
            addCriterion("minus_stock_flag not in", values, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagBetween(String value1, String value2) {
            addCriterion("minus_stock_flag between", value1, value2, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andMinusStockFlagNotBetween(String value1, String value2) {
            addCriterion("minus_stock_flag not between", value1, value2, "minusStockFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagIsNull() {
            addCriterion("purchase_by_sale_flag is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagIsNotNull() {
            addCriterion("purchase_by_sale_flag is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagEqualTo(String value) {
            addCriterion("purchase_by_sale_flag =", value, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagNotEqualTo(String value) {
            addCriterion("purchase_by_sale_flag <>", value, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagGreaterThan(String value) {
            addCriterion("purchase_by_sale_flag >", value, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_by_sale_flag >=", value, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagLessThan(String value) {
            addCriterion("purchase_by_sale_flag <", value, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagLessThanOrEqualTo(String value) {
            addCriterion("purchase_by_sale_flag <=", value, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagLike(String value) {
            addCriterion("purchase_by_sale_flag like", value, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagNotLike(String value) {
            addCriterion("purchase_by_sale_flag not like", value, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagIn(List<String> values) {
            addCriterion("purchase_by_sale_flag in", values, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagNotIn(List<String> values) {
            addCriterion("purchase_by_sale_flag not in", values, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagBetween(String value1, String value2) {
            addCriterion("purchase_by_sale_flag between", value1, value2, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andPurchaseBySaleFlagNotBetween(String value1, String value2) {
            addCriterion("purchase_by_sale_flag not between", value1, value2, "purchaseBySaleFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagIsNull() {
            addCriterion("multi_level_approval_flag is null");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagIsNotNull() {
            addCriterion("multi_level_approval_flag is not null");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagEqualTo(String value) {
            addCriterion("multi_level_approval_flag =", value, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagNotEqualTo(String value) {
            addCriterion("multi_level_approval_flag <>", value, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagGreaterThan(String value) {
            addCriterion("multi_level_approval_flag >", value, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagGreaterThanOrEqualTo(String value) {
            addCriterion("multi_level_approval_flag >=", value, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagLessThan(String value) {
            addCriterion("multi_level_approval_flag <", value, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagLessThanOrEqualTo(String value) {
            addCriterion("multi_level_approval_flag <=", value, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagLike(String value) {
            addCriterion("multi_level_approval_flag like", value, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagNotLike(String value) {
            addCriterion("multi_level_approval_flag not like", value, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagIn(List<String> values) {
            addCriterion("multi_level_approval_flag in", values, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagNotIn(List<String> values) {
            addCriterion("multi_level_approval_flag not in", values, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagBetween(String value1, String value2) {
            addCriterion("multi_level_approval_flag between", value1, value2, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiLevelApprovalFlagNotBetween(String value1, String value2) {
            addCriterion("multi_level_approval_flag not between", value1, value2, "multiLevelApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeIsNull() {
            addCriterion("multi_bill_type is null");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeIsNotNull() {
            addCriterion("multi_bill_type is not null");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeEqualTo(String value) {
            addCriterion("multi_bill_type =", value, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeNotEqualTo(String value) {
            addCriterion("multi_bill_type <>", value, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeGreaterThan(String value) {
            addCriterion("multi_bill_type >", value, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeGreaterThanOrEqualTo(String value) {
            addCriterion("multi_bill_type >=", value, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeLessThan(String value) {
            addCriterion("multi_bill_type <", value, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeLessThanOrEqualTo(String value) {
            addCriterion("multi_bill_type <=", value, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeLike(String value) {
            addCriterion("multi_bill_type like", value, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeNotLike(String value) {
            addCriterion("multi_bill_type not like", value, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeIn(List<String> values) {
            addCriterion("multi_bill_type in", values, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeNotIn(List<String> values) {
            addCriterion("multi_bill_type not in", values, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeBetween(String value1, String value2) {
            addCriterion("multi_bill_type between", value1, value2, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andMultiBillTypeNotBetween(String value1, String value2) {
            addCriterion("multi_bill_type not between", value1, value2, "multiBillType");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagIsNull() {
            addCriterion("force_approval_flag is null");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagIsNotNull() {
            addCriterion("force_approval_flag is not null");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagEqualTo(String value) {
            addCriterion("force_approval_flag =", value, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagNotEqualTo(String value) {
            addCriterion("force_approval_flag <>", value, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagGreaterThan(String value) {
            addCriterion("force_approval_flag >", value, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagGreaterThanOrEqualTo(String value) {
            addCriterion("force_approval_flag >=", value, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagLessThan(String value) {
            addCriterion("force_approval_flag <", value, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagLessThanOrEqualTo(String value) {
            addCriterion("force_approval_flag <=", value, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagLike(String value) {
            addCriterion("force_approval_flag like", value, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagNotLike(String value) {
            addCriterion("force_approval_flag not like", value, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagIn(List<String> values) {
            addCriterion("force_approval_flag in", values, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagNotIn(List<String> values) {
            addCriterion("force_approval_flag not in", values, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagBetween(String value1, String value2) {
            addCriterion("force_approval_flag between", value1, value2, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andForceApprovalFlagNotBetween(String value1, String value2) {
            addCriterion("force_approval_flag not between", value1, value2, "forceApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagIsNull() {
            addCriterion("update_unit_price_flag is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagIsNotNull() {
            addCriterion("update_unit_price_flag is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagEqualTo(String value) {
            addCriterion("update_unit_price_flag =", value, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagNotEqualTo(String value) {
            addCriterion("update_unit_price_flag <>", value, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagGreaterThan(String value) {
            addCriterion("update_unit_price_flag >", value, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagGreaterThanOrEqualTo(String value) {
            addCriterion("update_unit_price_flag >=", value, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagLessThan(String value) {
            addCriterion("update_unit_price_flag <", value, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagLessThanOrEqualTo(String value) {
            addCriterion("update_unit_price_flag <=", value, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagLike(String value) {
            addCriterion("update_unit_price_flag like", value, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagNotLike(String value) {
            addCriterion("update_unit_price_flag not like", value, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagIn(List<String> values) {
            addCriterion("update_unit_price_flag in", values, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagNotIn(List<String> values) {
            addCriterion("update_unit_price_flag not in", values, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagBetween(String value1, String value2) {
            addCriterion("update_unit_price_flag between", value1, value2, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andUpdateUnitPriceFlagNotBetween(String value1, String value2) {
            addCriterion("update_unit_price_flag not between", value1, value2, "updateUnitPriceFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagIsNull() {
            addCriterion("over_link_bill_flag is null");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagIsNotNull() {
            addCriterion("over_link_bill_flag is not null");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagEqualTo(String value) {
            addCriterion("over_link_bill_flag =", value, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagNotEqualTo(String value) {
            addCriterion("over_link_bill_flag <>", value, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagGreaterThan(String value) {
            addCriterion("over_link_bill_flag >", value, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagGreaterThanOrEqualTo(String value) {
            addCriterion("over_link_bill_flag >=", value, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagLessThan(String value) {
            addCriterion("over_link_bill_flag <", value, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagLessThanOrEqualTo(String value) {
            addCriterion("over_link_bill_flag <=", value, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagLike(String value) {
            addCriterion("over_link_bill_flag like", value, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagNotLike(String value) {
            addCriterion("over_link_bill_flag not like", value, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagIn(List<String> values) {
            addCriterion("over_link_bill_flag in", values, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagNotIn(List<String> values) {
            addCriterion("over_link_bill_flag not in", values, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagBetween(String value1, String value2) {
            addCriterion("over_link_bill_flag between", value1, value2, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andOverLinkBillFlagNotBetween(String value1, String value2) {
            addCriterion("over_link_bill_flag not between", value1, value2, "overLinkBillFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagIsNull() {
            addCriterion("in_out_manage_flag is null");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagIsNotNull() {
            addCriterion("in_out_manage_flag is not null");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagEqualTo(String value) {
            addCriterion("in_out_manage_flag =", value, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagNotEqualTo(String value) {
            addCriterion("in_out_manage_flag <>", value, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagGreaterThan(String value) {
            addCriterion("in_out_manage_flag >", value, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagGreaterThanOrEqualTo(String value) {
            addCriterion("in_out_manage_flag >=", value, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagLessThan(String value) {
            addCriterion("in_out_manage_flag <", value, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagLessThanOrEqualTo(String value) {
            addCriterion("in_out_manage_flag <=", value, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagLike(String value) {
            addCriterion("in_out_manage_flag like", value, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagNotLike(String value) {
            addCriterion("in_out_manage_flag not like", value, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagIn(List<String> values) {
            addCriterion("in_out_manage_flag in", values, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagNotIn(List<String> values) {
            addCriterion("in_out_manage_flag not in", values, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagBetween(String value1, String value2) {
            addCriterion("in_out_manage_flag between", value1, value2, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andInOutManageFlagNotBetween(String value1, String value2) {
            addCriterion("in_out_manage_flag not between", value1, value2, "inOutManageFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagIsNull() {
            addCriterion("multi_account_flag is null");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagIsNotNull() {
            addCriterion("multi_account_flag is not null");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagEqualTo(String value) {
            addCriterion("multi_account_flag =", value, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagNotEqualTo(String value) {
            addCriterion("multi_account_flag <>", value, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagGreaterThan(String value) {
            addCriterion("multi_account_flag >", value, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagGreaterThanOrEqualTo(String value) {
            addCriterion("multi_account_flag >=", value, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagLessThan(String value) {
            addCriterion("multi_account_flag <", value, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagLessThanOrEqualTo(String value) {
            addCriterion("multi_account_flag <=", value, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagLike(String value) {
            addCriterion("multi_account_flag like", value, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagNotLike(String value) {
            addCriterion("multi_account_flag not like", value, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagIn(List<String> values) {
            addCriterion("multi_account_flag in", values, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagNotIn(List<String> values) {
            addCriterion("multi_account_flag not in", values, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagBetween(String value1, String value2) {
            addCriterion("multi_account_flag between", value1, value2, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMultiAccountFlagNotBetween(String value1, String value2) {
            addCriterion("multi_account_flag not between", value1, value2, "multiAccountFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagIsNull() {
            addCriterion("move_avg_price_flag is null");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagIsNotNull() {
            addCriterion("move_avg_price_flag is not null");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagEqualTo(String value) {
            addCriterion("move_avg_price_flag =", value, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagNotEqualTo(String value) {
            addCriterion("move_avg_price_flag <>", value, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagGreaterThan(String value) {
            addCriterion("move_avg_price_flag >", value, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagGreaterThanOrEqualTo(String value) {
            addCriterion("move_avg_price_flag >=", value, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagLessThan(String value) {
            addCriterion("move_avg_price_flag <", value, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagLessThanOrEqualTo(String value) {
            addCriterion("move_avg_price_flag <=", value, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagLike(String value) {
            addCriterion("move_avg_price_flag like", value, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagNotLike(String value) {
            addCriterion("move_avg_price_flag not like", value, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagIn(List<String> values) {
            addCriterion("move_avg_price_flag in", values, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagNotIn(List<String> values) {
            addCriterion("move_avg_price_flag not in", values, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagBetween(String value1, String value2) {
            addCriterion("move_avg_price_flag between", value1, value2, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andMoveAvgPriceFlagNotBetween(String value1, String value2) {
            addCriterion("move_avg_price_flag not between", value1, value2, "moveAvgPriceFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagIsNull() {
            addCriterion("audit_print_flag is null");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagIsNotNull() {
            addCriterion("audit_print_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagEqualTo(String value) {
            addCriterion("audit_print_flag =", value, "auditPrintFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagNotEqualTo(String value) {
            addCriterion("audit_print_flag <>", value, "auditPrintFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagGreaterThan(String value) {
            addCriterion("audit_print_flag >", value, "auditPrintFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagGreaterThanOrEqualTo(String value) {
            addCriterion("audit_print_flag >=", value, "auditPrintFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagLessThan(String value) {
            addCriterion("audit_print_flag <", value, "auditPrintFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagLessThanOrEqualTo(String value) {
            addCriterion("audit_print_flag <=", value, "auditPrintFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagLike(String value) {
            addCriterion("audit_print_flag like", value, "auditPrintFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagNotLike(String value) {
            addCriterion("audit_print_flag not like", value, "auditPrintFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagIn(List<String> values) {
            addCriterion("audit_print_flag in", values, "auditPrintFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagNotIn(List<String> values) {
            addCriterion("audit_print_flag not in", values, "auditPrintFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagBetween(String value1, String value2) {
            addCriterion("audit_print_flag between", value1, value2, "auditPrintFlag");
            return (Criteria) this;
        }

        public Criteria andAuditPrintFlagNotBetween(String value1, String value2) {
            addCriterion("audit_print_flag not between", value1, value2, "auditPrintFlag");
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