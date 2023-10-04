package election.global.Interface;

import election.global.User;
import election.global.exception.globalException;

public interface ServerVote {

    public void vote(User user) throws java.rmi.RemoteException, globalException;


}
