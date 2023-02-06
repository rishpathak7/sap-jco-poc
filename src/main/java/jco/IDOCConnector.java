package jco;

import com.sap.conn.idoc.*;
import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.io.*;
import java.util.Properties;

public class IDOCConnector {
    static String IP="10.241.163.214", //IP or HOST
            USER="khsarkar", // user name of SAP
            PASSWORD=")js5Rm8M#nhy)U1P!*<r", // password of SAP
            CLIENT="200", //mandant in sap
            SYSNR="01", // instance number
            LANG="en", // language (es or en)
            AUTH_TYPE="Basic"; //auth type

    public static void main(String[] args) throws JCoException, IDocParseException, IOException {
        System.out.println("SAP Test is running");

        try {
            // This will create a file called mySAPSystem.jcoDestination
            String DESTINATION_NAME1 = "EXCHANGE_RATE01";
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
            JCoDestination destination = JCoDestinationManager.getDestination("EXCHANGE_RATE01");
//            final Destination destination1 = DestinationAccessor.getDestination(destinationName);
//            destination.ping();

            String iDocXML = null;
            FileReader fileReader;

            try {
                fileReader = new FileReader("/home/llohithvarma/Documents/sap-jco-poc/src/main/java/jco/payload.xml");
                BufferedReader br = new BufferedReader(fileReader);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                iDocXML = sb.toString();
                System.out.println(iDocXML);

                br.close();
                fileReader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
//
            try
            {
                IDocRepository iDocRepository = JCoIDoc.getIDocRepository(destination);
                System.out.println("iDocRepository ->"+iDocRepository);

                String tid=destination.createTID();
                IDocFactory iDocFactory=JCoIDoc.getIDocFactory();
                System.out.println("iDocFactory ->"+iDocFactory);
                System.out.println();
//
//                // a) create new IDoc
//                IDocDocument doc = iDocFactory.createIDocDocument(iDocRepository, "SYIDOC01");
////                System.out.println(doc.);
//                IDocSegment segment=doc.getRootSegment();
//                System.out.println("segment "+segment);
//                segment=segment.addChild("E1IDOCH");
//                // and so on. See IDoc Specification .....
//                JCoIDoc.send(doc, IDocFactory.IDOC_VERSION_DEFAULT, destination, tid);
//                System.out.println(" done ");

                // b) use existent xml file
                IDocXMLProcessor processor=iDocFactory.getIDocXMLProcessor();
                IDocDocumentList iDocList=processor.parse(iDocRepository, iDocXML);
                for(int i=0;i<iDocList.size();i++){
                    IDocDocument iDocDocument = iDocList.get(i);
                    iDocDocument.checkSyntax();
                }
                JCoIDoc.send(iDocList, IDocFactory.IDOC_VERSION_DEFAULT, destination, tid);
                destination.confirmTID(tid);

                System.out.println("TID: "+tid);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

//            IDocRepository iDocRepository = JCoIDoc.getIDocRepository(destination);
//            IDocFactory iDocFactory = JCoIDoc.getIDocFactory();
//            System.out.println(iDocFactory);
//            String tid = destination.createTID();
//            System.out.println(tid);
//            IDocFactory iDocFactory = JCoIDoc.getIDocFactory();

//            System.out.println("Attributes:");
//            System.out.println(destination.getAttributes());
//            System.out.println();

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
