package com.minegocio.backend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@Component
public class DatabaseProvisioningUtil {

    private ObjectMapper objectMapper;

    public DatabaseProvisioningUtil() {

    }

    // --------------------------------------------
    // 1. Generar contraseña segura
    // --------------------------------------------
    public String generarPasswordSegura() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public String generarJsonEstructuraTablaUsuarios() {

        String json = """
                {
                    "tabla": "usuarios",
                    "engine": "InnoDB",
                    "charset": "utf8mb4",
                    "collation": "utf8mb4_unicode_ci",
                    "columnas": {
                        "id": "INT AUTO_INCREMENT PRIMARY KEY",
                        "nombre_usuario": "VARCHAR(100) NOT NULL",
                        "correo": "VARCHAR(255) NOT NULL",
                        "rol": "ENUM('OWNER','ADMIN','MANAGER','EMPLOYEE','VIEWER','INVITED') NOT NULL",
                        "fecha_registro": "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    },
                    "indices": [
                        {
                            "nombre": "idx_usr_correo",
                            "columna": "correo"
                        }
                    ],
                    "ddl": "CREATE TABLE IF NOT EXISTS usuarios (id INT AUTO_INCREMENT PRIMARY KEY, nombre_usuario VARCHAR(100) NOT NULL, correo VARCHAR(255) NOT NULL, rol ENUM('OWNER','ADMIN','MANAGER','EMPLOYEE','VIEWER','INVITED') NOT NULL, fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP, INDEX idx_usr_correo (correo)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;"
                }
                """;

        return json;
    }

    public String generarJsonDesdeDDL(String ddl) {

        // Normalizamos
        String ddlClean = ddl.replaceAll("\\s+", " ").trim();

        // === 1. Obtener nombre tabla ===
        String nombreTabla = "";
        var matcherTabla = java.util.regex.Pattern
                .compile("CREATE TABLE IF NOT EXISTS ([a-zA-Z0-9_]+)", java.util.regex.Pattern.CASE_INSENSITIVE)
                .matcher(ddlClean);
        if (matcherTabla.find()) {
            nombreTabla = matcherTabla.group(1);
        }

        // === 2. Obtener columnas ===
        String columnasRaw = ddlClean.substring(ddlClean.indexOf("(") + 1, ddlClean.lastIndexOf(")"));

        String[] parts = columnasRaw.split(",");

        StringBuilder columnasJson = new StringBuilder();
        columnasJson.append("{\n");

        java.util.List<String> indices = new java.util.ArrayList<>();

        for (String part : parts) {
            part = part.trim();

            // Índices
            if (part.toUpperCase().startsWith("INDEX") || part.toUpperCase().startsWith("KEY")) {
                String[] idxParts = part.split("\\s+");
                String idxName = idxParts[1];
                String idxCol = part.substring(part.indexOf("(") + 1, part.indexOf(")"));
                indices.add("{ \"nombre\": \"" + idxName + "\", \"columna\": \"" + idxCol + "\" }");
                continue;
            }

            // Columnas normales
            if (part.contains(" ")) {
                String colName = part.split(" ")[0].replace("`", "");
                String colType = part.substring(colName.length()).trim();
                columnasJson.append("    \"" + colName + "\": \"" + colType + "\",\n");
            }
        }

        // Eliminar última coma
        if (columnasJson.lastIndexOf(",") == columnasJson.length() - 2) {
            columnasJson.deleteCharAt(columnasJson.length() - 2);
        }
        columnasJson.append("}");

        // === 3. Obtener ENGINE ===
        String engine = "InnoDB";
        var matcherEngine = java.util.regex.Pattern
                .compile("ENGINE=([a-zA-Z0-9_]+)", java.util.regex.Pattern.CASE_INSENSITIVE)
                .matcher(ddlClean);
        if (matcherEngine.find())
            engine = matcherEngine.group(1);

        // === 4. Obtener CHARSET ===
        String charset = "utf8mb4";
        var matcherCharset = java.util.regex.Pattern
                .compile("CHARSET=([a-zA-Z0-9_]+)", java.util.regex.Pattern.CASE_INSENSITIVE)
                .matcher(ddlClean);
        if (matcherCharset.find())
            charset = matcherCharset.group(1);

        // === 5. Obtener COLLATE ===
        String collate = "utf8mb4_unicode_ci";
        var matcherCollate = java.util.regex.Pattern
                .compile("COLLATE=([a-zA-Z0-9_]+)", java.util.regex.Pattern.CASE_INSENSITIVE)
                .matcher(ddlClean);
        if (matcherCollate.find())
            collate = matcherCollate.group(1);

        // === 6. Generar JSON final ===
        String json = """
                {
                    "tabla": "%s",
                    "engine": "%s",
                    "charset": "%s",
                    "collation": "%s",
                    "columnas": %s,
                    "indices": [%s],
                    "ddl": "%s"
                }
                """.formatted(
                nombreTabla,
                engine,
                charset,
                collate,
                columnasJson.toString(),
                String.join(", ", indices),
                ddlClean);

        return json;
    }

    public String jsonToSql(String jsonString) throws JsonProcessingException {

        JsonNode rootNode = objectMapper.readTree(jsonString);

        JsonNode ddlNode = rootNode.get("ddl");

        if (ddlNode == null || ddlNode.isNull() || ddlNode.asText().isBlank()) {

            throw new IllegalArgumentException(
                    "El campo 'ddl' (SQL DDL) está ausente, es nulo, o está vacío en el JSON.");
        }

        return ddlNode.asText();
    }

}
