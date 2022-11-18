package de.invesdwin.norva.beanpath.spi;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

@NotThreadSafe
public abstract class ABeanPathAccessor implements IBeanPathAccessor {

    @Override
    public final String toString() {
        return new ToStringBuilder(this).append("beanPathFragment", getBeanPathFragment())
                .append("typePathFragment", getTypePathFragment())
                .toString();
    }

    @Override
    public boolean isNullable() {
        if (getRawType().isPrimitive()) {
            return false;
        }
        if (getAnnotation(NotNull.class) != null) {
            return false;
        }
        final Column columnAnnotation = getAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.nullable()) {
            return false;
        }
        return true;
    }

}
