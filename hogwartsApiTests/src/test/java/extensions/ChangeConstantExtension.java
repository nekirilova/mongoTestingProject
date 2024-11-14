package extensions;

import nosql.MongoDbMethods;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

public class ChangeConstantExtension implements BeforeEachCallback, AfterTestExecutionCallback {
    MongoDbMethods mongoDbMethods = new MongoDbMethods();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ChangeConstant changeConstant = AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), ChangeConstant.class)
                .orElse(AnnotationSupport.findAnnotation(context.getRequiredTestClass(), ChangeConstant.class).orElse(null));
        if (changeConstant != null) {
            String constantName = changeConstant.constantName();
            String beforeTestConstantValue = changeConstant.beforeTestConstantName();
        //  проверяем значение константы и меняем ее в MongoDB
            if (!mongoDbMethods.getConstantValueByName("constant", constantName)
                    .equals(beforeTestConstantValue)) {
                mongoDbMethods.changeConstantByName(constantName, beforeTestConstantValue);
            } else {
                System.out.println("Значение константы уже актуально");
            }
        }

    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        ChangeConstant changeConstant = AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), ChangeConstant.class)
                .orElse(AnnotationSupport.findAnnotation(context.getRequiredTestClass(), ChangeConstant.class).orElse(null));
        if (changeConstant != null) {
            String constantName = changeConstant.constantName();
            String afterTestConstantValue = changeConstant.afterTestConstantName();
            //  проверяем значение константы и меняем ее в MongoDB
            if (!mongoDbMethods.getConstantValueByName("constant", constantName)
                    .equals(afterTestConstantValue)) {
                mongoDbMethods.changeConstantByName(constantName, afterTestConstantValue);
            } else {
                System.out.println("Значение константы уже актуально");
            }
        }
    }


}
