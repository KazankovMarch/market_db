package ru.adkazankov;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Properties properties = new Properties();
    static {
        try {
            ClassLoader classLoader = Config.class.getClassLoader();
            File file = new File(classLoader.getResource("app.properties").getFile());
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String DB_PASS = properties.getProperty("db_pass");
    public static final String DB_URL = properties.getProperty("db_url");
    public static final String DB_USER = properties.getProperty("db_user");
    public static final String SHOP = "Магазин";
    public static final String TRAD = "Продавцы";
    public static final String REGI = "Регистрация";


}
