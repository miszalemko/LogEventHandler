package service;

import entity.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


public class EventService {

    private static final Logger logger = LogManager.getLogger(EventService.class);

    private static SessionFactory sessionFactory;

    public EventService() {
        initSessionFactory();
    }

    public static void initSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
            registry.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = registry.buildServiceRegistry();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
    }

    public void saveEvent(Event event) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Event eventFromDb = getEventsByEventId(event.getId());
        if (eventFromDb == null) {
            logger.debug("Saving event " + event.toString());
            session.save(event);
        }
        session.getTransaction().commit();
        session.close();
    }

    public Event getEventsByEventId(String eventId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Event.class);
        Event event = (Event) criteria.add(Restrictions.eq("id", eventId))
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return event;
    }
}
