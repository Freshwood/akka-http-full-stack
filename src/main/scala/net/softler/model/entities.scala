package net.softler.model

import java.sql.Timestamp
import java.util.UUID

trait Entity {
  def id: UUID
  def created: Timestamp
  def updated: Option[Timestamp]
  def deleted: Option[Timestamp]
}

case class Todo(id: UUID,
                name: String,
                created: Timestamp,
                updated: Option[Timestamp],
                deleted: Option[Timestamp])
    extends Entity
