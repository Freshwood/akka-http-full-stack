package net.softler.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.{ActorMaterializer, Materializer}
import net.softler.config.ServerConfig
import akka.http.scaladsl.server.Directives._
import net.softler.data.flyway.FlywayService
import net.softler.data.persistence.TodoRepository
import net.softler.http.{ResourceRoute, TodoRoute}

import scala.concurrent.ExecutionContext

trait FlywayIntegration extends ServerConfig {
  val flyWayService = new FlywayService(jdbcUrl, dbUser, dbPassword)

  flyWayService.migrateDatabaseSchema()
}

trait HttpService extends ServerConfig {

  def todoRepo: TodoRepository

  def todoService: TodoRoute

  def resourceRoutes: ResourceRoute

  def routes: Route
}

trait HttpServer extends HttpService {
  implicit def actorSystem: ActorSystem

  implicit def materializer: Materializer

  implicit def ec: ExecutionContext

  override def todoRepo: TodoRepository = new TodoRepository

  override def todoService: TodoRoute = new TodoRoute(todoRepo)

  override def resourceRoutes: ResourceRoute = new ResourceRoute

  def routes: Route = todoService.route ~ resourceRoutes.route
}

/**
  * The root akka http server instance
  */
trait AkkaHttpServer extends HttpServer {

  implicit override def actorSystem: ActorSystem = ActorSystem("akka-full-stack")

  implicit override def materializer: Materializer = ActorMaterializer()

  implicit override def ec: ExecutionContext = actorSystem.dispatcher

  def routes: Route

  Http().bindAndHandle(routes, host, port)
}

object FullStackExampleApp extends App with AkkaHttpServer with FlywayIntegration
