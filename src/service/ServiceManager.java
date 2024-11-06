    package service;

    import service.Command;

    import java.util.HashMap;
    import java.util.Stack;

    public class ServiceManager {
        private final HashMap<String, Service> services = new HashMap<>();
        private final Stack<Command> history = new Stack<>();

        public <T extends Service> T getService(Class<T> serviceClass) {
            return (T) services.get(serviceClass.getName());
        }

        public void registerService(Service service) {
            services.put(service.getClassToBind().getName(), service);
        }

        public void executeCommand(Command command) {
            command.execute();
            history.push(command);
        }


        public void undoLastCommand() {
            if (!history.isEmpty()) {
                Command command = history.pop();
                command.undo();
            } else {
                System.out.println("Nothing to undo.");
            }
        }
    }
