package jco;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class BAPIConnector {
    static String IP="10.241.163.214", //IP or HOST
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
            String DESTINATION_NAME1 = "Z_POST_INVOICE_PAYMENT_FULL";
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

            // This will use that destination file to connect to SAP

            JCoDestination destination = JCoDestinationManager.getDestination("Z_POST_INVOICE_PAYMENT_FULL");
            //final Destination destination1 = DestinationAccessor.getDestination(destinationName);
            //final BapiRequestResult result = new BapiRequest("BAPI_COSTCENTER_GETLIST1")
            destination.ping();

            System.out.println("Attributes:");
            System.out.println(destination.getAttributes());
            System.out.println();

            //destination.ping();
        } catch (JCoException ex) {
            System.out.println("exception "+ex.toString());
        } catch(Exception ex) {
            System.out.println("exception "+ex.toString());
        }
    }

    private static void createDestinationDataFile(String destinationName, Properties connectProperties)
    {
        File destCfg = new File(destinationName+".jcoDestination");
        try
        {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "for tests only !");
            fos.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to create the destination files", e);
        }
    }
}
