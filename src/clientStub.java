


public interface clientStub extends java.rmi.Remote {

    public String getCredentials() throws java.rmi.RemoteException;

    public void badCredentials() throws java.rmi.RemoteException;

    public void goodCredentials() throws java.rmi.RemoteException;

}
