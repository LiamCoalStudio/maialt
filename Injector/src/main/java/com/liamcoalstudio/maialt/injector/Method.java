/*
 * MIT License
 *
 * Copyright (c) 2021 Liam Cole
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.liamcoalstudio.maialt.injector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method reference, used in {@link Inject}.
 */
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
