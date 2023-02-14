package jco;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.json.XML;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class BAPIPostPurchaseReq {
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
            String DESTINATION_NAME1 = "BAPI_PR_CHANGE";
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

            JCoDestination destination = JCoDestinationManager.getDestination("BAPI_PR_CHANGE");

            JCoFunction function = destination.getRepository().getFunction("BAPI_PR_CHANGE");
            JCoParameterList importParameterList = function.getImportParameterList();
            importParameterList.setValue("NUMBER","0010000080");
            JCoTable pritem = function.getTableParameterList().getTable("PRITEM");
            pritem.appendRow();
            pritem.setValue("PREQ_ITEM","00010");
            pritem.setValue("QUANTITY", "5.000");
            pritem.setValue("DELIV_DATE", "");
            JCoTable pritemx = function.getTableParameterList().getTable("PRITEMX");
            pritemx.appendRow();
            pritemx.setValue("PREQ_ITEM","00010");
            pritemx.setValue("PREQ_ITEMX","X");
            pritemx.setValue("QUANTITY", "X");
            pritemx.setValue("DELIV_DATE", "");

            function.execute(destination);
            String aReturn = function.getTableParameterList().getTable("RETURN").toXML();
            System.out.println(XML.toJSONObject(aReturn));

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
