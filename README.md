# Maialt
Class manipulation, and some random stuff. But mostly class manipulation.

## What is Maialt
Well the first thing I want to get out of the way is how to pronounce it: It is pronounced 'may-alt'. But more importantly, what does it do?

Maialt comes in four seperate maven packages:
* `maialt-common`: Contains a few classes, depended on by all other packages.
* `maialt-classes`: Contains the class searcher - more on that in a bit.
* `maialt-hooks`: Contains an experimental way of interacting with other threads, might be split off.
* `maialt-injector`: Uses ASM to create callbacks to methods inside existing methods.

## The Class Searcher
The class searcher (`ClassSearcher`) and member searcher (`MemberSearcher`) classes allow you
to search for classes that match a criteria. It can search inside an array of classes, a
directory, or a ZIP file. Here's an example:

```java
import com.liamcoalstudio.maialt.classes.ValidationException;
import com.liamcoalstudio.maialt.classes.classes.ClassSearcher;
import com.liamcoalstudio.maialt.classes.classes.AnnotationClassMatcher;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Finds all classes in test.jar with the annotation `Main.Annotated`
 */
public class Main {
    // RUNTIME retention is needed to use reflection on annotations.
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Annotated {}

    public static void main(String[] args) {
        try {
            ClassSearcher.stream(new File("test.jar"), new AnnotationClassMatcher<>(Annotated.class))
                    .forEach(System.out::println); // print all classes found
        } catch (IOException | ClassNotFoundException | ValidationException e) {
            // Oh no!
            //
            // IOException is probably thrown if the file isn't found.
            // ValidationException shouldn't be thrown without some hacking
            //   to make AnnotationClassMatcher accept non annotation classes,
            //   and at the moment, that class is the only one to throw this.
            // ClassNotFoundException shouldn't ever be thrown, and if it is 
            //   opening an issue on this project would be very helpful.
            e.printStackTrace();
        }
    }
}
```

## The Injector

The Bytecode Injector allows you to inject Java code into existing methods. This ones a
bit rough at the moment, but it works for simple injections. All method and field
references use internal java descriptors and class names. These are very different from
what appears in source files, so I will try my best to explain them:

### Class Names
Lets use `java.lang.Object` as an example. The internal identifier for this class would
be it's fully qualified name with `.` replaced with `/`, making `java/lang/Object`. But
what about subclasses? If there were a `java.lang.Object.ObjectAlso`, that would turn
into `java/lang/Object/ObjectAlso`, right? It's actually 
`java/lang/Object$ObjectAlso`, because the subclasses are turned into seperate classes,
and `$` was used to replace `/` in internal names. You can actually see this if you open
up a jar file. All subclasses would be called something like `Method$Bundle.class`.

### Descriptors
Descriptors decide what methods take as arguments or return, and decide what type fields
are.

#### Types
Descriptors use single characters for primitive types, probably to save space on methods
that take way too many arguments. These are the primitive types:
* `B` = `byte`
* `C` = `char`
* `S` = `short`
* `I` = `int`
* `J` = `long`
* `F` = `float`
* `D` = `double`
* `Z` = `boolean`
* `V` = `void`

But that's not it. There is also:
* `L<class name, as above>;` = `<class name>`
* `[<type>` = `<type>[]`

#### Field/Method Descriptors
Field descriptors are just a single type, as described above. But method descriptors
are a bit more complicated. Here are some examples

* `(BCSIJFDZ)V` = `void allPrimitives(byte a, char b, short c, int d, long e, float f, double g, boolean h)`
* `(Ljava/lang/Object;)I` = `int convertToInt(Object o)`
* `(BBBBB)Z` = `boolean allMatch(byte a, byte b, byte c, byte d, byte e)`
* `(Ljava/lang/Object;I)[Ljava/lang/Object;` = `Object[] duplicate(Object o, int times)`

### Injector
`Injector` provides a method: `inject`. That method takes a `byte[]` of the original class
(along with a bunch of other options), injects method calls into it, and returns the modified
class.

You can use this directly if you need to, but it's not recommended, as this library also 
provides `AnnotationProcessor`, which does all this for you, and all you have to do then
is annotate your classes and set it up.

