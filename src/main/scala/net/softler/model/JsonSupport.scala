package net.softler.model

import java.sql.Timestamp
import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsNumber, JsString, JsValue, JsonFormat, RootJsonFormat}

trait BaseJsonProtocol extends DefaultJsonProtocol {
  implicit val timestampFormat: JsonFormat[Timestamp] = new JsonFormat[Timestamp] {
    override def write(obj: Timestamp): JsValue = JsNumber(obj.getTime)

    override def read(json: JsValue): Timestamp = json match {
      case JsNumber(time) => new Timestamp(time.toLong)
      case _ =>
        throw new IllegalArgumentException(s"Could not read json input [$json] as json number")
    }
  }

  implicit val uuidJsonFormat: JsonFormat[UUID] = new JsonFormat[UUID] {
    override def write(x: UUID): JsValue = JsString(x.toString)

    override def read(value: JsValue): UUID = value match {
      case JsString(x) => UUID.fromString(x)
      case x =>
        throw new IllegalArgumentException("Expected UUID as JsString, but got " + x.getClass)
    }
  }
}

/**
  * Implicit json conversion -> Nothing to do when we complete the object
  */
trait JsonProtocol extends SprayJsonSupport with BaseJsonProtocol {
  implicit val todoFormat: RootJsonFormat[Todo] = jsonFormat5(Todo)
}

object JsonProtocol extends JsonProtocol
