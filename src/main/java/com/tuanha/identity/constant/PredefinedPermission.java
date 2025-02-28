package com.tuanha.identity.constant;

public class PredefinedPermission {
    public static final String CREATE_POST = "CREATE_POST";
    public static final String APPROVE_POST = "APPROVE_POST";
    public static final String REJECT_POST = "REJECT_POST";

    public static final String[] ALL_PERMISSIONS = {
        CREATE_POST, APPROVE_POST, REJECT_POST
    };

    public static final String[] ADMIN_PERMISSIONS = {
        CREATE_POST, APPROVE_POST, REJECT_POST
    };

    public static final String[] USER_PERMISSIONS = {
        CREATE_POST
    };
}
