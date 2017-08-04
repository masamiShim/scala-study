package dao

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import models.Task
import scala.concurrent.Future
import play.api.db.DBApi

class TaskDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Tasks = TableQuery[TasksTable]

  def all(): Future[Seq[Task]] = db.run(Tasks.result)

  def insert(task: Task): Future[Unit] = db.run(Tasks += task).map { _ => () }

  private class TasksTable(tag: Tag) extends Table[Task](tag, "task") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def label = column[String]("label")

    def * = (id.?, label) <> (Task.tupled, Task.unapply _)
  }
}