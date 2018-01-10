package net.softler.data.persistence

import net.softler.model.Todo
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import scala.concurrent.{ExecutionContext, Future}
import scala.language.{existentials, postfixOps}

class TodoTable(tag: Tag) extends BaseTable[Todo](tag, "todos") {
  val name = column[String]("name")

  def * =
    (id, name, created, updated.?, deleted.?) <> (Todo.tupled, Todo.unapply)
}

class TodoRepository(implicit ex: ExecutionContext)
    extends BaseRepo[Todo, TodoTable](TableQuery[TodoTable])
    with DBComponent {

  val table = TableQuery[TodoTable]

  def byName(name: String): Future[Seq[Todo]] = db.run {
    table.filter(_.name like name).result
  }
}
