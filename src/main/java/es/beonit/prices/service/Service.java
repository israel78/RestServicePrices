package es.beonit.prices.service;


import es.beonit.prices.domain.Prices;

import java.util.Date;

public interface Service {

    public Prices getPriceResponse(Date dateApply, int productId, int brandId);
    public boolean login(String userName, String pass);
    public String getUserId(String userName);

}
