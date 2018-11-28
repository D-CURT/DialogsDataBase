package utils.queries;

public enum HQLSection {
    SELECT_USER("from User where lower(name) like :name"),
    DELETE_RELATION_BY_USER("delete from Relations where user =: user");

    private final String hql;

    HQLSection(String hql) {
        this.hql = hql;
    }

    public String getHql() {
        return hql;
    }
}
