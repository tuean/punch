package com.tuean.helper.apt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes("com.tuean.annotation.ApiJson")
@SupportedSourceVersion(SourceVersion.RELEASE_19)
public class ApiJsonAptProcessor extends AbstractProcessor {

    private static Logger logger = LoggerFactory.getLogger(ApiJsonAptProcessor.class);

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement typeElement : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(typeElement);
            for (Element element : annotatedElements) {
                if (element.getKind() == ElementKind.METHOD) {
                    logger.info("element:{}", element);
                }
            }
        }
        return true;
    }


}
