package com.pruebatecnica.GestorEquipos.specs;


import java.util.List;

public class SearchCriteria {

    private String key;
    private Object value;
    private List<String> values; // Para las operaciones SearchOperation.IN
    private SearchOperation operation;
    private Boolean ignoreNull = false;

    /**
     * Contructor generico
     * @param key campo
     * @param operation tipo operacion
     */
    public SearchCriteria(String key, SearchOperation operation) {
        this.key = key;
        this.operation = operation;
    }

    /**
     * Contructor generico
     * @param key campo
     * @param value valores
     * @param operation tipo operacion
     */
    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    /**
     * Contructor ignorando los valores null
     * @param key campo
     * @param value valores
     * @param operation tipo operacion
     * @param ignoreNull ignorar null
     */
    public SearchCriteria(String key, Object value, SearchOperation operation, Boolean ignoreNull) {
        this.key = key;
        this.value = value;
        this.operation = operation;
        this.ignoreNull = ignoreNull;
    }

    /**
     * Contructor para operaciones de tipo SearchOperation.IN
     * @param key campo
     * @param values valores
     */
    public SearchCriteria(String key, List<String> values) {
        this.key = key;
        this.values = values;
        this.operation = SearchOperation.IN;
    }

    /**
     * Contructor para operaciones de tipo SearchOperation.IN ignorando los valores null
     * @param key campo
     * @param values valores
     * @param ignoreNull ignorar null
     */
    public SearchCriteria(String key, List<String> values, Boolean ignoreNull) {
        this.key = key;
        this.values = values;
        this.operation =  SearchOperation.IN;
        this.ignoreNull = ignoreNull;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }

    public Boolean getIgnoreNull() {
        return ignoreNull;
    }

    public void setIgnoreNull(Boolean ignoreNull) {
        this.ignoreNull = ignoreNull;
    }
}
