package com.github.zhengframework.validator.aop;

import com.google.inject.matcher.AbstractMatcher;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import javax.validation.Constraint;
import javax.validation.Valid;

/**
 * Validation method matcher. Method require validation if it's annotated with {@code @Valid} or any
 * validation annotation (all validation annotations must be annotated with {@code @Constraint}) or
 * any param annotated.
 */
public class ValidatedMethodMatcher extends AbstractMatcher<Method> {

  @Override
  public boolean matches(final Method method) {
    boolean matches = isValidationAnnotations(method.getAnnotations());
    if (!matches) {
      for (Annotation[] annotations : method.getParameterAnnotations()) {
        if (isValidationAnnotations(annotations)) {
          matches = true;
          break;
        }
      }
    }
    return matches;
  }

  private boolean isValidationAnnotations(final Annotation... annotations) {
    boolean matches = false;
    for (Annotation ann : annotations) {
      final Class<? extends Annotation> annotationType = ann.annotationType();
      if (Valid.class.equals(annotationType) || annotationType
          .isAnnotationPresent(Constraint.class)) {
        matches = true;
        break;
      }
    }
    return matches;
  }
}
