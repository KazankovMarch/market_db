package ru.adkazankov;

import ru.adkazankov.dao.ProviderDao;
import ru.adkazankov.dao.RegistrationDao;
import ru.adkazankov.dao.ShopDao;
import ru.adkazankov.dao.TraderDao;

public class Main {

    public static void main(String[] args) {
        ProviderDao providerDao = new ProviderDao();
        RegistrationDao registrationDao = new RegistrationDao();
        ShopDao shopDao = new ShopDao();
        TraderDao traderDao = new TraderDao();

        System.out.println(providerDao.findByKey("Цветы"));
        System.out.println(registrationDao.findByKey("Василий"));
        System.out.println(shopDao.findByKey("Яблоко"));
        System.out.println(traderDao.findBykey("Василий", "Казань", "Яблоко"));
    }

}
