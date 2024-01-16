package com.kaengee.withhobby.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassword {
    private String currentPassword;
    private String newPassword;
}
