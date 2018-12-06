package utils.queries;

public enum HQLSection {
    SELECT_USER_BY_NAME("from User where lower(name) like :name"),
    SELECT_USER_BY_LOGIN_AND_PASS("from User where login = :login and password = :password"),
    SELECT_QUESTION_BY_CONTENT("from Question where content =: content"),
    SELECT_ANSWER_BY_CONTENT("from Answer where content=:content"),
    SELECT_RELATION_WITHOUT_ANSWER("from Relations where user =: user and question =: question and answer is null"),
    DELETE_RELATION_BY_USER("delete from Relations where user =: user"),
    DELETE_RELATION_BY_QUESTION("delete from Relations where question =: question");

    private final String hql;

    HQLSection(String hql) {
        this.hql = hql;
    }

    public String getHql() {
        return hql;
    }
}
