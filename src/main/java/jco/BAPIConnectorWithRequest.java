package jco;

import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.s4hana.connectivity.rfc.BapiRequest;
import com.sap.cloud.sdk.s4hana.connectivity.rfc.BapiRequestResult;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class BAPIConnectorWithRequest {
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
            String DESTINATION_NAME1 = "Z_POST_INVOICE_PAYMENT_FULL";
            Properties connectProperties = new Properties();
            connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,   IP);
            connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,    SYSNR);
            connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,   CLIENT);
            connectProperties.setProperty(DestinationDataProvider.JCO_USER,     USER);
            connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,   PASSWORD);
            connectProperties.setProperty(DestinationDataProvider.JCO_LANG,     LANG);
            connectProperties.setProperty(DestinationDataProvider.JCO_AUTH_TYPE, AUTH_TYPE);
            //connectProperties.setProperty(DestinationDataProvider.)
            connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
            connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,    "10");


            createDestinationDataFile(DESTINATION_NAME1,connectProperties);

            Map<String, String> destinationProps = new HashMap<String, String>();
// Added a sample destination name. Solved NoSuchElementException error.
            destinationProps.put("Name", DESTINATION_NAME1);
            destinationProps.put("URL", IP);
            destinationProps.put("Type", "HTTP");
            destinationProps.put("Password", PASSWORD);
            destinationProps.put("Authentication", "BasicAuthentication");
            destinationProps.put("User", USER);

            final Destination destination= new DefaultDestination(destinationProps);
            final BapiRequestResult result = new BapiRequest("Z_POST_INVOICE_PAYMENT_FULL").execute(destination);
            System.out.println("Result: " + result.toString());

            /*
            final BapiRequestResult resultGetCostCenters = new BapiRequest("Z_POST_INVOICE_PAYMENT_FULL")
                    .withExporting("CONTROLLINGAREA", "BAPI0012_GEN-CO_AREA", "0001")
                    .withTable("COSTCENTERLIST", "BAPI0012_CCLIST").end()
                    .withTableAsReturn("BAPIRET2")
                    .execute(destination);
            */

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
