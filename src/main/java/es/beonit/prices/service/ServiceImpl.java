package es.beonit.prices.service;
import java.util.Date;

import es.beonit.prices.domain.Prices;
import es.beonit.prices.repository.Dao;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private Dao dao;

    public Prices getPriceResponse(Date dateApply, int productId, int brandId) {
        return dao.getPriceResponse(dateApply,productId,brandId);
    }

    public boolean login(String userName, String pass) {
        return null != dao.getUsers().stream()
                .filter(u -> u.getFirstName().equals(userName))
                .filter(u -> u.getPassw().equals(pass)).findAny().orElse(null);
    }
    public String getUserId(String userName) {
         return String.valueOf(dao.getUsers().stream()
                .filter(u -> u.getFirstName().equals(userName))
                .findAny().orElse(null).getId());

    }

}

