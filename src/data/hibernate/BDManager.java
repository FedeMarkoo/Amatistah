package data.hibernate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

public class BDManager {

	private static SessionFactory factory;

	public Object getObject(String classForGet) throws ClassNotFoundException, IOException {
		return getObject(classForGet, null);
	}

	public Object getObject(String classForGet, Integer index) throws ClassNotFoundException, IOException {
		Class<?> realClass = Class.forName("data.objects." + classForGet);
		return getObject(realClass, index);
	}

	public Object getObject(Class<?> classForGet) throws ClassNotFoundException, IOException {
		Class<?> realClass = Class.forName("data.objects." + classForGet);
		return getObject(realClass, null);
	}

	@SuppressWarnings("rawtypes")
	public Object getObject(Class<?> classForGet, Integer index) throws ClassNotFoundException, IOException {
		Object result = null;
		Session session = getSession();
		try {
			if (index == null) {
				Transaction tx = session.beginTransaction();
				NativeQuery q = session.createNativeQuery("CREATE TABLE 'Menu' ('menuID'	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,'descript' TEXT NOT NULL UNIQUE, 'url' TEXT NOT NULL, 'title'	TEXT DEFAULT 'AmaTista')");
				//q.executeUpdate();
				tx.commit();
				CriteriaBuilder cb = session.getCriteriaBuilder();
				CriteriaQuery<?> cq = cb.createQuery(classForGet);
				Root rootEntry = cq.from(classForGet);
				CriteriaQuery<?> all = cq.select(rootEntry);
				
				Query<?> allQuery = (Query<?>) session.createQuery(all);
				result = allQuery.getResultList();
			} else {
				result = session.get(classForGet, index);
			}
		} finally {
			session.close();
		}
		return result;

	}

	public Object saveObject(Object object) throws ClassNotFoundException, IOException {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(object);
		tx.commit();
		session.close();
		return object;
	}

	private Session getSession() throws ClassNotFoundException, IOException {
		if (factory == null) {
			Configuration config = new Configuration();

			registerMappers(config);

			config.setProperties(getHibernateProperties());

			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
					.applySettings(config.getProperties());
			factory = config.buildSessionFactory(builder.build());
		}
		return factory.openSession();
	}

	private static Properties getHibernateProperties() {
		Properties props = new Properties();

		props.put("hibernate.connection.driver_class", "org.sqlite.JDBC");
		props.put("hibernate.connection.url", "jdbc:sqlite:../DataBase.db");
		props.put("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.format_sql", "true");

		return props;

	}

	private static void registerMappers(Configuration config) throws ClassNotFoundException, IOException {
		Class<?>[] classes = getClasses("data.objects");
		for (Class<?> class1 : classes) {
			config.addAnnotatedClass(class1);
		}
	}

	private static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<?> resources = classLoader.getResources(path);
		ArrayList<File> dirs = new ArrayList<>();
		while (resources.hasMoreElements()) {
			URL resource = (URL) resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> classes = new ArrayList<>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	private static ArrayList<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
		ArrayList<Class<?>> classes = new ArrayList<>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(
						Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}
}
