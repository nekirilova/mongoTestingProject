package endpoints;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Logical {
    HIDE_STUDENTS("/logic/student/hide/"),
    CATCH_STUDENTS("/logic/student/catch/"),
    GET_STUDENTS_LIST("/logic/student/list/"),
    SEND_OWL("/logic/sendOwl"),
    CAST_SPELL("/logic/cast");
    private final String value;
}
