package net.softler.http

import akka.http.scaladsl.server.Route
import net.softler.data.persistence.TodoRepository
import akka.http.scaladsl.server.Directives._
import net.softler.model.{JsonProtocol, Todo}

import scala.concurrent.ExecutionContext

/**
  * The todo endpoints to fetch todo's
  */
class TodoRoute(todoRepository: TodoRepository)(implicit ec: ExecutionContext)
    extends JsonProtocol {

  lazy val route: Route = pathPrefix("todo") {
    pathEndOrSingleSlash {
      get {
        complete(todoRepository.all)
      } ~ post {
        entity(as[Todo]) { todo =>
          complete(todoRepository.insert(todo))
        }
      }
    }
  }
}
