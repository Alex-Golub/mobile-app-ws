package edu.mrdrprof.app.ws;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Get access to spring application context to get beans by name.
 *
 * @author Mr.Dr.Professor
 * @since 3/21/2021 1:47 PM
 */
public class SpringApplicationContext implements ApplicationContextAware {
  private static ApplicationContext context;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  public static Object getBean(String beanName) {
    return context.getBean(beanName);
  }
}
