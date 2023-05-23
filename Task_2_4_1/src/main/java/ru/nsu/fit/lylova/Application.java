package ru.nsu.fit.lylova;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.fit.lylova.model.TestConfiguration;

import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Objects;

public class Application {

    public static void main(String[] args) throws URISyntaxException {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(Application.class.getClassLoader(), new Binding(), cc);
        String filePath = "/config.groovy";
        DelegatingScript script = (DelegatingScript) sh.parse(
                new InputStreamReader(Objects.requireNonNull(Application.class.getResourceAsStream(filePath))));

        TestConfiguration config = new TestConfiguration();
        config.setScriptPath(Objects.requireNonNull(Application.class.getResource(filePath)).toURI());
        script.setDelegate(config);
        script.run();
        config.postProcess();
    }

}
