package com.studyhub.infra.dialect;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MariaDBDialect;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.type.BasicType;
import org.hibernate.type.BasicTypeRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.Date;

import static org.hibernate.query.sqm.produce.function.FunctionParameterType.DATE;

public class CustomFunctionContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        TypeConfiguration typeConfiguration = functionContributions.getTypeConfiguration();
        BasicTypeRegistry basicTypeRegistry = typeConfiguration.getBasicTypeRegistry();

        BasicType<Date> dateType = basicTypeRegistry.resolve(StandardBasicTypes.DATE);

        Dialect dialect = functionContributions.getDialect();
        SqmFunctionRegistry functionRegistry = functionContributions.getFunctionRegistry();

        if (dialect instanceof MariaDBDialect) {
            functionRegistry
                    .patternDescriptorBuilder("to_date", "date_format(?1, '%Y-%m-%d')")
                    .setInvariantType(dateType)
                    .setExactArgumentCount(1)
                    .setParameterTypes(DATE)
                    .register();
        }

        if (dialect instanceof H2Dialect) {
            functionRegistry
                    .patternDescriptorBuilder("to_date", "formatdatetime(?1, 'yyyy-MM-dd')")
                    .setInvariantType(dateType)
                    .setExactArgumentCount(1)
                    .setParameterTypes(DATE)
                    .register();
        }

    }

}
