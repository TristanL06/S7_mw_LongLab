




public interface Distant extends java.rmi.Remote {

    public Candidate retrieveCandidate() throws java.rmi.RemoteException;

    public void getVotingMaterials(clientStub cbc) throws java.rmi.RemoteException;



}
