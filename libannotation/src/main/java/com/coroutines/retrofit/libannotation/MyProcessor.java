package com.coroutines.retrofit.libannotation;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.mydemo.toolslist.annotation.Teachers")
public class MyProcessor extends AbstractProcessor {
    /**
     * {@inheritDoc}
     *
     * @param annotations
     * @param roundEnv
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "------------>>>>>>："+annotations.size());
        if (!annotations.isEmpty()){
            try {
                JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile("com.a.B");
                OutputStream os = sourceFile.openOutputStream();
                os.write("package com.a;\n @com.enjoy.ann.Lance\n public class B{}".getBytes());
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        for (TypeElement element : annotations){
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    "element====>>" + element.getQualifiedName() + "\n" + element.getQualifiedName() + "\n" +
                    element.asType());
            List<? extends Element> list = element.getEnclosedElements();
            for (int i = 0; i < list.size(); i++) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                        "list====>>" + list.get(i).toString() + "\n" + list.get(i).getSimpleName() + "\n" +
                                element.asType());
            }

            Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(element);
            for (Element childElement : set){
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                        "childElement====>>" + childElement.getSimpleName());
            }
        }
        return false;
    }
}