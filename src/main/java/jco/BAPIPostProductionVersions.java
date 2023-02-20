package jco;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.json.XML;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class BAPIPostProductionVersions {
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
            String DESTINATION_NAME1 = "CM_FV_PROD_VERS_DB_UPDATE";
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
            JCoParameterList tableParameterList = function.getTableParameterList();
            System.out.println("table attribute -> " + tableParameterList.getListMetaData());

            JCoTable materialTable = function.getTableParameterList().getTable("IT_MKAL_I");
            materialTable.appendRow();
            materialTable.setValue("MANDT", "206");
            materialTable.setValue("MATNR", "16");
            materialTable.setValue("WERKS", "US04");
            materialTable.setValue("VERID", "PV04");
            materialTable.setValue("BDATU", "2021-01-02");
            materialTable.setValue("ADATU", "2021-01-02");
            materialTable.setValue("STLAL", "8");
            materialTable.setValue("STLAN", "9");
            materialTable.setValue("PLNTY", "N");
            materialTable.setValue("PLNNR", "50000002");
            materialTable.setValue("ALNAL", "1");
            materialTable.setValue("LOSGR", "0");
            materialTable.setValue("TEXT1", "PRODUCTION VERS");
            materialTable.setValue("EWAHR", "0");
            materialTable.setValue("BSTMI", "1");
            materialTable.setValue("BSTMA", "999.999");
            materialTable.setValue("PRDAT", "2020-02-27");
            materialTable.setValue("PPEGUID", "00000000000000000000000000000000");

            function.execute(destination);

            String vendorRa = function.getTableParameterList().getTable("IT_MKAL_I").toXML();
            System.out.println(XML.toJSONObject(vendorRa));
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