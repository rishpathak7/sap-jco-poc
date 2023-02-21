package jco;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.json.XML;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class BAPIProductionOrder {
    static String   IP="10.241.163.214", //IP or HOST
            USER="khsarkar", // user name of SAP
            PASSWORD=")js5Rm8M#nhy)U1P!*<r", // password of SAP
            CLIENT="200", //mandant in sap
            SYSNR="01", // instance number
            LANG="en", // language (es or en)
            AUTH_TYPE="Basic"; //auth type

    public static void main(String[] args) {
        System.out.println("SAP Test is running");

        try {
            // This will create a file called mySAPSystem.jcoDestination
            String DESTINATION_NAME1 = "BAPI_PRODORD_CREATE";
            Properties connectProperties = new Properties();
            connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,   IP);
            connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,    SYSNR);
            connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,   CLIENT);
            connectProperties.setProperty(DestinationDataProvider.JCO_USER,     USER);
            connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,   PASSWORD);
            connectProperties.setProperty(DestinationDataProvider.JCO_LANG,     LANG);
            connectProperties.setProperty(DestinationDataProvider.JCO_AUTH_TYPE, AUTH_TYPE);
            connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
            connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,    "10");

            createDestinationDataFile(DESTINATION_NAME1,connectProperties);

            JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME1);
            destination.ping();

            JCoFunction function = destination.getRepository().getFunction(DESTINATION_NAME1);
            System.out.println("function: "+function);
            System.out.println(function.getExportParameterList().getMetaData());

            JCoStructure orderdata = function.getImportParameterList().getStructure("ORDERDATA");
            orderdata.setValue("MATERIAL", "3");
            orderdata.setValue("PLANT", "US01");
            orderdata.setValue("ORDER_TYPE", "ZP02");
            orderdata.setValue("BASIC_START_TIME", "00:00:00");
            orderdata.setValue("BASIC_END_DATE", "20210823");
            orderdata.setValue("BASIC_END_TIME", "00:00:00");
            orderdata.setValue("QUANTITY", "1000");
            orderdata.setValue("SCRAP_QUANTITY", "0");
            orderdata.setValue("QUANTITY_UOM", "EA");
            orderdata.setValue("CONFIGURATION", "0");
            orderdata.setValue("SALES_ORDER_ITEM", "0");
            orderdata.setValue("SEQUENCE_NUMBER", "0");
            orderdata.setValue("GR_PROC_TIME", "0");
            orderdata.setValue("ADDITIONAL_DAYS", "0");

           function.execute(destination);
           System.out.println(function.getExportParameterList().toXML());
           System.out.println(XML.toJSONObject(function.getExportParameterList().toXML()));
        } catch(Exception ex) {
            System.out.println("exception "+ex.toString());
        }
    }

    private static void createDestinationDataFile(String destinationName, Properties connectProperties) {
        File destCfg = new File(destinationName+".jcoDestination");
        try {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "for tests only !");
            fos.close();
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to create the destination files", e);
        }
    }
}
