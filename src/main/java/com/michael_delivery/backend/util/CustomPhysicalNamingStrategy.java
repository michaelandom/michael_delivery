package com.michael_delivery.backend.util;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String tableName = name.getText();
        String capitalizedTableName = getFinalName (tableName.substring(0, 1).toUpperCase() + tableName.substring(1),true);
        return Identifier.toIdentifier(capitalizedTableName);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        String tableName = name.getText().strip();
        String finalColumnName = getFinalName(tableName,false);
        return Identifier.toIdentifier(finalColumnName);
    }

    private static String getFinalName(String tableName, boolean capitalize) {
        StringBuilder snakeCaseName = new StringBuilder();
        for (char c : tableName.toCharArray()) {
            if (Character.isUpperCase(c)) {
                snakeCaseName.append("_");
                if (capitalize) {
                    snakeCaseName.append(c);
                } else{
                    snakeCaseName.append(Character.toLowerCase(c));
                }
            } else {
                snakeCaseName.append(c);
            }
        }
        return  snakeCaseName.toString().startsWith("_") ? snakeCaseName.substring(1) : snakeCaseName.toString();
    }
}
