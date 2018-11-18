package utils;

public enum  SQLSection {
    GET_USER("SELECT * FROM public.user WHERE name=?"),
    GET_QUESTION("SELECT * FROM public.question WHERE content=?"),
    GET_ANSWER("SELECT * FROM public.answer WHERE content=?"),
    ADD_USER("INSERT INTO public.user (name) VALUES (?)"),
    ADD_QUESTION("INSERT INTO public.question (content) VALUES (?)"),
    ADD_ANSWER("INSERT INTO public.answer (content) VALUES (?)"),
    ADD_RELATION("INSERT INTO public.relations (id_user, id_question) VALUES (?, ?)"),
    UPDATE_RELATION("UPDATE public.relations SET id_answer=? WHERE id_question=?"),
    REMOVE_USER("DELETE FROM public.user WHERE name=?");

    private final String SQL;

    SQLSection(String SQL) {
        this.SQL = SQL;
    }

    public String getSQL() {
        return SQL;
    }
}
