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
            String DESTINATION_NAME1 = "BAPI_EMPLOYEE_GETDATA";
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

            JCoDestination destination = JCoDestinationManager.getDestination("BAPI_EMPLOYEE_GETDATA");
            destination.ping();

            JCoFunction function = destination.getRepository().getFunction("BAPI_EMPLOYEE_GETDATA");

            JCoParameterList jcoParameterList = function.getImportParameterList();
            jcoParameterList.setValue("workerID", "6723");
            jcoParameterList.setValue("firstName", "David");
            jcoParameterList.setValue("lastName", "sssss");
            jcoParameterList.setValue("secondaryLastName", "Hercule");
            jcoParameterList.setValue("street", "xxxx Beekman Place NW");
            jcoParameterList.setValue("street2", "Building 100");
            jcoParameterList.setValue("street3", "Street 100");
            jcoParameterList.setValue("street4", "Area 100");
            jcoParameterList.setValue("city", "Victor");
            jcoParameterList.setValue("district", "District");
            jcoParameterList.setValue("postalCode", "20009");
            jcoParameterList.setValue("region", "District of Columbia");
            jcoParameterList.setValue("countryKey", "US");
            jcoParameterList.setValue("emailAddress", "david.ssss@ssss.com");
            jcoParameterList.setValue("telephone", "(347) 276-xxxx");
            jcoParameterList.setValue("businessPartnerNumber", "xxxxxx");
            jcoParameterList.setValue("reconciliationAccountNumber", "2111103");
            jcoParameterList.setValue("companyCode", "300");
            jcoParameterList.setValue("languageKey", "en_US");
            function.execute(destination);

            System.out.println(function.getTableParameterList().getMetaData());

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
