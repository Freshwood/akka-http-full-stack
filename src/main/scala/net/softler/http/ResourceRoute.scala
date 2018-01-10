package net.softler.http

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

/**
  * The static resource routes
  */
class ResourceRoute {

  private val index = get {
    getFromResource("web/index.html")
  }

  lazy val route: Route =
    pathEndOrSingleSlash {
      index
    } ~ {
      path("index.html") {
        index
      }
    }
}
