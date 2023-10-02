import exception.globalException;

import java.util.ArrayList;

public interface Distant extends java.rmi.Remote {

    public ArrayList<Candidate> retrieveCandidate() throws java.rmi.RemoteException;

    public void getVotingMaterials(clientStub clientSubObject, int studentNumber) throws java.rmi.RemoteException, globalException;



}
