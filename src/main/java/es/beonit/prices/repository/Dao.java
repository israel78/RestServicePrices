package es.beonit.prices.repository;

import es.beonit.prices.domain.Prices;
import es.beonit.prices.domain.User;

import java.util.Date;
import java.util.List;

public interface Dao {
    public Prices getPriceResponse(Date dateApply, int productId, int brandId);
    public List<User> getUsers();
}
