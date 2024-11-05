package service;

import java.util.HashMap;

public class ServiceManager {
    private final HashMap<String, Service> services = new HashMap<>();

    public <T extends Service> T getService(Class<?> tClass) {
        return (T) services.get(tClass.getName());
    }

    public void registerService(Service service) {
        services.put(service.getClassToBind().getName(), service);
    }
}
