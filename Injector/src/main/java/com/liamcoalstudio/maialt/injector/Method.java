package com.liamcoalstudio.maialt.injector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Method {
    String value() default "";

    class Bundle {
        public final String className, methodName, descriptor;
        public int requiredIndex = -1;

        public Bundle(String className, String methodName, String descriptor) {
            this.className = className;
            this.methodName = methodName;
            this.descriptor = descriptor;
        }

        public Bundle(String className, Method method) {
            this.className = className;
            this.methodName = method.value().split("\\(")[0];
            this.descriptor = method.value().substring(method.value().indexOf('('));
        }

        public Bundle(Inject method) {
            if(method.target().matches(".+;.+\\(.*\\).+")) {
                this.className = method.target().split(";")[0];
                String a = method.target().split(";", 2)[1];
                this.methodName = a.substring(0, a.indexOf('('));
                this.descriptor = a.substring(a.indexOf('('));
            } else if(method.target().matches(".+;.+:.+")) {
                this.className = method.target().split(";")[0];
                String a = method.target().split(";", 2)[1];
                this.methodName = a.substring(0, a.indexOf(':'));
                this.descriptor = a.substring(a.indexOf(':')+1);
            } else {
                this.className = null;
                this.methodName = null;
                this.descriptor = null;
            }
            this.requiredIndex = method.index();
        }

        public boolean matches(String className, String methodName, String descriptor) {
            return this.className.equals(className) &&
                   this.methodName.equals(methodName) &&
                   this.descriptor.equals(descriptor);
        }
    }
}
