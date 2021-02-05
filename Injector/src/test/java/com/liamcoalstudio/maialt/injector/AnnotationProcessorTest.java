package com.liamcoalstudio.maialt.injector;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

@InjectsInto(InjectorTest.class)
public class AnnotationProcessorTest {
    @Test
    public void inject() throws IOException {
        HijackedClassLoader ldr = new HijackedClassLoader();
        AnnotationProcessor.inject(AnnotationProcessorTest.class, ldr);
    }

    @Inject(method = @Method("hashCode()I"), at = InjectLocation.START)
    public static void testInject() {

    }

    public int hashCode() {
        return super.hashCode();
    }
}