### HijackedClassLoader
To define the new classes, the injector uses a `HijackedClassLoader`, which defines a method
called `define` that takes a `byte[]` as an argument. This method allows defining
classes directly from bytes. There isn't really anything special to creating one, just
pass `<class to inject to>.class.getClassLoader()` to the constructor.

### AnnotationProcessor
`maialt-injector` also comes with some annotations, and an annotation processor
(`AnnotationProcessor`). This processor can be used along with the annotations to not
have to directly mess with the injector, leaving `AnnotationProcessor` to do the work
for you. Here's an example:

```java
import com.liamcoalstudio.maialt.injector.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * An injection class injecting into itself is OK, though not very useful.
 *
 * The saveTo option allows you to specify a filename to save the output
 * to. It can be useful when debugging. If not specified, no output file
 * is created.
 */
@InjectsInto(value = InjectTest.class, saveTo = "InjectTest-modified.class")
public class InjectTest {
    public int field;

    public void doNothing() {
        // This function does nothing... unless...
    }

    public void methodAlso(int arg0, int arg1) {
        field = 0;
        doNothing();
        field = 7;
        arg1 = 3;
        doNothing();
    }

    /**
     * Injects code right at the start of doNothing.
     *
     * There is also InjectLocation.END for injecting at the return of the
     * method. Not just the return keyword, though. Java makes sure every
     * method has the return bytecode inside it, even if return isn't in
     * the source.
     */
    @Inject(method = @Method("doNothing()V"),
            at = InjectLocation.START)
    public static void doSomething() {
        System.out.println("Hello from doNothing()V! Not doing nothing anymore though.");
    }

    /**
     * Injects code right before a method is invoked.
     *
     * This inject also has some arguments. These are the allowed arrangements:
     * - void (): No context is passed. This is the safest.
     * - void (Arguments): All arguments are passed to the inject. Might have
     *   a 5 argument limit
     * - void (type of object, Arguments): For instance methods only.
     *   Using this on a static will crash the JVM!
     */
    @Inject(method = @Method("methodAlso(II)V"),
            at = InjectLocation.BEFORE_INVOKE,
            target = "InjectTest;doNothing()V")
    public static void beforeInvoke(InjectTest object, Arguments args) {
        System.out.println("Right before doing nothing, the second argument is " + args.get(1).getInt());
    }

    /**
     * Injects right before a field access. The `index` option allows to
     * specify a single match to use if there are multiple. Not specifying
     * it will tell Injector to add it to every instance.
     */
    @Inject(method = @Method("methodAlso(II)V"),
            at = InjectLocation.BEFORE_FIELD,
            index = 0,
            target = "InjectTest;field:I")
    public static void beforeField() {
        System.out.println("Right before first field access");
    }

    /**
     * Same thing as BEFORE_FIELD except it injects after the access.
     * This inject also skips the first access.
     */
    @Inject(method = @Method("methodAlso(II)V"),
            at = InjectLocation.AFTER_FIELD,
            index = 1,
            target = "InjectTest;field:I")
    public static void afterSecondField() {
        System.out.println("Right after last field access");
    }

    public static void main(String[] args) {
        try {
            // Create a new HijackedClassLoader, and inject into
            // it.
            HijackedClassLoader ldr = new HijackedClassLoader(InjectTest.class.getClassLoader());
            AnnotationProcessor.inject(InjectTest.class, ldr);
            
            // You shouldn't use the original class because it's loaded
            // with a different loader, and wouldn't have the changes.
            Class<?> clazz = ldr.loadClass("InjectTest");
            Object instance = clazz.getConstructor().newInstance();
            
            // Ideally, a central method (like main(...)) would be
            // executed instead, but since this example isn't very
            // complex, this works nicely as well.
            clazz.getMethod("doNothing").invoke(instance);
            clazz.getMethod("methodAlso", int.class, int.class).invoke(instance, 7, 5);
        } catch (IOException | ReflectiveOperationException e) {
            // I've condensed all the exceptions reflection throws
            // into ReflectiveOperationException because reflection
            // has a lot of exceptions.
            //
            // AnnotationProcessor just throws IOException.
            e.printStackTrace();
        }
    }
}
```

## Hooks
This section is TODO.
Reason: `maialt-hooks` may be split off at any time, and I want to be sure where
its going before I write docs for it.
