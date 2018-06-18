package filemanager.interceptor;

import filemanager.model.Command;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.ext.Provider;

@Provider
public class RequestKeyCheckInterceptor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestKeyCheckInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object[] methodArguments = methodInvocation.getArguments();
        String key = (String) methodArguments[0];
        Command command = (Command) methodArguments[1];
        if (key == null || key.isEmpty()) {
            throw new ForbiddenException();
        } else {
            String hash = keyHash(key);
            LOGGER.info(hash + " has been authorized to run job: \n" +
                    "date: " + command.getDate() + "\n" +
                    "client: " + command.getClient());
            return methodInvocation.proceed();
        }
    }

    private String keyHash(String key) {
        return DigestUtils.sha256Hex(key);
    }
}
