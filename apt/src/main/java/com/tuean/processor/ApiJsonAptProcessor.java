package com.tuean.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.tuean.annotation.ApiJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

//@AutoService(ApiJsonAptProcessor.class)
@SupportedAnnotationTypes("com.tuean.annotation.ApiJson")
@SupportedSourceVersion(SourceVersion.RELEASE_19)
public class ApiJsonAptProcessor extends AbstractProcessor {

    private static Logger logger = LoggerFactory.getLogger(ApiJsonAptProcessor.class);


    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        logger.info("init");
        System.out.println("init");

        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("11");
//        https://www.cnblogs.com/throwable/p/9139908.html
        messager.printMessage(Diagnostic.Kind.NOTE, "111");
        for (TypeElement typeElement : annotations) {
            try {
//                parseElement(typeElement);
                new File("E:\\Win-Idea\\Activation_Code\\test");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(typeElement);
            logger.info("element:{}", typeElement);
            System.out.println("22");
            for (Element element : annotatedElements) {
                if (element.getKind() == ElementKind.METHOD) {
                    logger.info("element:{}", element);
                    System.out.println("element");
                }
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(ApiJson.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }




//    private void parseElement(Element element) throws Exception {
//        String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
//        String className = element.getSimpleName().toString();
//
//        messager.printMessage(Diagnostic.Kind.NOTE, "=HelloProcessor=" + packageName + "/" + className);
//
//        FieldSpec id = FieldSpec.builder(Long.class, "id")
//                .addModifiers(Modifier.PRIVATE)
//                .addJavadoc("ID")
//                .build();
//        FieldSpec name = FieldSpec.builder(String.class, "name")
//                .addModifiers(Modifier.PRIVATE)
//                .addJavadoc("名称")
//                .build();
//
//        TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")
//                .addModifiers(Modifier.PUBLIC)
////                .addAnnotation(Getter.class)
////                .addAnnotation(Setter.class)
//                .addField(id)
//                .addField(name)
//                .build();
//
//        JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
//                .build();
//
//        // 写入
//        javaFile.writeTo(filer);
//    }
}
