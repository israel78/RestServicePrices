package es.beonit.prices.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Prices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "brand_id")
    private int brand_id;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "price_list")
    private int priceList;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "priority")
    private double priority;
    @Column(name = "price")
    private double price;
    @Column(name = "curr")
    private String curr;

}
