package dataServerApp;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class DataServerApplication {
    private final static Logger logger = Logger.getLogger(DataServerApplication.class);
    private static final int defaultPort = 1099;

    /** Smallest available server port */
    private static final int SMALLEST_PORT = 1025;
    /** Largest available server port */
    private static final int LARGEST_PORT = 65535;

    private String serverIP = null;

    private IRemoteDb remoteDb = null;

    // Change the control of authentication from Remote Db to Data Server.
    private Authenticator authenticator = null;
    private DataWareHouse wareHouse = null;
    private Registry registry = null;
    private DataWareHouse dataWareHouse = null;
    /**
     * constructor
     */
    public DataServerApplication() {
        this.authenticator = Authenticator.getInstance();
        this.dataWareHouse = new DataWareHouse();
    }

    /**
     * start run server
     */
    public void runDataServer() {
        if (serverIP == null) {
            logger.fatal("Server address hasn't been specified");
            return;
        }

        try {
            // For testing purpose, IP address is not used(since it is for now only local machine)
            // Later the ip will be used for several machines testing purpose.
            registry = LocateRegistry.createRegistry(defaultPort);
            registry.bind("DB", remoteDb);

            logger.info("Data server start running (by RMI) at localhost port: " + defaultPort);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Data server remote registry set up failed");
        }
    }

    /**
     * exit data server program
     */
    public void exit() {
        try {
            UnicastRemoteObject.unexportObject(remoteDb, false);
            
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Data server remove remote object from rmi runtime failed");
        }
    }

    /**
     * Set up server address (ip, port)
     * @param ip
     * @return true if set successfully
     */
    public boolean setAddress(String ip) {
        this.serverIP = ip;
        return true;
    }



    void setRemoteDb(DataServerFacade facade){

        try{
            this.remoteDb = new RemoteDb(facade);

        }catch (Exception e){
            logger.fatal("Initialization database remote object failed");
            e.printStackTrace();
        }
    }

    String addUser(String username, String password){
        return authenticator.registerUser(username, password).toJSONString();
    }

    String checkUser(String username, String password){
        return authenticator.authenticate(username, password).toJSONString();
    }

    String saveCanvas(JSONObject canvas, String managerName){
        JSONObject returnMessage = new JSONObject();
        if(!dataWareHouse.save(managerName,canvas)){
            returnMessage.put("header", "Fail");
            returnMessage.put("message", "Fail to store the canva");
            return returnMessage.toJSONString();
        }
        returnMessage.put("header", "Success");
        returnMessage.put("message", "Successfully save the data");
        return returnMessage.toJSONString();
    }
    String retrieveCanvas(String targetManager){
        return dataWareHouse.retrieveData(targetManager);
    }

    void iteratePassBook(){
        authenticator.iteratePassbook();
    }
}
