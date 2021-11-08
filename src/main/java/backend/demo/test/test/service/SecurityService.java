package backend.demo.test.test.service;

import backend.demo.test.test.model.User;

public interface SecurityService {
	boolean isAuthenticated();
    void autoLogin(String username, String password);
//    boolean findUserByDelFlg(int delFlg);
    
}
