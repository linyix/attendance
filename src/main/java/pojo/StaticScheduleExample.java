package pojo;

import java.util.ArrayList;
import java.util.List;

public class StaticScheduleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StaticScheduleExample() {
        oredCriteria = new ArrayList<Criteria>();
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
            criteria = new ArrayList<Criterion>();
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNull() {
            addCriterion("department_id is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNotNull() {
            addCriterion("department_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdEqualTo(Integer value) {
            addCriterion("department_id =", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotEqualTo(Integer value) {
            addCriterion("department_id <>", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThan(Integer value) {
            addCriterion("department_id >", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("department_id >=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThan(Integer value) {
            addCriterion("department_id <", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("department_id <=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIn(List<Integer> values) {
            addCriterion("department_id in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotIn(List<Integer> values) {
            addCriterion("department_id not in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdBetween(Integer value1, Integer value2) {
            addCriterion("department_id between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("department_id not between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDay1IsNull() {
            addCriterion("day1 is null");
            return (Criteria) this;
        }

        public Criteria andDay1IsNotNull() {
            addCriterion("day1 is not null");
            return (Criteria) this;
        }

        public Criteria andDay1EqualTo(Integer value) {
            addCriterion("day1 =", value, "day1");
            return (Criteria) this;
        }

        public Criteria andDay1NotEqualTo(Integer value) {
            addCriterion("day1 <>", value, "day1");
            return (Criteria) this;
        }

        public Criteria andDay1GreaterThan(Integer value) {
            addCriterion("day1 >", value, "day1");
            return (Criteria) this;
        }

        public Criteria andDay1GreaterThanOrEqualTo(Integer value) {
            addCriterion("day1 >=", value, "day1");
            return (Criteria) this;
        }

        public Criteria andDay1LessThan(Integer value) {
            addCriterion("day1 <", value, "day1");
            return (Criteria) this;
        }

        public Criteria andDay1LessThanOrEqualTo(Integer value) {
            addCriterion("day1 <=", value, "day1");
            return (Criteria) this;
        }

        public Criteria andDay1In(List<Integer> values) {
            addCriterion("day1 in", values, "day1");
            return (Criteria) this;
        }

        public Criteria andDay1NotIn(List<Integer> values) {
            addCriterion("day1 not in", values, "day1");
            return (Criteria) this;
        }

        public Criteria andDay1Between(Integer value1, Integer value2) {
            addCriterion("day1 between", value1, value2, "day1");
            return (Criteria) this;
        }

        public Criteria andDay1NotBetween(Integer value1, Integer value2) {
            addCriterion("day1 not between", value1, value2, "day1");
            return (Criteria) this;
        }

        public Criteria andDay2IsNull() {
            addCriterion("day2 is null");
            return (Criteria) this;
        }

        public Criteria andDay2IsNotNull() {
            addCriterion("day2 is not null");
            return (Criteria) this;
        }

        public Criteria andDay2EqualTo(Integer value) {
            addCriterion("day2 =", value, "day2");
            return (Criteria) this;
        }

        public Criteria andDay2NotEqualTo(Integer value) {
            addCriterion("day2 <>", value, "day2");
            return (Criteria) this;
        }

        public Criteria andDay2GreaterThan(Integer value) {
            addCriterion("day2 >", value, "day2");
            return (Criteria) this;
        }

        public Criteria andDay2GreaterThanOrEqualTo(Integer value) {
            addCriterion("day2 >=", value, "day2");
            return (Criteria) this;
        }

        public Criteria andDay2LessThan(Integer value) {
            addCriterion("day2 <", value, "day2");
            return (Criteria) this;
        }

        public Criteria andDay2LessThanOrEqualTo(Integer value) {
            addCriterion("day2 <=", value, "day2");
            return (Criteria) this;
        }

        public Criteria andDay2In(List<Integer> values) {
            addCriterion("day2 in", values, "day2");
            return (Criteria) this;
        }

        public Criteria andDay2NotIn(List<Integer> values) {
            addCriterion("day2 not in", values, "day2");
            return (Criteria) this;
        }

        public Criteria andDay2Between(Integer value1, Integer value2) {
            addCriterion("day2 between", value1, value2, "day2");
            return (Criteria) this;
        }

        public Criteria andDay2NotBetween(Integer value1, Integer value2) {
            addCriterion("day2 not between", value1, value2, "day2");
            return (Criteria) this;
        }

        public Criteria andDay3IsNull() {
            addCriterion("day3 is null");
            return (Criteria) this;
        }

        public Criteria andDay3IsNotNull() {
            addCriterion("day3 is not null");
            return (Criteria) this;
        }

        public Criteria andDay3EqualTo(Integer value) {
            addCriterion("day3 =", value, "day3");
            return (Criteria) this;
        }

        public Criteria andDay3NotEqualTo(Integer value) {
            addCriterion("day3 <>", value, "day3");
            return (Criteria) this;
        }

        public Criteria andDay3GreaterThan(Integer value) {
            addCriterion("day3 >", value, "day3");
            return (Criteria) this;
        }

        public Criteria andDay3GreaterThanOrEqualTo(Integer value) {
            addCriterion("day3 >=", value, "day3");
            return (Criteria) this;
        }

        public Criteria andDay3LessThan(Integer value) {
            addCriterion("day3 <", value, "day3");
            return (Criteria) this;
        }

        public Criteria andDay3LessThanOrEqualTo(Integer value) {
            addCriterion("day3 <=", value, "day3");
            return (Criteria) this;
        }

        public Criteria andDay3In(List<Integer> values) {
            addCriterion("day3 in", values, "day3");
            return (Criteria) this;
        }

        public Criteria andDay3NotIn(List<Integer> values) {
            addCriterion("day3 not in", values, "day3");
            return (Criteria) this;
        }

        public Criteria andDay3Between(Integer value1, Integer value2) {
            addCriterion("day3 between", value1, value2, "day3");
            return (Criteria) this;
        }

        public Criteria andDay3NotBetween(Integer value1, Integer value2) {
            addCriterion("day3 not between", value1, value2, "day3");
            return (Criteria) this;
        }

        public Criteria andDay4IsNull() {
            addCriterion("day4 is null");
            return (Criteria) this;
        }

        public Criteria andDay4IsNotNull() {
            addCriterion("day4 is not null");
            return (Criteria) this;
        }

        public Criteria andDay4EqualTo(Integer value) {
            addCriterion("day4 =", value, "day4");
            return (Criteria) this;
        }

        public Criteria andDay4NotEqualTo(Integer value) {
            addCriterion("day4 <>", value, "day4");
            return (Criteria) this;
        }

        public Criteria andDay4GreaterThan(Integer value) {
            addCriterion("day4 >", value, "day4");
            return (Criteria) this;
        }

        public Criteria andDay4GreaterThanOrEqualTo(Integer value) {
            addCriterion("day4 >=", value, "day4");
            return (Criteria) this;
        }

        public Criteria andDay4LessThan(Integer value) {
            addCriterion("day4 <", value, "day4");
            return (Criteria) this;
        }

        public Criteria andDay4LessThanOrEqualTo(Integer value) {
            addCriterion("day4 <=", value, "day4");
            return (Criteria) this;
        }

        public Criteria andDay4In(List<Integer> values) {
            addCriterion("day4 in", values, "day4");
            return (Criteria) this;
        }

        public Criteria andDay4NotIn(List<Integer> values) {
            addCriterion("day4 not in", values, "day4");
            return (Criteria) this;
        }

        public Criteria andDay4Between(Integer value1, Integer value2) {
            addCriterion("day4 between", value1, value2, "day4");
            return (Criteria) this;
        }

        public Criteria andDay4NotBetween(Integer value1, Integer value2) {
            addCriterion("day4 not between", value1, value2, "day4");
            return (Criteria) this;
        }

        public Criteria andDay5IsNull() {
            addCriterion("day5 is null");
            return (Criteria) this;
        }

        public Criteria andDay5IsNotNull() {
            addCriterion("day5 is not null");
            return (Criteria) this;
        }

        public Criteria andDay5EqualTo(Integer value) {
            addCriterion("day5 =", value, "day5");
            return (Criteria) this;
        }

        public Criteria andDay5NotEqualTo(Integer value) {
            addCriterion("day5 <>", value, "day5");
            return (Criteria) this;
        }

        public Criteria andDay5GreaterThan(Integer value) {
            addCriterion("day5 >", value, "day5");
            return (Criteria) this;
        }

        public Criteria andDay5GreaterThanOrEqualTo(Integer value) {
            addCriterion("day5 >=", value, "day5");
            return (Criteria) this;
        }

        public Criteria andDay5LessThan(Integer value) {
            addCriterion("day5 <", value, "day5");
            return (Criteria) this;
        }

        public Criteria andDay5LessThanOrEqualTo(Integer value) {
            addCriterion("day5 <=", value, "day5");
            return (Criteria) this;
        }

        public Criteria andDay5In(List<Integer> values) {
            addCriterion("day5 in", values, "day5");
            return (Criteria) this;
        }

        public Criteria andDay5NotIn(List<Integer> values) {
            addCriterion("day5 not in", values, "day5");
            return (Criteria) this;
        }

        public Criteria andDay5Between(Integer value1, Integer value2) {
            addCriterion("day5 between", value1, value2, "day5");
            return (Criteria) this;
        }

        public Criteria andDay5NotBetween(Integer value1, Integer value2) {
            addCriterion("day5 not between", value1, value2, "day5");
            return (Criteria) this;
        }

        public Criteria andDay6IsNull() {
            addCriterion("day6 is null");
            return (Criteria) this;
        }

        public Criteria andDay6IsNotNull() {
            addCriterion("day6 is not null");
            return (Criteria) this;
        }

        public Criteria andDay6EqualTo(Integer value) {
            addCriterion("day6 =", value, "day6");
            return (Criteria) this;
        }

        public Criteria andDay6NotEqualTo(Integer value) {
            addCriterion("day6 <>", value, "day6");
            return (Criteria) this;
        }

        public Criteria andDay6GreaterThan(Integer value) {
            addCriterion("day6 >", value, "day6");
            return (Criteria) this;
        }

        public Criteria andDay6GreaterThanOrEqualTo(Integer value) {
            addCriterion("day6 >=", value, "day6");
            return (Criteria) this;
        }

        public Criteria andDay6LessThan(Integer value) {
            addCriterion("day6 <", value, "day6");
            return (Criteria) this;
        }

        public Criteria andDay6LessThanOrEqualTo(Integer value) {
            addCriterion("day6 <=", value, "day6");
            return (Criteria) this;
        }

        public Criteria andDay6In(List<Integer> values) {
            addCriterion("day6 in", values, "day6");
            return (Criteria) this;
        }

        public Criteria andDay6NotIn(List<Integer> values) {
            addCriterion("day6 not in", values, "day6");
            return (Criteria) this;
        }

        public Criteria andDay6Between(Integer value1, Integer value2) {
            addCriterion("day6 between", value1, value2, "day6");
            return (Criteria) this;
        }

        public Criteria andDay6NotBetween(Integer value1, Integer value2) {
            addCriterion("day6 not between", value1, value2, "day6");
            return (Criteria) this;
        }

        public Criteria andDay7IsNull() {
            addCriterion("day7 is null");
            return (Criteria) this;
        }

        public Criteria andDay7IsNotNull() {
            addCriterion("day7 is not null");
            return (Criteria) this;
        }

        public Criteria andDay7EqualTo(Integer value) {
            addCriterion("day7 =", value, "day7");
            return (Criteria) this;
        }

        public Criteria andDay7NotEqualTo(Integer value) {
            addCriterion("day7 <>", value, "day7");
            return (Criteria) this;
        }

        public Criteria andDay7GreaterThan(Integer value) {
            addCriterion("day7 >", value, "day7");
            return (Criteria) this;
        }

        public Criteria andDay7GreaterThanOrEqualTo(Integer value) {
            addCriterion("day7 >=", value, "day7");
            return (Criteria) this;
        }

        public Criteria andDay7LessThan(Integer value) {
            addCriterion("day7 <", value, "day7");
            return (Criteria) this;
        }

        public Criteria andDay7LessThanOrEqualTo(Integer value) {
            addCriterion("day7 <=", value, "day7");
            return (Criteria) this;
        }

        public Criteria andDay7In(List<Integer> values) {
            addCriterion("day7 in", values, "day7");
            return (Criteria) this;
        }

        public Criteria andDay7NotIn(List<Integer> values) {
            addCriterion("day7 not in", values, "day7");
            return (Criteria) this;
        }

        public Criteria andDay7Between(Integer value1, Integer value2) {
            addCriterion("day7 between", value1, value2, "day7");
            return (Criteria) this;
        }

        public Criteria andDay7NotBetween(Integer value1, Integer value2) {
            addCriterion("day7 not between", value1, value2, "day7");
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