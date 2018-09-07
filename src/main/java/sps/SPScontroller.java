package sps;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@RestController
public class SPScontroller {
	
    @RequestMapping("/")
    public String index() throws Exception {
    	
    	ScriptEngineManager factory = new ScriptEngineManager();
    	ScriptEngine engine = factory.getEngineByName("groovy");

    	engine.put("my", SPSutil.getDataSource());
    	String result = (String) engine.eval(loadGvy());
    	
        return "Greetings from Spring Boot!" + result;
    }
    
  
    private Reader loadGvy() throws Exception {
        //need set the file path locally!!!!
        File gvy = new File("D:\\2018\\mockup-sp\\sps-boot-groovy\\test-temp-table.gvy");

        InputStream in = new FileInputStream(gvy);
        return (new InputStreamReader(in));
    }    
}
