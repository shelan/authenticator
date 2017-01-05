package org.shelan;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.shelan.model.Model;
import org.shelan.model.Sql2oModel;

/**
 * Created by shelan on 1/4/17.
 */
public class App {

    public static void main(String[] args) {

        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        Option hostOption = Option.builder("dbhost")
                .desc("Database host")
                .hasArg()
                .build();
        Option portOption = Option.builder("dbport")
                .desc("Database port")
                .hasArg()
                .build();
        Option dbOption = Option.builder("dbname")
                .desc("Database name")
                .hasArg(true)
                .build();
        Option usernameOption = Option.builder("u")
                .desc("Database username")
                .hasArg(true)
                .build();
        Option passwordOption = Option.builder("p")
                .desc("Database Password")
                .hasArg(true)
                .build();
        Option appPortOption = Option.builder("port")
                .desc("App port option")
                .hasArg(true)
                .build();

        options.addOption(hostOption);
        options.addOption(portOption);
        options.addOption(dbOption);
        options.addOption(usernameOption);
        options.addOption(passwordOption);

        try {
            CommandLine line = parser.parse(options, args);
            String username = line.getOptionValue("u", "root");
            String password = line.getOptionValue("p", "");
            String host = line.getOptionValue("dbhost", "localhost");
            String port = line.getOptionValue("dbport", "3306");
            String db = line.getOptionValue("dbname", "auth");
            String appport = line.getOptionValue("port", "4567");

            Model sql2oModel = new Sql2oModel(host, Integer.valueOf(port), db, username, password);
            Controller controller = new Controller(sql2oModel);
            Router router = new Router(controller, Integer.valueOf(appport));

            router.registerRoutes();


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
