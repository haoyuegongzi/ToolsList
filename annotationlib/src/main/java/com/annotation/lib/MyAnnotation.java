package com.annotation.lib;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({"com.mydemo.toolslist.annotation.Teachers"})
public class MyAnnotation extends AbstractProcessor {
    Messager messager = null;

    @Override
    public boolean process(Set<? extends TypeElement> anno, RoundEnvironment env) {
        messager = processingEnv.getMessager();
        printMessage("<————annotations.size()————>" + anno.size());
        for (TypeElement element : anno) {
            printMessage("element====>>" + element.getQualifiedName());
            List<? extends Element> list = element.getEnclosedElements();
            for (int i = 0; i < list.size(); i++) {
                printMessage("childList====>>" + list.get(i).getSimpleName());
            }

            Set<? extends Element> set = env.getElementsAnnotatedWith(element);
            for (Element childElement : set) {
                printMessage("childElement====>>" + childElement.getSimpleName());
            }
        }
        return false;
    }

    private void printMessage(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }
}