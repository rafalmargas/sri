package edu.pjwstk.sri.lab2.dao;

import edu.pjwstk.sri.lab2.model.Category;

import javax.ejb.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * DAO for Category
 */
@Startup
@Singleton
public class CategoryCacheService {

    private Map<Long, Category> categoryMap;
    @Inject
    private final CategoryDao categoryDao;

    //TODO: Tutaj możesz skorzystać z .getOrDefault i zwrócić coś innego, jeżeli nie znajdzie kategorii po id.
    public Category findById(Long id) {
        return categoryMap.get(id);
    }

    public List<Category> findAll() {
        return new ArrayList<>(categoryMap.values());
    }

    @Schedule(second = "*", minute = "1", hour = "*", persistent = false)
    @PostConstruct
    public void refreshAll() {
        this.categoryMap = categoryDao.listAll().stream()
                .collect(
                        toMap(
                                Category::getId,
                                identity()
                        )
                );
    }
}


