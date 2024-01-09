package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.User;

public interface AuthenticationService {
    User signInAndReturnJWT(User signInRequest);
}
