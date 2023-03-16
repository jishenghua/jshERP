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

        public Criteria andAmountApprovalFlagIsNull() {
            addCriterion("amount_approval_flag is null");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagIsNotNull() {
            addCriterion("amount_approval_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagEqualTo(String value) {
            addCriterion("amount_approval_flag =", value, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagNotEqualTo(String value) {
            addCriterion("amount_approval_flag <>", value, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagGreaterThan(String value) {
            addCriterion("amount_approval_flag >", value, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagGreaterThanOrEqualTo(String value) {
            addCriterion("amount_approval_flag >=", value, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagLessThan(String value) {
            addCriterion("amount_approval_flag <", value, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagLessThanOrEqualTo(String value) {
            addCriterion("amount_approval_flag <=", value, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagLike(String value) {
            addCriterion("amount_approval_flag like", value, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagNotLike(String value) {
            addCriterion("amount_approval_flag not like", value, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagIn(List<String> values) {
            addCriterion("amount_approval_flag in", values, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagNotIn(List<String> values) {
            addCriterion("amount_approval_flag not in", values, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagBetween(String value1, String value2) {
            addCriterion("amount_approval_flag between", value1, value2, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andAmountApprovalFlagNotBetween(String value1, String value2) {
            addCriterion("amount_approval_flag not between", value1, value2, "amountApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagIsNull() {
            addCriterion("stock_approval_flag is null");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagIsNotNull() {
            addCriterion("stock_approval_flag is not null");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagEqualTo(String value) {
            addCriterion("stock_approval_flag =", value, "stockApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagNotEqualTo(String value) {
            addCriterion("stock_approval_flag <>", value, "stockApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagGreaterThan(String value) {
            addCriterion("stock_approval_flag >", value, "stockApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagGreaterThanOrEqualTo(String value) {
            addCriterion("stock_approval_flag >=", value, "stockApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagLessThan(String value) {
            addCriterion("stock_approval_flag <", value, "stockApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagLessThanOrEqualTo(String value) {
            addCriterion("stock_approval_flag <=", value, "stockApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagLike(String value) {
            addCriterion("stock_approval_flag like", value, "stockApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagNotLike(String value) {
            addCriterion("stock_approval_flag not like", value, "stockApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagIn(List<String> values) {
            addCriterion("stock_approval_flag in", values, "stockApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagNotIn(List<String> values) {
            addCriterion("stock_approval_flag not in", values, "stockApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagBetween(String value1, String value2) {
            addCriterion("stock_approval_flag between", value1, value2, "stockApprovalFlag");
            return (Criteria) this;
        }

        public Criteria andStockApprovalFlagNotBetween(String value1, String value2) {
            addCriterion("stock_approval_flag not between", value1, value2, "stockApprovalFlag");
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