package election.global.Interface;

import election.global.VotingMaterials;
import election.global.exception.globalException;

public interface clientStub extends java.rmi.Remote {

    public String getCredentials() throws java.rmi.RemoteException;

    public void badCredentials(String password) throws java.rmi.RemoteException, globalException;

    public VotingMaterials goodCredentials(VotingMaterials votingMaterials) throws java.rmi.RemoteException;

    public String getUserName() throws java.rmi.RemoteException;

}
