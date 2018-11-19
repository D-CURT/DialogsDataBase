package utils;

public enum  SQLSection {
    GET_USER("SELECT * FROM users WHERE name=?"),
    GET_QUESTION("SELECT * FROM question WHERE content=?"),
    GET_ANSWER("SELECT * FROM answer WHERE content=?"),
    GET_RELATION_BY_USER("SELECT * FROM relations WHERE id_user=?"),
    GET_RELATION_BY_USER_AND_QUESTION("SELECT * FROM relations WHERE id_user=? AND id_question=?"),
    GET_FULL_DATA("SELECT users.name, question.content, answer.content FROM users " +
            "JOIN relations ON relations.id_user=users.id " +
            "JOIN question ON relations.id_question=question.id " +
            "JOIN answer ON relations.id_answer=answer.id"),
    ADD_USER("INSERT INTO users (name) VALUES (?)"),
    ADD_QUESTION("INSERT INTO question (content) VALUES (?)"),
    ADD_ANSWER("INSERT INTO answer (content) VALUES (?)"),
    ADD_RELATION("INSERT INTO relations (id_user, id_question) VALUES (?, ?)"),
    UPDATE_RELATION("UPDATE relations SET id_answer=? WHERE id_user=? AND id_question=? AND id_answer IS NULL"),
    REMOVE_USER("DELETE FROM users WHERE id=?"),
    REMOVE_QUESTION("DELETE FROM question WHERE id=?"),
    REMOVE_RELATION_BY_USER("DELETE FROM relations WHERE id_user=?"),
    REMOVE_RELATION_BY_USER_AND_QUESTION("DELETE FROM relations WHERE id_user=? AND id_question=?");


    private final String SQL;

    SQLSection(String SQL) {
        this.SQL = SQL;
    }

    public String getSQL() {
        return SQL;
    }
}
