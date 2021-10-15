/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 *
 * @author Shang
 */
public class PropertiesFileHelper {

    public static Properties getProperties(ServletContext context, String fileRelativePath) throws IOException {
        InputStream input = context.getResourceAsStream(fileRelativePath);
        Properties prop = new Properties();
        prop.load(input);
        return prop;
    }

}
