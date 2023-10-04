package election.global.Interface;

import election.global.exception.globalException;

public interface ServerVote {

    public void vote(String userName, int studentNumber) throws java.rmi.RemoteException, globalException;


}
