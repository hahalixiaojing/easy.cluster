package easy.cluster.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Cluster {
	String cluster() default "FailoverCluster" ;
	String loadbalance() default "RandomLoadBalance";
	String service() default "";
	String invoker();
}
