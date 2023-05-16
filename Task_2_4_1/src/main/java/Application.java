import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import group.Group;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;

public class Application {
    private GroovyShell groovyShell;

    private Application() {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        groovyShell = new GroovyShell(Application.class.getClassLoader(), new Binding(), cc);
    }

    public static void main(String[] args) throws IOException {
        Group group = new Group();
        Application application = new Application();

        application.parse(group, "Task_2_4_1/src/config/group.groovy");

        System.out.println(group.getGroupNumber());
        System.out.println(group.getStudents().getList().size());
        for (var student : group.getStudents().getList()) {
            System.out.println(student.getName());
            System.out.println(student.getRepo());
        }
    }

    public void parse(Object object, String path) throws IOException {
        DelegatingScript script = (DelegatingScript) groovyShell.parse(new File(path));
        script.setDelegate(object);
        script.run();
    }
}
