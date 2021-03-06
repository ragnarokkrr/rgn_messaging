package org.rgn.messaging.poc.reqreplcontr.annotations;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;

@Target(value = { TYPE })
public @interface MessagingContract {

	boolean reflectionName() default false;

	String queue() default "";

	String defautlResponseSuffix() default "_response";

	boolean temporaryResponse() default true;

	boolean setMessageIdAsCorrelationId() default false;
}
