// Archivo: src/main/java/com/aluracursos/conversordemonedas/ExchangeRateResponse.java
package com.aluracursos.conversordemonedas;

public class ExchangeRateResponse {
    private String result;
    private String documentation;
    private String terms_of_use;
    private long time_last_update_unix;
    private String time_last_update_utc;
    private long time_next_update_unix;
    private String time_next_update_utc;
    private String base_code;
    private String target_code;
    private double conversion_rate; // Este es el campo más importante para nosotros

    // Métodos getters para acceder a los datos
    // Gson usará estos getters (y setters si los tuvieras, aunque no son estrictamente necesarios para leer)
    // para mapear el JSON a tu objeto.

    public String getResult() {
        return result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getTerms_of_use() {
        return terms_of_use;
    }

    public long getTime_last_update_unix() {
        return time_last_update_unix;
    }

    public String getTime_last_update_utc() {
        return time_last_update_utc;
    }

    public long getTime_next_update_unix() {
        return time_next_update_unix;
    }

    public String getTime_next_update_utc() {
        return time_next_update_utc;
    }

    public String getBase_code() {
        return base_code;
    }

    public String getTarget_code() {
        return target_code;
    }

    public double getConversion_rate() {
        return conversion_rate;
    }

    // Opcional: un método toString() para facilitar la depuración
    @Override
    public String toString() {
        return "ExchangeRateResponse{" +
                "result='" + result + '\'' +
                ", base_code='" + base_code + '\'' +
                ", target_code='" + target_code + '\'' +
                ", conversion_rate=" + conversion_rate +
                '}';
    }
}