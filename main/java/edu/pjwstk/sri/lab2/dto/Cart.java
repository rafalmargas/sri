package edu.pjwstk.sri.lab2.dto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import edu.pjwstk.sri.lab2.dao.ProductDao;
import edu.pjwstk.sri.lab2.model.Product;



@Stateful
@TransactionManagement(TransactionManagementType.CONTAINER)
public class Cart {
	String customerName;
	String customerId;
	List<OrderItem> contents;
	private ProductDao prodService;
	
	@Resource
	private EJBContext context;
	
	public void initialize(){
		contents = new ArrayList<OrderItem>();
	}
	
	public void addItem(OrderItem item){
		Product product = item.getProduct();
		int prodStock = product.getStock();
		int newProdStock = prodStock - item.getAmount();
		product.setStock(newProdStock);
		if (newProdStock < 0){
			context.setRollbackOnly();
		}
		prodService.update(product);
		contents.add(item);
	}
	
	public void removeItem(OrderItem item){
		Product product = item.getProduct();
		int prodStock = product.getStock();
		product.setStock(prodStock + item.getAmount());
		prodService.update(product);
		contents.remove(item);
	}
	
	public List<OrderItem> getContents(){
		return contents;
	}

	@Remove
	public void remove(){
		contents = null;
	}
}
