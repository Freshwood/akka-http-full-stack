package net.softler

import akka.actor.ActorSystem
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.stream.Materializer
import net.softler.model.JsonProtocol
import net.softler.server.HttpServer
import org.scalatest.{Matchers, _}

import scala.concurrent.ExecutionContext

/**
  * The main akka http server test which should test all routes
  */
class AkkaHttpServerTest extends FlatSpec with Matchers with ScalatestRouteTest with JsonProtocol {
  self =>

  object ServerTest extends Tag("net.softler.test")

  object TestHttpServer extends HttpServer {
    override implicit def actorSystem: ActorSystem = self.system

    override implicit def materializer: Materializer = self.materializer

    override implicit def ec: ExecutionContext = self.executor
  }

  private def routes: Route = TestHttpServer.routes

  "The akka http router" should "response with an index page" taggedAs ServerTest in {
    get("/index.html") {
      contentType shouldBe ContentTypes.`text/html(UTF-8)`
    }
  }

  it should "fetch a list of todo's" taggedAs ServerTest in {
    get("/todo") {
      contentType shouldBe ContentTypes.`application/json`
    }
  }

  it should "not handle a unknown path" taggedAs ServerTest in {
    get("/todos") {
      handled shouldBe false
    }
  }

  private def get(path: String)(assertion: => Assertion) = {
    Get(path) ~> routes ~> check(assertion)
  }
}
