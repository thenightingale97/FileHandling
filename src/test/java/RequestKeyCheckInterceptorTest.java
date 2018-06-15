import com.google.inject.Inject;
import filemanager.exceptions.DateIsEmptyException;
import filemanager.interceptor.RequestKeyCheckInterceptor;
import filemanager.model.Command;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.ForbiddenException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class RequestKeyCheckInterceptorTest {

    @Inject
    RequestKeyCheckInterceptor requestKeyCheckInterceptor;

    @After
    public void tearDown() {
        requestKeyCheckInterceptor = null;
    }

    @Before
    public void setUp() {
        requestKeyCheckInterceptor = new RequestKeyCheckInterceptor();
    }

    @Test(expected = ForbiddenException.class)
    public void requestInterceptorEmptyKeyPassTest() throws Throwable {
        MethodInvocation methodInvocation = new MethodInvocation() {
            @Override
            public Method getMethod() {
                RequestKeyCheckInterceptorTest test = new RequestKeyCheckInterceptorTest();
                Method method = null;
                try {
                    method = test.getClass().getMethod("test", null);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return method;

            }

            @Override
            public Object[] getArguments() {
                return new Object[]{"", new Command().setClient("test").setDate(LocalDateTime.now())};
            }

            @Override
            public Object proceed() {
                return null;
            }

            @Override
            public Object getThis() {
                return null;
            }

            @Override
            public AccessibleObject getStaticPart() {
                return null;
            }
        };
        requestKeyCheckInterceptor.invoke(methodInvocation);
    }

    @Test
    public void requestInterceptorPresentKeyKeyTest() throws Throwable {
        MethodInvocation methodInvocation = new MethodInvocation() {
            @Override
            public Method getMethod() {
                RequestKeyCheckInterceptorTest test = new RequestKeyCheckInterceptorTest();
                Method method = null;
                try {
                    method = test.getClass().getMethod("test", null);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return method;

            }

            @Override
            public Object[] getArguments() {
                return new Object[]{"testKey", new Command().setClient("test").setDate(LocalDateTime.now())};
            }

            @Override
            public Object proceed() {
                return 200;
            }

            @Override
            public Object getThis() {
                return null;
            }

            @Override
            public AccessibleObject getStaticPart() {
                return null;
            }
        };
        assertEquals(200, requestKeyCheckInterceptor.invoke(methodInvocation));
    }

    @Test(expected = DateIsEmptyException.class)
    public void requestInterceptorEmptyDateFieldTest() throws Throwable {
        MethodInvocation methodInvocation = new MethodInvocation() {
            @Override
            public Method getMethod() {
                RequestKeyCheckInterceptorTest test = new RequestKeyCheckInterceptorTest();
                Method method = null;
                try {
                    method = test.getClass().getMethod("test", null);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return method;

            }

            @Override
            public Object[] getArguments() {
                return new Object[]{"testKey", new Command().setClient("test")};
            }

            @Override
            public Object proceed() {
                return null;
            }

            @Override
            public Object getThis() {
                return null;
            }

            @Override
            public AccessibleObject getStaticPart() {
                return null;
            }
        };
    }

    private void test() {

    }

}
