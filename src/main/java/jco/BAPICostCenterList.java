package jco;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.json.XML;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class BAPICostCenterList {
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
            String DESTINATION_NAME1 = "BAPI_COSTCENTER_GETLIST1";
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

            JCoDestination destination = JCoDestinationManager.getDestination("BAPI_COSTCENTER_GETLIST1");
            destination.ping();

            JCoFunction function = destination.getRepository().getFunction("BAPI_COSTCENTER_GETLIST1");
            System.out.println(function);
            JCoParameterList importParameterList = function.getImportParameterList();
            importParameterList.setValue("CONTROLLINGAREA","US01");
//            importParameterList.setValue("CONTROLLINGAREA","");
//            importParameterList.setValue("COSTCENTER_FROM","");
//            importParameterList.setValue("COSTCENTER_TO","");
//            importParameterList.setValue("COMPANYCODE_FROM","");
//            importParameterList.setValue("COMPANYCODE_TO","");
//            importParameterList.setValue("PERSON_IN_CHARGE_FROM","");
//            importParameterList.setValue("PERSON_IN_CHARGE_TO","");
//            importParameterList.setValue("DATE_FROM","");
//            importParameterList.setValue("DATE_TO","");
//            importParameterList.setValue("COSTCENTERGROUP","");
//            importParameterList.setValue("BUSINESS_AREA_FROM","");
//            importParameterList.setValue("BUSINESS_AREA_TO","");
//            importParameterList.setValue("MASTER_DATE_INACTIVE","");
//            importParameterList.setValue("PERSON_IN_CHARGE_USER_FROM","");
//            importParameterList.setValue("PERSON_IN_CHARGE_USER_TO","");

            function.execute(destination);
            String output = function.getTableParameterList().getTable("COSTCENTERLIST").toXML();
            System.out.println(XML.toJSONObject(output));

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
