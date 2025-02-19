package aixiya.framework.backend.platform.foundation.api.annotation;



import aixiya.framework.backend.platform.foundation.api.utils.validator.SourceTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author wangqun865@163.com
 */
//@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = SourceTypeValidator.class)
public @interface SourceTypeValid {
    String message() default "文件来源不匹配！";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
