package jco;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.json.XML;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class BAPIPostConnector {
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
            String DESTINATION_NAME1 = "BAPI_GOODSMVT_GETITEMS";
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

            JCoDestination destination = JCoDestinationManager.getDestination("BAPI_GOODSMVT_GETITEMS");
            destination.ping();

//            System.out.println("---------------------------");
//            System.out.println("destination \n"+destination.getAttributes());
//            System.out.println("---------------------------");

//            System.out.println("---------------------------");
//            System.out.println("Repository -> " + destination);
//            System.out.println("---------------------------");

            JCoFunction function = destination.getRepository().getFunction("BAPI_GOODSMVT_GETITEMS");
            JCoParameterList tableParameterList = function.getTableParameterList();
            System.out.println("table attribute -> " + tableParameterList.getListMetaData());

//            System.out.println(function.getTableParameterList().getTable("MATERIAL_RA").isEmpty());

            JCoTable materialTable = function.getTableParameterList().getTable("MATERIAL_RA");
            materialTable.appendRow();
            materialTable.setValue("SIGN", "I");
            materialTable.setValue("OPTION", "EQ");
            materialTable.setValue("LOW", "000000000000000090");
            materialTable.setValue("HIGH", "01");
            materialTable.setValue("HIGH_EXTERNAL", "0100");
            materialTable.setValue("HIGH_GUID", "00");
            materialTable.setValue("HIGH_VERSION", "090");
            materialTable.setValue("LOW_EXTERNAL", "080");
            materialTable.setValue("LOW_GUID", "080");
            materialTable.setValue("LOW_VERSION", "09");
            materialTable.setValue("LOW_LONG", "90");
            materialTable.setValue("HIGH_LONG", "09");

            JCoTable plantTable = function.getTableParameterList().getTable("PLANT_RA");
            plantTable.appendRow();
            plantTable.setValue("SIGN", "I");
            plantTable.setValue("OPTION", "EQ");
            plantTable.setValue("LOW", "US01");
            plantTable.setValue("HIGH", "US02");

            JCoTable pstingDataTable = function.getTableParameterList().getTable("PSTNG_DATE_RA");
            pstingDataTable.appendRow();
            pstingDataTable.setValue("SIGN", "I");
            pstingDataTable.setValue("OPTION", "BT");
            pstingDataTable.setValue("LOW", "20200227");
            pstingDataTable.setValue("HIGH", "20200303");
            pstingDataTable.appendRow();

            System.out.println("---------- Before Function execution --------------\n");
            System.out.println(function.getTableParameterList().getTable("MATERIAL_RA"));
            System.out.println(function.getTableParameterList().getTable("PLANT_RA"));
            System.out.println(function.getTableParameterList().getTable("PSTNG_DATE_RA"));
            System.out.println(function.getTableParameterList().getTable("RETURN"));
            System.out.println(function.getTableParameterList().getTable("GOODSMVT_HEADER"));
            System.out.println(function.getTableParameterList().getTable("GOODSMVT_ITEMS"));
            System.out.println(function.getTableParameterList().getTable("GOODSMVT_ITEMS_CWM"));
            System.out.println("---------- Before Function execution --------------\n");

            System.out.println("---------- After Function execution --------------\n");
            function.execute(destination);

            System.out.println(function.getTableParameterList().getTable("MATERIAL_RA"));
            System.out.println(function.getTableParameterList().getTable("PLANT_RA"));
            System.out.println(function.getTableParameterList().getTable("PSTNG_DATE_RA"));
            System.out.println(function.getTableParameterList().getTable("RETURN"));
            System.out.println(function.getTableParameterList().getTable("GOODSMVT_HEADER").getNumRows());
            System.out.println(function.getTableParameterList().getTable("GOODSMVT_ITEMS"));
            System.out.println(function.getTableParameterList().getTable("GOODSMVT_ITEMS_CWM"));
            System.out.println("---------- After Function execution --------------\n");

            String vendorRa = function.getTableParameterList().getTable("VENDOR_RA").toXML();
            System.out.println(vendorRa);

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