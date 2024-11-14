package extensions;
import org.junit.jupiter.api.extension.ExtendWith;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(ChangeConstantExtension.class)
public @interface ChangeConstant {

    String constantName();

    String beforeTestConstantName();

    String afterTestConstantName();

}
