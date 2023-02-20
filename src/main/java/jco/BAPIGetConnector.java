package jco;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class BAPIGetConnector {
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

            JCoFunction function = destination.getRepository().getFunction("BAPI_GOODSMVT_GETDETAIL");
            JCoParameterList importParameterList = function.getImportParameterList();
            importParameterList.setValue("MATDOCUMENTYEAR","2020");
            importParameterList.setValue("MATERIALDOCUMENT","1000000621");
            System.out.println(function);
            function.execute(destination);
            System.out.println("xyz:  " + function.getExportParameterList().toXML());
//            JCoParameterList tableParameterList = function.getTableParameterList();
//            System.out.println("table attribute -> " + tableParameterList.getListMetaData());
//            JCoTable materialTable = function.getTableParameterList().getTable("MATERIAL_RA");
//            materialTable.appendRow();
//            materialTable.setValue("SIGN", "I");
//            materialTable.setValue("OPTION", "EQ");
//            function.execute(destination);
////            JCoTable materialTable = function.getTableParameterList().getTable("MATERIAL_RA");
////            materialTable.appendRow();
////            materialTable.setValue("SIGN", "I");
////            function.execute(destination);
//            System.out.println(function.getTableParameterList().getTable("MATERIAL_RA"));
//            JCoParameterList input = function.getImportParameterList();
//            System.out.println(input.getListMetaData());
//            input.setValue("DATE","20200427");
//            input.setValue("FROM_CURR","");
//            input.setValue("RATE_TYPE","123");
//            input.setValue("TO_CURRNCY","");
//            System.out.println(input.getListMetaData());

//            function.execute(destination);

//            String output = function.getExportParameterList().toXML();
//            System.out.println(XML.toJSONObject(output));

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