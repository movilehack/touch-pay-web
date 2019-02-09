package com.touchpay.presentation.routers

import com.touchpay.domain.CredentialKind
import com.touchpay.domain.Permission

class RouterPermission {
    var onlyAuthenticated = false
    val kinds = mutableSetOf<CredentialKind>()
    val permissions = mutableSetOf<Permission>()

    fun copy(): RouterPermission {
        val copyRouterPermission = RouterPermission()
        copyRouterPermission.onlyAuthenticated = onlyAuthenticated
        kinds.toCollection(copyRouterPermission.kinds)
        permissions.toCollection(copyRouterPermission.permissions)
        return copyRouterPermission
    }
}
