package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user, Car car) {
        user.setCar(car);
        sessionFactory.getCurrentSession().save(car);
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getFromCar(String model, int series) {
        String query = "from Car where model ='%s' and series=%d".formatted(model, series);
        Car car = sessionFactory.getCurrentSession().createQuery(query, Car.class).uniqueResult();
        if (car != null) {
            return car.getUser();
        }
        else {
            System.out.println("Нет такого User'а");
        }
        return new User();
    }
}
