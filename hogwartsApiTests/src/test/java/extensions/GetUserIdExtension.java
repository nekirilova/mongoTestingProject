package extensions;

import apiMethods.UsersMethods;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import utilMethods.UtilMethods;

public class GetUserIdExtension implements BeforeEachCallback, ParameterResolver {
    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(GetUserIdExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        GetUserId getUserId = AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), GetUserId.class)
                .orElse(null);
        if(getUserId != null) {
            // удаляем всех пользователей и создаем дефолтных
            String usersList = new UsersMethods().deleteUsersAndCreateDefaultUsers().extract().asString();
            //получаем из аннотации имя пользователя
            String userName = getUserId.name();
            //получаем айди пользователя
            String userId = new UtilMethods().getUserByName(usersList, userName).getId();
            context.getStore(NAMESPACE).put("userId", userId);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(String.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(GetUserIdExtension.NAMESPACE).get("userId", String.class);
    }
}
