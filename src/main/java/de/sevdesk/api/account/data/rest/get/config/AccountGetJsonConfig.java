package de.sevdesk.api.account.data.rest.get.config;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.sevdesk.api.account.data.rest.get.CheckAccountGet;
import de.sevdesk.api.account.data.rest.get.TermAccountGet;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RegisterForReflection
@JacksonAnnotationsInside
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CheckAccountGet.class, name = "CheckAccountGet"),
        @JsonSubTypes.Type(value = TermAccountGet.class, name = "TermAccountGet")
})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountGetJsonConfig {
}
