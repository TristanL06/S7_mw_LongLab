package election.global.Interface;

import election.global.User;
import election.global.exception.globalException;

public interface ServerVote extends java.rmi.Remote {

    public void vote(User user) throws java.rmi.RemoteException, globalException;


}
