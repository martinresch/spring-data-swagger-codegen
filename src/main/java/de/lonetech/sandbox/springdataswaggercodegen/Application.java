package de.lonetech.sandbox.springdataswaggercodegen;

import de.lonetech.sandbox.springdataswaggercodegen.service.PetDataLoad;
import de.lonetech.sandbox.springdataswaggercodegen.service.PetExport;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("de.lonetech.sandbox.springdataswaggercodegen.repository")
public class Application implements CommandLineRunner {

  @Autowired
  PetDataLoad petDataLoad;

  @Autowired
  PetExport petExport;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    CommandLineParser parser = new DefaultParser();

    Options options = new Options();
    options.addOption("load", false, "load all pets into database");
    options.addOption("export", false, "export all pets into csv");
    options.addOption(Option.builder("export").hasArg().optionalArg(false).argName("file").desc("path and file name of the export file").build());

    if (args == null || args.length == 0) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("java -jar spring-data-swagger-codegen.jar", null, options, "\ne.g.\njava -jar spring-data-swagger-codegen.jar -load -export /tmp/export.csv");
      return;
    }

    try {
      CommandLine line = parser.parse(options, args);

      if (line.hasOption("load")) {
        petDataLoad.loadAndStore();
      }
      if (line.hasOption("export")) {
        String fileName = line.getOptionValue("export");
        petExport.export(fileName);
      }
    }
    catch (ParseException exp) {
      System.out.println("Unexpected exception:" + exp.getMessage());
    }

    System.exit(0);
  }
}
