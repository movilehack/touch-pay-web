package com.touchpay.presentation.routers

import com.touchpay.domain.CredentialKind
import com.touchpay.domain.Permission
import com.touchpay.presentation.PresentationComponent
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.core.Handler

class RouterRoot(
        presentationComponent: PresentationComponent,
        router: Router,
        routerPermission: RouterPermission,
        routerUtils: RouterUtils)
    : AbstractRouter(presentationComponent, router, routerPermission, routerUtils) {

    constructor(presentationComponent: PresentationComponent, router: Router, routerUtils: RouterUtils) :
        this(presentationComponent, router, RouterPermission(), routerUtils)

    fun allow(vararg kinds: CredentialKind): RouterBranch {
        val newRouterAuthentication = routerPermission.copy()
        kinds.toCollection(newRouterAuthentication.kinds)
        return RouterBranch(presentationComponent, router, newRouterAuthentication, routerUtils)
    }

    fun allow(vararg permissions: Permission): RouterBranch {
        val newRouterAuthentication = routerPermission.copy()
        permissions.toCollection(newRouterAuthentication.permissions)
        return RouterBranch(presentationComponent, router, newRouterAuthentication, routerUtils)
    }

    fun onlyAuthenticated(): RouterBranch {
        val newRouterAuthentication = routerPermission.copy()
        newRouterAuthentication.onlyAuthenticated = true
        return RouterBranch(presentationComponent, router, newRouterAuthentication, routerUtils)
    }

    fun handler(path: String, requestHandler: Handler<RoutingContext>) {
        router.route(path).handler(requestHandler)
    }
}

