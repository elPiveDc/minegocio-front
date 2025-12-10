package com.minegocio.backend.multitenant.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TenantCredentials {
    private String url;
    private String username;
    private String password;
}
