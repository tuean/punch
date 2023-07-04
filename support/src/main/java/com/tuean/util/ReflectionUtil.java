package com.tuean.util;

import org.reflections.Reflections;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

public class ReflectionUtil {

    /**
     * Returns a list of all classes in a package.
     */
    public static List<Class<?>> getAllClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        for (String directory : System.getProperty("java.class.path").split(":")) {
            String filePath = directory + "/" + path;
            File file = new File(filePath);
            if (file.exists() && file.isDirectory()) {
                for (File subFile : file.listFiles()) {
                    if (subFile.getName().endsWith(".class")) {
                        String className = packageName + "." + subFile.getName().replace(".class", "");
                        try {
                            classes.add(Class.forName(className));
                        } catch (ClassNotFoundException e) {
                            // Ignore this class
                        }
                    }
                }
            }
        }
        return classes;
    }

    /**
     * Returns a list of classes that have the specified annotation.
     */
    public static List<Class<?>> findAnnotatedClasses(List<Class<?>> classes, Class<? extends Annotation> annotation) {
        List<Class<?>> annotatedClasses = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(annotation)) {
                annotatedClasses.add(clazz);
            }
        }
        return annotatedClasses;
    }

    public static Set<Class<?>> findAnnotatedClasses(String packageName, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageName);

        return reflections.getTypesAnnotatedWith(annotation);
    }
}
