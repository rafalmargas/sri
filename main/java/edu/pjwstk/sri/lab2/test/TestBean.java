package edu.pjwstk.sri.lab2.test;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.pjwstk.sri.lab2.dao.CategoryDao;
import edu.pjwstk.sri.lab2.dao.ProductDao;
import edu.pjwstk.sri.lab2.model.Category;

@Named("testBean")
@RequestScoped
public class TestBean implements Serializable {

	@Inject
	private CategoryDao catService;
	@Inject
	private ProductDao prodService;
	
	public TestBean() {
	}
	
	public void test() {
		List<Category> listAll = catService.listAll(null, null);
		System.out.println(listAll);
	}
	

}
