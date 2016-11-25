package easy.cluster.annoation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import easy.cluster.IFilter;

@Target({ TYPE, METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Filter {
	Class<IFilter>[] filter();
}
